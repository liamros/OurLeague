package it.our.league.app;

import it.our.league.riot.dto.Match;

public interface LeagueMatchManager {
    
    public int updateMatchHistory(int summInfoId);

    public int completeMatchData(String matchId);

    public Match getMatchData(String matchId);

    // provisory
    public int populateMatchInfo();

}
