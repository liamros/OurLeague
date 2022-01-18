package it.kekw.clowngg.match;

import it.kekw.clowngg.common.RestAdapter;
import it.kekw.clowngg.riot.RiotMgrInterface;
import it.kekw.clowngg.riot.dto.SummonerDTO;


public class ClownMatchMgrImpl implements ClownMatchMgr {

    private RiotMgrInterface riotMgr;

    private String authHeaderKey;

    private String apiToken;


    @Override
    public String ping() {
        return "true";
    }

    @Override
    public String insertSummoner(String summonerName) {
        SummonerDTO dto = null;
        try {
            RestAdapter.addHeader(authHeaderKey, apiToken);
            dto = riotMgr.getSummonerInfoBySummonerName(summonerName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return dto.getPuuid();
    }


    public void setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
    }


    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    

}
