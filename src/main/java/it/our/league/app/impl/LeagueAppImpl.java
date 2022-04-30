package it.our.league.app.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import it.our.league.app.thread.DataRefreshRunnable;
import it.our.league.app.utility.LeagueAppUtility;
import it.our.league.common.constants.LeagueQueueType;
import it.our.league.common.constants.ShowCaseType;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Participant;

public class LeagueAppImpl implements LeagueAppManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAppImpl.class);

    private Thread t;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private LeagueSummonerManager leagueSummonerImpl;
    @Autowired
    private LeagueMatchManager leagueMatchImpl;
    @Autowired
    private ShowCaseRankingRepository showCaseRankingRepository;

    @Override
    public Map<String, List<AppShowCaseRankingDTO>> getShowCaseRankings() {

        Iterable<ShowCaseRankingJPA> scrs = showCaseRankingRepository.findAll();
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
    // TODO update prevPosition of a rankingList only when at least one of its value
    // changes
    public void updateShowCaseRankings() {

        List<ShowCaseRankingJPA> listShowCaseUpdated = new ArrayList<>();
        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        listShowCaseUpdated.addAll(getHighestKDAShowcase(summoners));
        listShowCaseUpdated.addAll(getHighestWinrateShowcase(summoners));
        listShowCaseUpdated.addAll(getHighestKillShowcase(summoners));
        showCaseRankingRepository.saveAll(listShowCaseUpdated);
        LOGGER.info("Persisted {}", listShowCaseUpdated);
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

    public ShowCaseRankingJPA generateLowerWinRate() {

        ShowCaseRankingJPA showCaseJpa = new ShowCaseRankingJPA();
        AppSummonerDTO summoner = leagueSummonerImpl.getLowestWinrateSummoner();
        Float minWR = Float.MAX_VALUE;
        if (summoner.getRanks().isEmpty())
            minWR = null;
        for (AppRankInfoDTO rank : summoner.getRanks())
            minWR = Math.min(minWR, rank.getWinrate());
        showCaseJpa.setStatName(ShowCaseType.WORST_WR.statName());
        showCaseJpa.setSummInfoId(summoner.getSummInfoId());
        showCaseJpa.setValue(minWR);
        showCaseJpa.setDescription("Lowest WinRate");
        return showCaseJpa;
    }

    @Deprecated
    public ShowCaseRankingJPA generateLowerKda() {

        ShowCaseRankingJPA showCaseJpa = new ShowCaseRankingJPA();
        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        Participant participant = getParticipantWithLowerKda(summoners);

        showCaseJpa.setStatName(ShowCaseType.WORST_KDA.statName());
        for (AppSummonerDTO summoner : summoners) {
            if (participant.getPuuid().equals(summoner.getPuuid()))
                showCaseJpa.setSummInfoId(summoner.getSummInfoId());
        }
        showCaseJpa.setValue(Float.valueOf(String.valueOf(participant.getChallenges().getKda())));
        showCaseJpa.setDescription("Lowest KDA");
        return showCaseJpa;
    }

    @Deprecated
    private Participant getParticipantWithLowerKda(List<AppSummonerDTO> summoners) {

        List<Match> matches = new ArrayList<>();
        Double lowerKda = (double) showCaseRankingRepository.findByStatName(ShowCaseType.WORST_KDA.statName())
                .get(0).getValue();
        Participant participant = null;

        for (Match matchDTO : matches) {
            for (AppSummonerDTO summoner : summoners) {
                if (lowerKda == null || LeagueAppUtility.getParticipantByMatch(matchDTO, summoner.getPuuid())
                        .getChallenges().getKda() < lowerKda)
                    lowerKda = LeagueAppUtility.getParticipantByMatch(matchDTO, summoner.getPuuid()).getChallenges()
                            .getKda();
                participant = LeagueAppUtility.getParticipantByMatch(matchDTO, summoner.getPuuid());
            }
        }
        return participant;
    }

    @Override
    public synchronized String asyncronousDataRefresh() {
        // TODO find another way to handle this (implement a threadHandler)
        if (t != null && t.isAlive())
            return "KO";

        Runnable dataRefreshRunnable = new DataRefreshRunnable();
        applicationContext.getAutowireCapableBeanFactory().autowireBean(dataRefreshRunnable);
        t = new Thread(dataRefreshRunnable);
        t.start();
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
                    .getAllParticipantInfoByPuuid(summoner.getPuuid());
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

    @SuppressWarnings("unused")
    @Deprecated
    private ShowCaseRankingJPA getHighestRankShowcase(List<AppSummonerDTO> summoners) {

        AppRankInfoDTO highestRank = null;
        for (AppSummonerDTO summoner : summoners) {
            if (highestRank == null)
                highestRank = LeagueAppUtility.getHighestRankFromDto(summoner.getRanks());
            else {
                List<AppRankInfoDTO> ranks = new ArrayList<>();
                ranks.addAll(summoner.getRanks());
                ranks.add(highestRank);
                highestRank = LeagueAppUtility.getHighestRankFromDto(ranks);
            }
        }
        ShowCaseRankingJPA jpa = new ShowCaseRankingJPA();
        jpa.setStatName(ShowCaseType.HIGHEST_RANK.statName());
        jpa.setSummInfoId(highestRank.getSummInfoId());
        jpa.setDescription(
                MessageFormat.format("{0} {1} {2}LP in {3}", highestRank.getTier(), highestRank.getDivision(),
                        highestRank.getLp(), LeagueQueueType.getById(highestRank.getQueueTypeId()).description()));
        return jpa;
    }

    private List<ShowCaseRankingJPA> getHighestKillShowcase(List<AppSummonerDTO> summoners) {

        Queue<Map<String, Object>> maxHeap = new PriorityQueue<>((a, b) -> {
            Integer aKills = (Integer) a.get("kills");
            Integer bKills = (Integer) b.get("kills");
            if (aKills == bKills) {
                float bKda = LeagueAppUtility.calculateKDA(bKills, (Integer) b.get("deaths"),
                        (Integer) b.get("assists"));
                float aKda = LeagueAppUtility.calculateKDA(aKills, (Integer) a.get("deaths"),
                        (Integer) a.get("assists"));
                if (bKda > aKda)
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
            m.put("deaths", p.getDeaths());
            m.put("assists", p.getAssists());
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

    @Override
    // TODO divide by queueId
    public AppLineChartWrapperDTO getWinratePerMinuteChart() {
        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        List<AppLineChartDTO> charts = new ArrayList<>();
        for (AppSummonerDTO summoner : summoners) {
            List<AppParticipantInfoDTO> matches = leagueMatchImpl.getAllParticipantInfoByPuuid(summoner.getPuuid());
            AppLineChartDTO lineChart = new AppLineChartDTO();
            lineChart.setId(summoner.getName());
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
        AppLineChartWrapperDTO response = new AppLineChartWrapperDTO();
        response.setName("Winrate/Minute");
        response.setxUnit("Minute");
        response.setyUnit("Winrate");
        response.setFormat(">-.2%");
        response.setCharts(charts);
        response.setMinY(0);
        response.setMaxY(1);
        return response;
    }

    @Override
    public AppLineChartWrapperDTO getVisionPerMinuteChart() {
        
        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        List<AppLineChartDTO> charts = new ArrayList<>();
        for (AppSummonerDTO summoner : summoners) {
            List<AppParticipantInfoDTO> matches = leagueMatchImpl.getAllParticipantInfoByPuuid(summoner.getPuuid());
            AppLineChartDTO lineChart = new AppLineChartDTO();
            lineChart.setId(summoner.getName());
            Map<Integer, List<AppParticipantInfoDTO>> matchesPerMinute = initTimeMap(matches);
            for (Map.Entry<Integer, List<AppParticipantInfoDTO>> entry : matchesPerMinute.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    lineChart.addData(String.valueOf(entry.getKey()), (float) 0);
                    continue;
                }
                Integer totVisionScore = 0;
                for (AppParticipantInfoDTO p : entry.getValue())
                    totVisionScore+=p.getVisionScore();
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
        AppLineChartWrapperDTO response = new AppLineChartWrapperDTO();
        response.setName("VisionScore/Minute");
        response.setxUnit("Minute");
        response.setyUnit("VisionScore");
        response.setFormat(">-.2f");
        response.setCharts(charts);
        response.setMinY(0);
        response.setMaxY(140);
        return response;
    }



    private Map<Integer, List<AppParticipantInfoDTO>> initTimeMap(List<AppParticipantInfoDTO> matches) {
        Map<Integer, List<AppParticipantInfoDTO>> matchesPerMinute = new HashMap<>();
        for (int i = 15; i <= 50; i+=5) 
            matchesPerMinute.put(i, new ArrayList<>());
        for (AppParticipantInfoDTO match : matches) {
            Long endTime = match.getEndTime().getTime();
            Long startTime = match.getStartTime().getTime();
            Integer timeSlice = ((int) (endTime - startTime)) / 1000 / 60 / 5 * 5;
            if (timeSlice < 15)
                continue;
            if (timeSlice > 50)
                timeSlice =  50;
            List<AppParticipantInfoDTO> l = matchesPerMinute.get(timeSlice);
            l.add(match);
        }
        return matchesPerMinute;
    }
    

}
