package it.our.league.app.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.impl.persistence.entity.MatchInfoJPA;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.app.impl.persistence.repository.MatchInfoRepository;
import it.our.league.app.impl.persistence.repository.RelSummonerMatchRepository;
import it.our.league.app.impl.persistence.repository.SummonerInfoRepository;
import it.our.league.app.mongodb.repository.MatchRepository;
import it.our.league.app.thread.MatchHistoryRunnable;
import it.our.league.riot.RiotManagerInterface;
import it.our.league.riot.dto.Match;

public class LeagueMatchImpl implements LeagueMatchManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueMatchImpl.class);

    private final long defaultTimestamp = 1641524400;

    private RiotManagerInterface riotManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SummonerInfoRepository summonerInfoRepository;

    @Autowired
    private RelSummonerMatchRepository relSummonerMatchRepository;

    @Autowired
    private MatchInfoRepository matchInfoRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Override
    @Transactional
    public int updateMatchHistory(int summInfoId) {

        List<String> matchIds = null;
        Optional<SummonerInfoJPA> optional = summonerInfoRepository.findById(summInfoId);
        if (!optional.isPresent())
            return 0;
        SummonerInfoJPA summoner = optional.get();
        int count = 0;
        while (matchIds == null || !matchIds.isEmpty()) {
            Integer countMatches = relSummonerMatchRepository.getNumberOfMatches(summInfoId);
            /**
             * countMatches is the index from which starts the list of matchIds that Riot sends
             */
            matchIds = riotManager.getMatchIdsByPuuid(summoner.getPuuid(), "ranked", 100, defaultTimestamp,
                    countMatches);
            Iterable<MatchInfoJPA> jpas = matchInfoRepository.findAllById(matchIds);
            /**
             * filter matchIds by removing already existing records on DB, otherwise they'd
             * be updated with null values
             */
            Set<String> existingMatchIds = new HashSet<>();
            for (MatchInfoJPA jpa : jpas)
                existingMatchIds.add(jpa.getMatchId());
            List<String> filteredMatchIds = new ArrayList<>();
            for (String matchId : matchIds)
                if (!existingMatchIds.contains(matchId))
                    filteredMatchIds.add(matchId);
            matchInfoRepository.saveAll(LeagueAppUtility.generateMatchInfoJpas(filteredMatchIds));
            relSummonerMatchRepository.saveAll(LeagueAppUtility.generateRelSummonerMatchJpas(summInfoId, matchIds));
            count+=matchIds.size();
        }

        return count;
    }

    @Override
    public int completeMatchData(String matchId) {
        try {
            Match match = riotManager.getMatchById(matchId);
            MatchInfoJPA jpa = LeagueAppUtility.generateMatchInfoJpa(match);
            matchInfoRepository.save(jpa);
            LOGGER.info("INFO : Persisted match {} to DB", matchId);
            matchRepository.save(match);
            LOGGER.info("INFO : Persisted match {} to MongoDB", matchId);
        } catch (Exception e) {
            LOGGER.error("ERROR : Error occured while performing operations with {}", matchId, e);
            if (e.getCause().getMessage().contains("Http request failed"))
                throw e;
            return 0;
        }
        return 1;
    }

    @Override
    public Match getMatchData(String matchId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int populateMatchInfo() {
        List<String> matchIds = matchInfoRepository.getAllPendingMatches();
        int count = 0;

        for (String matchId : matchIds)
            count += completeMatchData(matchId);
           
        return count;
    }

    @Override
    public String asyncronousMatchHistoryUpdate() {
        Runnable matchHistoryRunnable = new MatchHistoryRunnable();
        applicationContext.getAutowireCapableBeanFactory().autowireBean(matchHistoryRunnable);
        Thread thread = new Thread(matchHistoryRunnable);
        thread.start();
        return "OK";
    }
    
}
