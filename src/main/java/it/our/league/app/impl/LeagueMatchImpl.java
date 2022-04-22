package it.our.league.app.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.app.impl.persistence.repository.MatchInfoRepository;
import it.our.league.app.impl.persistence.repository.RelSummonerMatchRepository;
import it.our.league.app.impl.persistence.repository.SummonerInfoRepository;
import it.our.league.app.mongodb.repository.MatchRepository;
import it.our.league.riot.RiotManagerInterface;
import it.our.league.riot.dto.MatchDTO;

public class LeagueMatchImpl implements LeagueMatchManager {


    private final long defaultTimestamp = 1641524400;

    private RiotManagerInterface riotManager;

    @Autowired
    private SummonerInfoRepository summonerInfoRepository;

    @Autowired
    private RelSummonerMatchRepository relSummonerMatchRepository;

    @Autowired
    private MatchInfoRepository matchInfoRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public int updateMatchHistory(int summInfoId) {

        Optional<SummonerInfoJPA> jpa = summonerInfoRepository.findById(summInfoId);
        if(!jpa.isPresent())
            return 0;
        SummonerInfoJPA summoner = jpa.get();
        Long lastCreationTime = relSummonerMatchRepository.getLastMatchCreationTimeBySummoner(summInfoId);
        lastCreationTime = lastCreationTime == null ? defaultTimestamp : lastCreationTime;
        List<String> matchIds = riotManager.getMatchIdsByPuuid(summoner.getPuuid(), "ranked", 100, lastCreationTime);
        if (matchIds.isEmpty())
            return 0;
        matchInfoRepository.saveAll(LeagueAppUtility.generateMatchInfoJpas(matchIds));
        relSummonerMatchRepository.saveAll(LeagueAppUtility.generateRelSummonerMatchJpas(summInfoId, matchIds));

        return matchIds.size();
    }

    @Override
    public int completeMatchData(String matchId) {
        // TODO Auto-generated method stub

        MatchDTO match = riotManager.getMatchById(matchId);

        matchRepository.save(match);

        return 1;
    }

    @Override
    public MatchDTO getMatchData(String matchId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int populateMatchInfo() {
        List<String> matchIds = matchInfoRepository.getAllPendingMatches();

        // TODO : fetch data, persist it to DB and MongoDB

        return 0;
    }
    
}
