package it.our.league.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.controller.dto.AppParticipantInfoDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Participant;

// TODO add basic auth
@RestController
@RequestMapping("match")
public class LeagueMatchController implements LeagueMatchManager {

    @Autowired
    private LeagueMatchManager leagueMatchImpl;

    @Override
    @PostMapping("/updateMatchHistory")
    public int updateMatchHistory(@RequestBody AppSummonerDTO summoner) {
        return leagueMatchImpl.updateMatchHistory(summoner);
    }

    @PostMapping("/completeMatchData")
    public int populateMatchData(@RequestBody String matchId) {
        return leagueMatchImpl.populateMatchData(matchId);
    }

    @Override
    @GetMapping("/populateMatchInfo")
    public int populateAllMatchData() {
        return leagueMatchImpl.populateAllMatchData();
    }

    @Override
    @GetMapping("/getAllMatchesByPuuid")
    public List<Match> getAllMatchesByPuuid(@RequestParam String puuid) {
        return leagueMatchImpl.getAllMatchesByPuuid(puuid);
    }

    @Override
    @GetMapping("/alignRelSummonerMatches")
    public int alignRelSummonerMatches() {
        return leagueMatchImpl.alignRelSummonerMatches();
    }

    @Override
    @GetMapping("/getAllMatchStatisticsByPuuid")
    public List<Participant> getAllMatchStatisticsByPuuid(@RequestParam String puuid) {
        return leagueMatchImpl.getAllMatchStatisticsByPuuid(puuid);
    }

    @Override
    @GetMapping("/getParticipantInfo")
    public AppParticipantInfoDTO getParticipantInfo(@RequestParam String matchId, String puuid) {
        return leagueMatchImpl.getParticipantInfo(matchId, puuid);
    }

    @Override
    @GetMapping("/getAllPopulatedParticipantInfoByPuuid")
    public List<AppParticipantInfoDTO> getAllPopulatedParticipantInfoByPuuid(@RequestParam String puuid) {
        return leagueMatchImpl.getAllPopulatedParticipantInfoByPuuid(puuid);
    }

    @Override
    @GetMapping("/getHighestKillParticipantInfo")
    public AppParticipantInfoDTO getHighestKillParticipantInfo(String puuid) {
        return leagueMatchImpl.getHighestKillParticipantInfo(puuid);
    }

    @Override
    @GetMapping("/getAllPopulatedParticipantInfo")
    public List<AppParticipantInfoDTO> getAllPopulatedParticipantInfo() {
        return leagueMatchImpl.getAllPopulatedParticipantInfo();
    }

	@Override
	public List<AppParticipantInfoDTO> getAllPopulatedParticipantInfoByPuuid(@RequestParam String puuid, Integer queueTypeId) {
		return leagueMatchImpl.getAllPopulatedParticipantInfoByPuuid(puuid, queueTypeId);
	}

	@Override
	public AppParticipantInfoDTO getHighestKillParticipantInfo(@RequestParam String puuid, Integer queueTypeId) {
		return leagueMatchImpl.getHighestKillParticipantInfo(puuid, queueTypeId);
	}
    
}
