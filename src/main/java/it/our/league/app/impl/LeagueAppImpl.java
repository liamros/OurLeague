package it.our.league.app.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import it.our.league.app.LeagueAppManager;
import it.our.league.app.LeagueMatchManager;
import it.our.league.app.LeagueSummonerManager;
import it.our.league.app.controller.dto.AppParticipantInfoDTO;
import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppShowCaseDetailDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.ShowCaseDetailJPA;
import it.our.league.app.impl.persistence.repository.ShowCaseDetailRepository;
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
    private ShowCaseDetailRepository showCaseDetailRepository;

    @Override
    public List<AppShowCaseDetailDTO> getShowCaseDetails() {

        Iterable<ShowCaseDetailJPA> list = showCaseDetailRepository.findAll();
        List<AppShowCaseDetailDTO> dtos = new ArrayList<>();
        for (ShowCaseDetailJPA showCaseDetailJpa : list) {
            List<AppRankInfoDTO> ranks = leagueSummonerImpl.getRanksByPuuid(showCaseDetailJpa.getSummoner().getPuuid());
            // provisory
            AppRankInfoDTO highestRank = LeagueAppUtility.getHighestRankFromDto(ranks);
            dtos.add(LeagueAppUtility.generateAppShowCaseDetailDTO(showCaseDetailJpa, highestRank));
        }
        return dtos;
    }

    @Override
    public void updateShowCaseDetails() {

        List<ShowCaseDetailJPA> listShowCaseUpdated = new ArrayList<>();
        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        listShowCaseUpdated.addAll(Arrays.asList(getHighestKDAShowcase(summoners), getHighestWinrateShowcase(summoners),
                getHighestRankShowcase(summoners)));
        showCaseDetailRepository.saveAll(listShowCaseUpdated);
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



    public ShowCaseDetailJPA generateLowerWinRate() {

        ShowCaseDetailJPA showCaseJpa = new ShowCaseDetailJPA();
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
    public ShowCaseDetailJPA generateLowerKda() {

        ShowCaseDetailJPA showCaseJpa = new ShowCaseDetailJPA();
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
        Double lowerKda = (double) showCaseDetailRepository.findByStatName(ShowCaseType.WORST_KDA.statName())
                .getValue();
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


    private ShowCaseDetailJPA getHighestKDAShowcase(List<AppSummonerDTO> summoners) {

        AppSummonerDTO finalSummoner = null;
        Float higherKDA = Float.MIN_VALUE;
        Integer queueTypeId = null;
        for (AppSummonerDTO summoner : summoners) {
            List<AppParticipantInfoDTO> participantInfos = leagueMatchImpl
                    .getAllParticipantInfoByPuuid(summoner.getPuuid());
            Map<Integer, List<AppParticipantInfoDTO>> participantInfoPerQueue = new HashMap<>();
            for (AppParticipantInfoDTO p : participantInfos) {
                if (!p.getQueueTypeId().equals(LeagueQueueType.RANKED_SOLO_5x5.id())
                        && !p.getQueueTypeId().equals(LeagueQueueType.RANKED_FLEX_SR.id()))
                    continue;
                List<AppParticipantInfoDTO> l = participantInfoPerQueue.getOrDefault(p.getQueueTypeId(),
                        new ArrayList<>());
                l.add(p);
                participantInfoPerQueue.putIfAbsent(p.getQueueTypeId(), l);
            }
            for (Map.Entry<Integer, List<AppParticipantInfoDTO>> entry : participantInfoPerQueue.entrySet()) {
                Float currentKDA = LeagueAppUtility.getAverageKDA(entry.getValue());
                if (higherKDA < currentKDA) {
                    finalSummoner = summoner;
                    higherKDA = currentKDA;
                    queueTypeId = entry.getKey();
                }
            }
        }
        ShowCaseDetailJPA jpa = new ShowCaseDetailJPA();
        jpa.setStatName(ShowCaseType.BEST_KDA.statName());
        jpa.setSummInfoId(finalSummoner.getSummInfoId());
        jpa.setDescription(MessageFormat.format(String.format("%.1f in {0}", higherKDA),
                LeagueQueueType.getById(queueTypeId).description()));
        return jpa;
    }

    private ShowCaseDetailJPA getHighestWinrateShowcase(List<AppSummonerDTO> summoners) {

        Float highestWinrate = Float.MIN_VALUE;
        AppSummonerDTO finalSummoner = null;
        Integer queueTypeId = null;
        for (AppSummonerDTO summoner : summoners) {
            Float rankHighestWinrate = Float.MIN_VALUE;
            for (AppRankInfoDTO rank : summoner.getRanks()) {
                rankHighestWinrate = Math.max(rankHighestWinrate, rank.getWinrate());
                if (highestWinrate < rankHighestWinrate) {
                    highestWinrate = rankHighestWinrate;
                    finalSummoner = summoner;
                    queueTypeId = rank.getQueueTypeId();
                }
            }
        }
        ShowCaseDetailJPA jpa = new ShowCaseDetailJPA();
        jpa.setStatName(ShowCaseType.BEST_WR.statName());
        jpa.setSummInfoId(finalSummoner.getSummInfoId());
        jpa.setDescription(MessageFormat.format("{0}% in {1}", String.format("%.1f", highestWinrate),
                LeagueQueueType.getById(queueTypeId).description()));
        return jpa;
    }

    private ShowCaseDetailJPA getHighestRankShowcase(List<AppSummonerDTO> summoners) {

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
        ShowCaseDetailJPA jpa = new ShowCaseDetailJPA();
        jpa.setStatName(ShowCaseType.HIGHEST_RANK.statName());
        jpa.setSummInfoId(highestRank.getSummInfoId());
        jpa.setDescription(
                MessageFormat.format("{0} {1} {2}LP in {3}", highestRank.getTier(), highestRank.getDivision(),
                        highestRank.getLp(), LeagueQueueType.getById(highestRank.getQueueTypeId()).description()));
        return jpa;
    }
    
}
