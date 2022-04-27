package it.our.league.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.our.league.app.LeagueAppManager;
import it.our.league.app.controller.dto.AppShowCaseRankingDTO;

@RestController
// CrossOrigin only for development purposes
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("app")
public class LeagueAppController implements LeagueAppManager {

    @Autowired
    private LeagueAppManager leagueAppImpl;

    @Override
    @GetMapping("/getShowCaseDetails")
    public List<AppShowCaseRankingDTO> getShowCaseRankings() {
        return leagueAppImpl.getShowCaseRankings();
    }

    @Override
    @GetMapping("/updateShowCaseDetails")
    public void updateShowCaseRankings() {
        leagueAppImpl.updateShowCaseRankings();
    }

    @Override
    @GetMapping("/getWinRate")
    public List<Float> getWinRateByPuuid(@RequestParam String puuid) {
        return leagueAppImpl.getWinRateByPuuid(puuid);
    }

    @Override
    @GetMapping("/asyncronousDataRefresh")
    public String asyncronousDataRefresh() {
        return leagueAppImpl.asyncronousDataRefresh();
    }
    
}
