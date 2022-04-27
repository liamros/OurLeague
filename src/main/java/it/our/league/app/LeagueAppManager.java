package it.our.league.app;

import java.util.List;

import it.our.league.app.controller.dto.AppShowCaseRankingDTO;

public interface LeagueAppManager {
    
    public List<AppShowCaseRankingDTO> getShowCaseRankings();

    public void updateShowCaseRankings();

    public List<Float> getWinRateByPuuid(String puuid);

    public String asyncronousDataRefresh();

}
