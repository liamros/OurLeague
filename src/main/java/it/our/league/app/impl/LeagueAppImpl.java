package it.our.league.app.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.our.league.app.LeagueAppManager;
import it.our.league.app.LeagueMatchManager;
import it.our.league.app.LeagueSummonerManager;
import it.our.league.app.controller.dto.AppLineChartDTO;
import it.our.league.app.controller.dto.AppLineChartWrapperDTO;
import it.our.league.app.controller.dto.AppParticipantInfoDTO;
import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppShowCaseRankingDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.ShowCaseRankingJPA;
import it.our.league.app.impl.persistence.repository.ShowCaseRankingRepository;
import it.our.league.app.thread.DataRefreshHandler;
import it.our.league.app.utility.LeagueAppUtility;
import it.our.league.common.constants.LeagueQueueType;
import it.our.league.common.constants.LineChartType;
import it.our.league.common.constants.ShowCaseType;

public class LeagueAppImpl implements LeagueAppManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAppImpl.class);

    @Autowired
    private DataRefreshHandler dataRefreshHandler;
    @Autowired
    private LeagueSummonerManager leagueSummonerImpl;
    @Autowired
    private LeagueMatchManager leagueMatchImpl;
    @Autowired
    private ShowCaseRankingRepository showCaseRankingRepository;

    @Override
    public Map<String, List<AppShowCaseRankingDTO>> getShowCaseRankings() {

        Iterable<ShowCaseRankingJPA> scrs = showCaseRankingRepository.findAllByOrderByPositionAsc();
        Map<String, List<AppShowCaseRankingDTO>> response = new HashMap<>();

        for (ShowCaseRankingJPA showCaseRanking : scrs) {
            List<AppShowCaseRankingDTO> l = response.getOrDefault(showCaseRanking.getStatName(), new ArrayList<>());
            List<AppRankInfoDTO> ranks = leagueSummonerImpl.getRanksByPuuid(showCaseRanking.getSummoner().getPuuid());
            // provisory
            AppRankInfoDTO highestRank = LeagueAppUtility.getHighestRankFromDto(ranks);
            l.add(LeagueAppUtility.generateAppShowCaseDetailDTO(showCaseRanking, highestRank));
            response.putIfAbsent(showCaseRanking.getStatName(), l);
        }
        return response;
    }

    @Override
    @Transactional
    public void updateShowCaseRankings() {

        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        checkAndSaveShowcaseRankings(getHighestKDAShowcase(summoners));
        checkAndSaveShowcaseRankings(getHighestKillShowcase(summoners));
        checkAndSaveShowcaseRankings(getHighestWinrateShowcase(summoners));
        LOGGER.info("Showcase Rankings updated");
    }

    @Override
    public List<Float> getWinRateByPuuid(String puuid) {
        List<Float> list = new ArrayList<Float>();
        List<AppRankInfoDTO> ranks = leagueSummonerImpl.getRanksByPuuid(puuid);
        for (AppRankInfoDTO rank : ranks) {
            list.add(rank.getWinrate());
        }
        return list;
    }

    @Override
    public String asyncronousDataRefresh() {
        dataRefreshHandler.startDataRefresh();
        return "OK";
    }

    private List<ShowCaseRankingJPA> getHighestKDAShowcase(List<AppSummonerDTO> summoners) {

        Queue<Map<String, Object>> maxHeap = new PriorityQueue<>((a, b) -> {
            Float aKda = (Float) a.get("value");
            Float bKda = (Float) b.get("value");
            if (bKda > aKda)
                return 1;
            return -1;
        });

        for (AppSummonerDTO summoner : summoners) {
            List<AppParticipantInfoDTO> participantInfos = leagueMatchImpl
                    .getAllPopulatedParticipantInfoByPuuid(summoner.getPuuid());
            Map<Integer, List<AppParticipantInfoDTO>> groupedByQueueId = new HashMap<>();
            for (AppParticipantInfoDTO p : participantInfos) {
                if (!p.getQueueTypeId().equals(LeagueQueueType.RANKED_SOLO_5x5.id())
                        && !p.getQueueTypeId().equals(LeagueQueueType.RANKED_FLEX_SR.id()))
                    continue;
                List<AppParticipantInfoDTO> l = groupedByQueueId.getOrDefault(p.getQueueTypeId(),
                        new ArrayList<>());
                l.add(p);
                groupedByQueueId.putIfAbsent(p.getQueueTypeId(), l);
            }
            Map<String, Object> map = null;
            for (Map.Entry<Integer, List<AppParticipantInfoDTO>> entry : groupedByQueueId.entrySet()) {
                Float kda = LeagueAppUtility.getAverageKDA(entry.getValue());
                if (map == null || (float) map.get("value") < kda) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("value", kda);
                    m.put("summInfoId", summoner.getSummInfoId());
                    String desc = MessageFormat.format(String.format("%.2f in {0}", kda),
                            LeagueQueueType.getById(entry.getKey()).description());
                    m.put("description", desc);
                    map = m;
                }
            }
            if (map != null)
                maxHeap.offer(map);
        }
        List<ShowCaseRankingJPA> list = showCaseRankingRepository.findByStatName(ShowCaseType.BEST_KDA.statName());
        List<ShowCaseRankingJPA> results = updateShowCaseRankingJpas(list, maxHeap, ShowCaseType.BEST_KDA);

        return results;
    }

    private List<ShowCaseRankingJPA> getHighestWinrateShowcase(List<AppSummonerDTO> summoners) {

        Queue<Map<String, Object>> maxHeap = new PriorityQueue<>((a, b) -> {
            if ((Float) b.get("value") > (Float) a.get("value"))
                return 1;
            return -1;
        });
        for (AppSummonerDTO summoner : summoners) {
            Map<String, Object> highestRank = null;
            for (AppRankInfoDTO rank : summoner.getRanks()) {
                Map<String, Object> m = new HashMap<>();
                m.put("summInfoId", rank.getSummInfoId());
                m.put("value", rank.getWinrate());
                String desc = MessageFormat.format("{0}% in {1}", String.format("%.2f", rank.getWinrate()),
                        LeagueQueueType.getById(rank.getQueueTypeId()).description());
                m.put("description", desc);
                if (highestRank == null) {
                    highestRank = m;
                    continue;
                }
                if (rank.getWinrate() > (Float) highestRank.get("value"))
                    highestRank = m;
            }
            if (highestRank != null)
                maxHeap.offer(highestRank);
        }
        List<ShowCaseRankingJPA> list = showCaseRankingRepository.findByStatName(ShowCaseType.BEST_WR.statName());
        List<ShowCaseRankingJPA> results = updateShowCaseRankingJpas(list, maxHeap, ShowCaseType.BEST_WR);

        return results;
    }

    private List<ShowCaseRankingJPA> getHighestKillShowcase(List<AppSummonerDTO> summoners) {

        Queue<Map<String, Object>> maxHeap = new PriorityQueue<>((a, b) -> {
            Integer aKills = (Integer) a.get("kills");
            Integer bKills = (Integer) b.get("kills");
            if (aKills == bKills) {
                AppRankInfoDTO aRank = (AppRankInfoDTO) a.get("highestRank");
                AppRankInfoDTO bRank = (AppRankInfoDTO) b.get("highestRank");
                AppRankInfoDTO highest = LeagueAppUtility.getHighestRankFromDto(Arrays.asList(aRank, bRank));
                if (bRank.getSummInfoId() == highest.getSummInfoId())
                    return 1;
                return -1;
            }
            return bKills - aKills;
        });

        for (AppSummonerDTO summoner : summoners) {
            AppParticipantInfoDTO p = leagueMatchImpl.getHighestKillParticipantInfo(summoner.getPuuid());
            Map<String, Object> m = new HashMap<>();
            m.put("summInfoId", p.getSummInfoId());
            m.put("kills", p.getKills());
            m.put("highestRank", LeagueAppUtility.getHighestRankFromDto(summoner.getRanks()));
            m.put("value", (float) p.getKills());
            String desc = MessageFormat.format("{0} with {1}", p.getKills(), p.getChampionName());
            m.put("description", desc);
            maxHeap.offer(m);
        }
        List<ShowCaseRankingJPA> list = showCaseRankingRepository.findByStatName(ShowCaseType.HIGHEST_KILLS.statName());
        List<ShowCaseRankingJPA> results = updateShowCaseRankingJpas(list, maxHeap, ShowCaseType.HIGHEST_KILLS);

        return results;
    }

    private List<ShowCaseRankingJPA> updateShowCaseRankingJpas(List<ShowCaseRankingJPA> list,
            Queue<Map<String, Object>> maxHeap, ShowCaseType showCaseType) {

        showCaseRankingRepository.detachAllEntities(list);
        List<ShowCaseRankingJPA> results = new ArrayList<>();
        Map<Integer, List<ShowCaseRankingJPA>> map = LeagueAppUtility.groupBySummId(list);
        int position = 1;
        while (!maxHeap.isEmpty()) {
            Map<String, Object> m = maxHeap.poll();
            ShowCaseRankingJPA jpa;
            Integer summInfoId = (Integer) m.get("summInfoId");
            Float value = (Float) m.get("value");
            String description = (String) m.get("description");
            if (map.get(summInfoId) == null || map.get(summInfoId).isEmpty())
                jpa = new ShowCaseRankingJPA();
            else
                jpa = map.get(summInfoId).get(0);
            jpa.setStatName(showCaseType.statName());
            jpa.setSummInfoId(summInfoId);
            jpa.setPrevPosition(jpa.getPosition());
            jpa.setPosition(position++);
            jpa.setValue(value);
            jpa.setDescription(description);
            results.add(jpa);
        }
        return results;
    }

    private void checkAndSaveShowcaseRankings(List<ShowCaseRankingJPA> scrs) {
        boolean positionChanged = false;
        for (ShowCaseRankingJPA scr : scrs) {
            if (scr.getPosition() != scr.getPrevPosition())
                positionChanged = true;
        }
        if (positionChanged) {
            showCaseRankingRepository.saveAll(scrs);
            return;
        }
        for (ShowCaseRankingJPA scr : scrs) {
            showCaseRankingRepository.saveExceptPosition(scr.getId(), scr.getValue(), scr.getDescription());
        }
    }

    // TODO divide by queueId
    private AppLineChartWrapperDTO getWinratePerMinuteChart(
            Map<String, List<AppParticipantInfoDTO>> matchesPerSummoner) {
        List<AppLineChartDTO> charts = new ArrayList<>();
        for (Map.Entry<String, List<AppParticipantInfoDTO>> summonerMatches : matchesPerSummoner.entrySet()) {
            List<AppParticipantInfoDTO> matches = summonerMatches.getValue();
            AppLineChartDTO lineChart = new AppLineChartDTO();
            lineChart.setId(summonerMatches.getKey());
            Map<Integer, List<AppParticipantInfoDTO>> matchesPerMinute = initTimeMap(matches);
            for (Map.Entry<Integer, List<AppParticipantInfoDTO>> entry : matchesPerMinute.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    lineChart.addData(String.valueOf(entry.getKey()), (float) 0);
                    continue;
                }
                Integer wins = 0;
                for (AppParticipantInfoDTO p : entry.getValue())
                    if (p.getWin())
                        wins++;
                lineChart.addData(String.valueOf(entry.getKey()), (float) wins / (float) entry.getValue().size());
            }
            if (!lineChart.getData().isEmpty()) {
                Collections.sort(lineChart.getData(), (a, b) -> {
                    return Integer.parseInt(a.getX()) - Integer.parseInt(b.getX());
                });
                charts.add(lineChart);
            }
        }
        AppLineChartWrapperDTO response = LeagueAppUtility.generateAppLineChartWrapperDTO(LineChartType.WINRATExMIN, charts, 0, 1);
        return response;
    }

    // TODO divide by queueId
    private AppLineChartWrapperDTO getVisionPerMinuteChart(
            Map<String, List<AppParticipantInfoDTO>> matchesPerSummoner) {

        List<AppLineChartDTO> charts = new ArrayList<>();
        for (Map.Entry<String, List<AppParticipantInfoDTO>> summonerMatches : matchesPerSummoner.entrySet()) {
            List<AppParticipantInfoDTO> matches = summonerMatches.getValue();
            AppLineChartDTO lineChart = new AppLineChartDTO();
            lineChart.setId(summonerMatches.getKey());
            Map<Integer, List<AppParticipantInfoDTO>> matchesPerMinute = initTimeMap(matches);
            for (Map.Entry<Integer, List<AppParticipantInfoDTO>> entry : matchesPerMinute.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    lineChart.addData(String.valueOf(entry.getKey()), (float) 0);
                    continue;
                }
                Integer totVisionScore = 0;
                for (AppParticipantInfoDTO p : entry.getValue())
                    totVisionScore += p.getVisionScore();
                Float value = (float) totVisionScore / (float) entry.getValue().size();
                lineChart.addData(String.valueOf(entry.getKey()), value);
            }
            if (!lineChart.getData().isEmpty()) {
                Collections.sort(lineChart.getData(), (a, b) -> {
                    return Integer.parseInt(a.getX()) - Integer.parseInt(b.getX());
                });
                charts.add(lineChart);
            }
        }
        AppLineChartWrapperDTO response = LeagueAppUtility.generateAppLineChartWrapperDTO(LineChartType.VISIONxMIN, charts, 0, 140);
        return response;
    }

    // TODO divide by queueId
    private AppLineChartWrapperDTO getGamesPerMinuteChart(Map<String, List<AppParticipantInfoDTO>> matchesPerSummoner) {

        List<AppLineChartDTO> charts = new ArrayList<>();
        int maxSize = 0;
        for (Map.Entry<String, List<AppParticipantInfoDTO>> summonerMatches : matchesPerSummoner.entrySet()) {
            List<AppParticipantInfoDTO> matches = summonerMatches.getValue();
            AppLineChartDTO lineChart = new AppLineChartDTO();
            lineChart.setId(summonerMatches.getKey());
            Map<Integer, List<AppParticipantInfoDTO>> matchesPerMinute = initTimeMap(matches);
            for (Map.Entry<Integer, List<AppParticipantInfoDTO>> entry : matchesPerMinute.entrySet()) {
                maxSize = Math.max(maxSize, entry.getValue().size());
                lineChart.addData(String.valueOf(entry.getKey()), (float) entry.getValue().size());
            }
            if (!lineChart.getData().isEmpty()) {
                Collections.sort(lineChart.getData(), (a, b) -> {
                    return Integer.parseInt(a.getX()) - Integer.parseInt(b.getX());
                });
                charts.add(lineChart);
            }
        }
        if (maxSize % 10 != 0)
            maxSize += (10 - maxSize % 10);
        AppLineChartWrapperDTO response = LeagueAppUtility.generateAppLineChartWrapperDTO(LineChartType.GAMESxMIN, charts, 0, maxSize);
        return response;
    }

    

    @Override
    public List<AppLineChartWrapperDTO> getAllHomeCharts() {

        List<AppParticipantInfoDTO> matches = leagueMatchImpl.getAllPopulatedParticipantInfo();
        Map<String, List<AppParticipantInfoDTO>> matchesPerSummoner = mapMatchesByGameName(matches);
        List<AppLineChartWrapperDTO> out = new ArrayList<>();
        out.add(getGamesPerMinuteChart(matchesPerSummoner));
        out.add(getVisionPerMinuteChart(matchesPerSummoner));
        out.add(getWinratePerMinuteChart(matchesPerSummoner));
        return out;
    }

    @Override
    public AppLineChartWrapperDTO getGamesPerMinuteChart() {
        List<AppParticipantInfoDTO> matches = leagueMatchImpl.getAllPopulatedParticipantInfo();
        return getGamesPerMinuteChart(mapMatchesByGameName(matches));
    }

    @Override
    public AppLineChartWrapperDTO getVisionPerMinuteChart() {
        List<AppParticipantInfoDTO> matches = leagueMatchImpl.getAllPopulatedParticipantInfo();
        return getVisionPerMinuteChart(mapMatchesByGameName(matches));
    }

    @Override
    public AppLineChartWrapperDTO getWinratePerMinuteChart() {
        List<AppParticipantInfoDTO> matches = leagueMatchImpl.getAllPopulatedParticipantInfo();
        return getWinratePerMinuteChart(mapMatchesByGameName(matches));
    }

    
    private Map<String, List<AppParticipantInfoDTO>> mapMatchesByGameName(List<AppParticipantInfoDTO> list) {

        Map<String, List<AppParticipantInfoDTO>> map = new HashMap<>();
        for (AppParticipantInfoDTO p : list) {
            List<AppParticipantInfoDTO> l = map.getOrDefault(p.getGameName(), new ArrayList<>());
            l.add(p);
            map.putIfAbsent(p.getGameName(), l);
        }
        return map;
    }

    private Map<Integer, List<AppParticipantInfoDTO>> initTimeMap(List<AppParticipantInfoDTO> matches) {
        Map<Integer, List<AppParticipantInfoDTO>> matchesPerMinute = new HashMap<>();
        int minTime = 15;
        int maxTime = 45;
        for (int i = minTime; i <= maxTime; i += 5)
            matchesPerMinute.put(i, new ArrayList<>());
        for (AppParticipantInfoDTO match : matches) {
            Long endTime = match.getEndTime().getTime();
            Long startTime = match.getStartTime().getTime();
            Integer timeSlice = ((int) (endTime - startTime)) / 1000 / 60 / 5 * 5;
            if (timeSlice < 15)
                continue;
            if (timeSlice > maxTime)
                timeSlice = maxTime;
            List<AppParticipantInfoDTO> l = matchesPerMinute.get(timeSlice);
            l.add(match);
        }
        return matchesPerMinute;
    }
}
