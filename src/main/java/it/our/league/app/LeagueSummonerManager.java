package it.our.league.app;

import java.io.IOException;
import java.util.List;

import it.our.league.app.controller.dto.ShowCaseDetailDTO;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Summoner;

public interface LeagueSummonerManager {

    public String ping();

    public Summoner insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

    public void updateAllRanks();

    public void updateAllSummoners();

    public List<Float> getWinRateBySummInfoId(Integer summInfoId);

    public List<Match> getMatchesByPuuid(String puuid, String queueType, Integer count);

    public List<ShowCaseDetailDTO> getShowCaseDetails();

    public void updateShowCaseDetails();

    public byte[] getProfileIconImage(String profileIconNumber) throws IOException;

    public Integer getSummonerIdByPuuid(String puuid);

    public List<Summoner> getAllSummoners();

}
