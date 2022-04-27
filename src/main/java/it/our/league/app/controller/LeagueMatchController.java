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

// TODO add security
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
    public Match getMatchData(String matchId) {
        // TODO Auto-generated method stub
        return null;
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
    @GetMapping("/getAllParticipantInfoByPuuid")
    public List<AppParticipantInfoDTO> getAllParticipantInfoByPuuid(@RequestParam String puuid) {
        return leagueMatchImpl.getAllParticipantInfoByPuuid(puuid);
    }

    @Override
    @GetMapping("/getHighestKillParticipantInfo")
    public AppParticipantInfoDTO getHighestKillParticipantInfo(String puuid) {
        return leagueMatchImpl.getHighestKillParticipantInfo(puuid);
    }
    
}
