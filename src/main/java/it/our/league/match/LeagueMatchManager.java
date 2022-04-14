package it.our.league.match;

import java.io.IOException;
import java.util.List;

import it.our.league.match.controller.dto.ShowCaseDetailDTO;
import it.our.league.riot.dto.MatchDTO;
import it.our.league.riot.dto.SummonerDTO;

public interface LeagueMatchManager {

    public String ping();

    public SummonerDTO insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

    public void updateAllRanks();

    public void updateAllSummoners();

    public List<Float> getWinRateBySummInfoId(Integer summInfoId);

    public List<MatchDTO> getMatchesByPuuid(String puuid, String queueType, Integer count);

    public List<ShowCaseDetailDTO> getShowCaseDetails();

    public void updateShowCaseDetails();

    public byte[] getProfileIconImage(String profileIconNumber) throws IOException;

}
