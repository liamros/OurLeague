package it.our.league.app.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import it.our.league.app.LeagueAppManager;
import it.our.league.app.LeagueSummonerManager;
import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppShowCaseDetailDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.ShowCaseDetailJPA;
import it.our.league.app.impl.persistence.repository.ShowCaseDetailRepository;
import it.our.league.app.thread.DataRefreshRunnable;
import it.our.league.app.utility.LeagueAppUtility;
import it.our.league.common.constants.ShowCaseType;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Participant;

public class LeagueAppImpl implements LeagueAppManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueAppImpl.class);

    private static Thread t = new Thread();

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private LeagueSummonerManager leagueSummonerImpl;
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
        listShowCaseUpdated.addAll(Arrays.asList(generateLowerWinRate(), generateLowerKda()));

        showCaseDetailRepository.saveAll(listShowCaseUpdated);
        LOGGER.info("INFO: Persisted {}", listShowCaseUpdated);
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



    private ShowCaseDetailJPA generateLowerWinRate() {

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

    private ShowCaseDetailJPA generateLowerKda() {

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
    public String asyncronousDataRefresh() {
        // TODO find another way to handle this
        synchronized(t) {
            if (t != null && t.isAlive())
                return "KO";
            
            Runnable dataRefreshRunnable = new DataRefreshRunnable();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(dataRefreshRunnable);
            t = new Thread(dataRefreshRunnable);
            t.start();
        }
        return "OK";
    }
    
}
