package it.our.league.match.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.our.league.match.LeagueMatchManager;
import it.our.league.match.controller.dto.ShowCaseDetailDTO;
import it.our.league.match.impl.LeagueMatchImpl;
import it.our.league.riot.dto.MatchDTO;
import it.our.league.riot.dto.SummonerDTO;

@RestController
// CrossOrigin only for development purposes
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("match")
public class LeagueMatchController implements LeagueMatchManager {

    @Autowired
    private LeagueMatchImpl leagueMatchImpl;

    @Override
    @GetMapping("/ping")
    public String ping() {
        return leagueMatchImpl.ping();
    }

    @Override
    @PutMapping("/insertSummoner")
    public SummonerDTO insertSummoner(@RequestBody String summonerName) {
        return leagueMatchImpl.insertSummoner(summonerName);
    }

    @Override
    @GetMapping("/getGameName")
    public String getGameNameByPuuid(@RequestParam String puuid) {
        return leagueMatchImpl.getGameNameByPuuid(puuid);
    }

    @Override
    @GetMapping("/getWinRate")
    public List<Float> getWinRateBySummInfoId(@RequestParam Integer summInfoId) {
        return leagueMatchImpl.getWinRateBySummInfoId(summInfoId);
    }

    @Override
    @GetMapping("/getMatches")
    public List<MatchDTO> getMatchesByPuuid(@RequestParam String puuid, String queueType, Integer count) {
        return leagueMatchImpl.getMatchesByPuuid(puuid, queueType, count);
    }

    @Override
    @GetMapping("/updateAllRanks")
    public void updateAllRanks() {
        leagueMatchImpl.updateAllRanks();
    }
    
    @Override
    @GetMapping("/updateAllSummoners")
    public void updateAllSummoners() {
        leagueMatchImpl.updateAllSummoners();
    }

    @Override
    @GetMapping("/getShowCaseDetails")
    public List<ShowCaseDetailDTO> getShowCaseDetails() {
        return leagueMatchImpl.getShowCaseDetails();
    }

    @Override
    @GetMapping("/updateShowCaseDetails")
    public void updateShowCaseDetails() {
        leagueMatchImpl.updateShowCaseDetails();
    }

    @Override
    @GetMapping(value = "/getProfileIcon", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getProfileIconImage(@RequestParam String profileIconNumber) throws IOException {
        return leagueMatchImpl.getProfileIconImage(profileIconNumber);
    }


}
