package it.our.league.app;

import java.util.List;
import java.util.Map;

import it.our.league.app.controller.dto.AppLineChartDTO;
import it.our.league.app.controller.dto.AppShowCaseRankingDTO;

public interface LeagueAppManager {
    
    public Map<String, List<AppShowCaseRankingDTO>> getShowCaseRankings();

    public void updateShowCaseRankings();

    public List<Float> getWinRateByPuuid(String puuid);

    public String asyncronousDataRefresh();

    public List<AppLineChartDTO> getWinrateAllLineCharts();

}
