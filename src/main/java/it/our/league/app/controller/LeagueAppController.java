package it.our.league.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.our.league.app.LeagueAppManager;
import it.our.league.app.controller.dto.AppLineChartWrapperDTO;
import it.our.league.app.controller.dto.AppShowCaseDTO;

@RestController
// CrossOrigin only for development purposes
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("app")
public class LeagueAppController implements LeagueAppManager {

    @Autowired
    private LeagueAppManager leagueAppImpl;

    @Override
    @GetMapping("/getShowCaseRankings")
    public List<AppShowCaseDTO> getShowCaseRankings() {
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
    public AppLineChartWrapperDTO getWinratePerMinuteChart() {
        return leagueAppImpl.getWinratePerMinuteChart();
    }

    @Override
    @GetMapping("/getVisionPerMinuteChart")
    public AppLineChartWrapperDTO getVisionPerMinuteChart() {
        return leagueAppImpl.getVisionPerMinuteChart();
    }

    @Override
    @GetMapping("/getGamesPerMinuteChart")
    public AppLineChartWrapperDTO getGamesPerMinuteChart() {
        return leagueAppImpl.getGamesPerMinuteChart();
    }

    @Override
    @GetMapping("/getAllHomeCharts")
    public List<AppLineChartWrapperDTO> getAllHomeCharts() {
        return leagueAppImpl.getAllHomeCharts();
    }

}
