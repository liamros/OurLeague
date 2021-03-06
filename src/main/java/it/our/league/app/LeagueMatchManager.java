package it.our.league.app;

import java.util.List;

import it.our.league.app.controller.dto.AppParticipantInfoDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Participant;

/**
 * Class which job is to manage and fetch data about matches and games
 */
public interface LeagueMatchManager {
    
    public int updateMatchHistory(AppSummonerDTO summoner);

    public int populateMatchData(String matchId);

    public int populateAllMatchData();

    public List<Match> getAllMatchesByPuuid(String puuid);

    public int alignRelSummonerMatches();

    public List<Participant> getAllMatchStatisticsByPuuid(String puuid);

    public AppParticipantInfoDTO getParticipantInfo(String matchId, String puuid);

    public List<AppParticipantInfoDTO> getAllPopulatedParticipantInfoByPuuid(String puuid);
    
	public List<AppParticipantInfoDTO> getAllPopulatedParticipantInfoByPuuid(String puuid, Integer queueTypeId);

    public AppParticipantInfoDTO getHighestKillParticipantInfo(String puuid);
    
    public AppParticipantInfoDTO getHighestKillParticipantInfo(String puuid, Integer queueTypeId);

    public List<AppParticipantInfoDTO> getAllPopulatedParticipantInfo();

}
