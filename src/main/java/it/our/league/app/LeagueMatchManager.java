package it.our.league.app;

import it.our.league.riot.dto.MatchDTO;

public interface LeagueMatchManager {
    
    public int updateMatchHistory(int summInfoId);

    public int completeMatchData(String matchId);

    public MatchDTO getMatchData(String matchId);


}
