package it.our.league.app.controller;

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

import it.our.league.app.LeagueSummonerManager;
import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Summoner;

@RestController
// CrossOrigin only for development purposes
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("summoner")
public class LeagueSummonerController implements LeagueSummonerManager {

    @Autowired
    private LeagueSummonerManager leagueSummonerImpl;

    @Override
    @GetMapping("/ping")
    public String ping() {
        return leagueSummonerImpl.ping();
    }

    @Override
    @PutMapping("/insertSummoner")
    public Summoner insertSummoner(@RequestBody String summonerName) {
        return leagueSummonerImpl.insertSummoner(summonerName);
    }

    @Override
    @GetMapping("/getGameName")
    public String getGameNameByPuuid(@RequestParam String puuid) {
        return leagueSummonerImpl.getGameNameByPuuid(puuid);
    }

    @Override
    @GetMapping("/updateAllRanks")
    public void updateAllRanks() {
        leagueSummonerImpl.updateAllRanks();
    }
    
    @Override
    @GetMapping("/updateAllSummoners")
    public void updateAllSummoners() {
        leagueSummonerImpl.updateAllSummoners();
    }

    @Override
    @GetMapping(value = "/getProfileIcon", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getProfileIconImage(@RequestParam String profileIconNumber) throws IOException {
        return leagueSummonerImpl.getProfileIconImage(profileIconNumber);
    }

    @Override
    @GetMapping("/getSummonerIdByPuuid")
    public Integer getSummonerIdByPuuid(String puuid) {
        return leagueSummonerImpl.getSummonerIdByPuuid(puuid);
    }

    @Override
    @GetMapping("/getAllSummoners")
    public List<Summoner> getAllSummoners() {
        return leagueSummonerImpl.getAllSummoners();
    }

    @Override
    @GetMapping("/getRanksByPuuid")
    public List<AppRankInfoDTO> getRanksByPuuid(@RequestParam String puuid) {
        return leagueSummonerImpl.getRanksByPuuid(puuid);
    }

    @Override
    @GetMapping("/getLowestWinrateSummoner")
    public AppSummonerDTO getLowestWinrateSummoner() {
        return leagueSummonerImpl.getLowestWinrateSummoner();
    }


}
