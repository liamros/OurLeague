package it.our.league.app;

import java.util.List;

import it.our.league.app.controller.dto.AppShowCaseDetailDTO;

public interface LeagueAppManager {
    
    public List<AppShowCaseDetailDTO> getShowCaseDetails();

    public void updateShowCaseDetails();

    public List<Float> getWinRateByPuuid(String puuid);

    public String asyncronousDataRefresh();

}
