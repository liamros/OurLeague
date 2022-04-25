package it.our.league.app;

import java.util.List;

import it.our.league.riot.dto.Match;

public interface LeagueMatchManager {
    
    public int updateMatchHistory(String puuid);

    public int completeMatchData(String matchId);

    public Match getMatchData(String matchId);

    // provisory
    public int populateMatchInfo();

    public List<Match> getMatchesByPuuid(String puuid, String queueType, Integer count);

}
