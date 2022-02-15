package it.kekw.clowngg.match;

import java.io.IOException;
import java.util.List;

import it.kekw.clowngg.match.controller.dto.ShowCaseDetailDTO;
import it.kekw.clowngg.riot.dto.MatchDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface ClownMatchMgr {

    public String ping();

    public SummonerDTO insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

    public void updateAllRanks();

    public List<Float> getWinRateBySummInfoId(Integer summInfoId);

    public List<MatchDTO> getMatchesByPuuid(String puuid, String queueType, Integer count);

    public List<ShowCaseDetailDTO> getShowCaseDetails();

    public void updateShowCaseDetails();

    public byte[] getProfileIconImage(String profileIconNumber) throws IOException;

}
