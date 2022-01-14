package it.kekw.clowngg.match;

public class ClownMatchMgrImpl implements ClownMatchMgr {

    private String matchAPI;

    @Override
    public String ping() {
        return "true";
    }











    public String getMatchAPI() {
        return matchAPI;
    }

    public void setMatchAPI(String matchAPI) {
        this.matchAPI = matchAPI;
    }
    
}
