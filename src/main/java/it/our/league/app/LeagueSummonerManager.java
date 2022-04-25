package it.our.league.app;

import java.io.IOException;
import java.util.List;

import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.riot.dto.Summoner;

public interface LeagueSummonerManager {

    public String ping();

    public Summoner insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

    public List<AppRankInfoDTO> getRanksByPuuid(String puuid);

    public void updateAllRanks();

    public void updateAllSummoners();

    public byte[] getProfileIconImage(String profileIconNumber) throws IOException;

    public Integer getSummonerIdByPuuid(String puuid);

    public List<Summoner> getAllSummoners();

    public AppSummonerDTO getLowestWinrateSummoner();

}
