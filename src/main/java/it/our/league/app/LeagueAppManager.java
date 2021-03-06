package it.our.league.app;

import java.util.List;
import java.util.Map;

import it.our.league.app.controller.dto.AppLineChartWrapperDTO;
import it.our.league.app.controller.dto.AppShowCaseDTO;

/**
 * Main Application Class which processes data fetched from the other mangagers,
 * and processes it for the main features of the webapp
 */
public interface LeagueAppManager {
    
    public List<AppShowCaseDTO> getShowCaseRankings();

    public void updateShowCaseRankings();

    public List<Float> getWinRateByPuuid(String puuid);

    public String asyncronousDataRefresh();

    public Map<String, List<AppLineChartWrapperDTO>> getAllHomeCharts(); 

    public AppLineChartWrapperDTO getWinratePerMinuteChart();

    public AppLineChartWrapperDTO getVisionPerMinuteChart();

    public AppLineChartWrapperDTO getGamesPerMinuteChart();

}
