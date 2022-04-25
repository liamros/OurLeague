package it.our.league.app.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.MatchInfoJPA;
import it.our.league.app.impl.persistence.repository.MatchInfoRepository;
import it.our.league.app.impl.persistence.repository.RelSummonerMatchRepository;
import it.our.league.app.mongodb.repository.MatchRepository;
import it.our.league.app.utility.LeagueAppUtility;
import it.our.league.riot.RiotManagerInterface;
import it.our.league.riot.dto.Match;

public class LeagueMatchImpl implements LeagueMatchManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueMatchImpl.class);

    /**
     * beginning of season 12 in seconds
     */
    private final long defaultTimestamp = 1641524400;

    private RiotManagerInterface riotManager;

    @Autowired
    private RelSummonerMatchRepository relSummonerMatchRepository;
    @Autowired
    private MatchInfoRepository matchInfoRepository;
    @Autowired
    private MatchRepository matchRepository;

     /**
     * Fetches from Riot APIs matchIds which don't exist on the DB.
     * It persists them, whith no additional data, for their enrichment look at {@link #completeMatchData}
     * @param puuid Riot's puuid assigned to Summoner
     * @return number of matches found
     */
    @Override
    @Transactional
    public int updateMatchHistory(AppSummonerDTO summoner) {

        List<String> matchIds = null;
        int count = 0;
        Timestamp lastEndTime = relSummonerMatchRepository.getLastMatchEndTimeBySummoner(summoner.getSummInfoId());
        Long fromTime = lastEndTime == null ? defaultTimestamp : lastEndTime.getTime()/1000+60;
        do {
            Integer pendingMatches = relSummonerMatchRepository.getNumberOfPendingMatches(summoner.getPuuid());
            /**
             * countMatches is the index from which starts the list of matchIds that Riot sends
             */
            matchIds = riotManager.getMatchIdsByPuuid(summoner.getPuuid(), "ranked", 100, fromTime,
                    pendingMatches);
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
            LOGGER.info("INFO : Persisted {} new matches", filteredMatchIds.size());
            relSummonerMatchRepository.saveAll(LeagueAppUtility.generateRelSummonerMatchJpas(summoner.getSummInfoId(), matchIds));
            LOGGER.info("INFO : Persisted {} new summoner-match relations", matchIds.size());
            count+=matchIds.size();
        } while (matchIds.size() == 100);
        /**
         * while condition is matchIds.size() == 100 because if it's less, there are no more games to fetch (count = 100)
         */

        return count;
    }

    /**
     * Fetches from DB records from MATCH_INFO that have null values, to enrich them by calling Riot APIs.
     * It also persists the response to MongoDB
     * @param matchId 
     * @return 0 if not successfull, else 1
     * 
     */
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
    @Transactional
    public List<Match> getMatchesByPuuid(String puuid, String queueType, Integer count) {

        List<String> matchesIds = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        try {
            matchesIds = riotManager.getMatchIdsByPuuid(puuid, queueType, count, null, null);
            for (String matchId : matchesIds) {
                matches.add(riotManager.getMatchById(matchId));
            }

        } catch (Exception e) {
            LOGGER.error("ERROR: Error while performing getMatchesBySummInfoId", e);
            throw new RuntimeException();
        }
        return matches;
    }
    
}
