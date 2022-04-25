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
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Match;

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
    public int completeMatchData(@RequestBody String matchId) {
        return leagueMatchImpl.completeMatchData(matchId);
    }

    @Override
    public Match getMatchData(String matchId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @GetMapping("/populateMatchInfo")
    public int populateMatchInfo() {
        return leagueMatchImpl.populateMatchInfo();
    }

    @Override
    @GetMapping("/getMatches")
    public List<Match> getMatchesByPuuid(@RequestParam String puuid, String queueType, Integer count) {
        return leagueMatchImpl.getMatchesByPuuid(puuid, queueType, count);
    }
    
}
