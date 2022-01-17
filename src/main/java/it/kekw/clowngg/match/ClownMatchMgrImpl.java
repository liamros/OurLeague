package it.kekw.clowngg.match;

import org.springframework.beans.factory.annotation.Autowired;

import it.kekw.clowngg.riot.api.RiotAPIMgr;
import it.kekw.clowngg.riot.api.dto.SummonerDTO;

public class ClownMatchMgrImpl implements ClownMatchMgr {

    @Autowired
    private RiotAPIMgr riotMgr;

    private String matchAPI;

    @Override
    public String ping() {
        return matchAPI;
    }

    @Override
    public String insertSummoner(String summonerName) {
        SummonerDTO dto = null;
        try {
            dto = riotMgr.getSummonerInfoBySummonerName(summonerName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return dto.getPuuid();
    }








    public String getMatchAPI() {
        return matchAPI;
    }

    public void setMatchAPI(String matchAPI) {
        this.matchAPI = matchAPI;
    }

}
