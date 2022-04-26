package it.our.league.app;

import java.util.List;

import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Match;

public interface LeagueMatchManager {
    
    public int updateMatchHistory(AppSummonerDTO summoner);

    public int populateMatchData(String matchId);

    public Match getMatchData(String matchId);

    // provisory
    public int populateAllMatchData();

    @Deprecated
    public List<Match> getMatchesByPuuid(String puuid, String queueType, Integer count);

    public List<Match> getAllMatchesByPuuid(String puuid);

    public int alignRelSummonerMatches();

}
