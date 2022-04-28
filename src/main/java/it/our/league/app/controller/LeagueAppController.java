package it.our.league.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.our.league.app.LeagueAppManager;
import it.our.league.app.controller.dto.AppLineChartDTO;
import it.our.league.app.controller.dto.AppShowCaseRankingDTO;

@RestController
// CrossOrigin only for development purposes
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("app")
public class LeagueAppController implements LeagueAppManager {

    @Autowired
    private LeagueAppManager leagueAppImpl;

    @Override
    @GetMapping("/getShowCaseRankings")
    public Map<String, List<AppShowCaseRankingDTO>> getShowCaseRankings() {
        return leagueAppImpl.getShowCaseRankings();
    }

    @Override
    @GetMapping("/updateShowCaseRankings")
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

    @Override
    @GetMapping("/getWinrateAllLineCharts")
    public List<AppLineChartDTO> getWinrateAllLineCharts() {
        return leagueAppImpl.getWinrateAllLineCharts();
    }
    
}
