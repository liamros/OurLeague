package it.our.league.app;

import it.our.league.riot.dto.Match;

public interface LeagueMatchManager {
    
    public int updateMatchHistory(String puuid);

    public int completeMatchData(String matchId);

    public Match getMatchData(String matchId);

    public String asyncronousMatchHistoryUpdate();

    // provisory
    public int populateMatchInfo();

}
