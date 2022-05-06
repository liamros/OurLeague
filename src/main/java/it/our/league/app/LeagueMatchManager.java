package it.our.league.app;

import java.util.List;

import it.our.league.app.controller.dto.AppParticipantInfoDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Participant;

public interface LeagueMatchManager {
    
    public int updateMatchHistory(AppSummonerDTO summoner);

    public int populateMatchData(String matchId);

    public Match getMatchData(String matchId);

    // provisory
    public int populateAllMatchData();

    public List<Match> getAllMatchesByPuuid(String puuid);

    public int alignRelSummonerMatches();

    public List<Participant> getAllMatchStatisticsByPuuid(String puuid);

    public AppParticipantInfoDTO getParticipantInfo(String matchId, String puuid);

    public List<AppParticipantInfoDTO> getAllParticipantInfoByPuuid(String puuid);

    public AppParticipantInfoDTO getHighestKillParticipantInfo(String puuid);

    public List<AppParticipantInfoDTO> getAllParticipantInfo();

}
