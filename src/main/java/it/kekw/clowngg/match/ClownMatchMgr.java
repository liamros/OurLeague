package it.kekw.clowngg.match;

import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface ClownMatchMgr {
    

    public String ping();

    public SummonerDTO insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

}
