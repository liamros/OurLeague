package it.kekw.clowngg.match;

import java.util.List;

import it.kekw.clowngg.match.controller.dto.ShowCaseDetailDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface ClownMatchMgr {
    

    public String ping();

    public SummonerDTO insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

    public void updateAllRanks();

    public List<Float> getWinRateBySummInfoId(Integer summInfoId);

    public List<ShowCaseDetailDTO> getShowCaseDetails();

    public List<ShowCaseDetailDTO> setShowCaseDetails(); 

}
