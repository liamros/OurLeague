package it.kekw.clowngg.match;

public interface ClownMatchMgr {
    

    public String ping();

    public String insertSummoner(String summonerName);

    public String getGameNameByPuuid(String puuid);

}
