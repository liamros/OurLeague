package it.kekw.clowngg.match;

import org.springframework.beans.factory.annotation.Autowired;

import it.kekw.clowngg.common.RestAdapter;
import it.kekw.clowngg.match.persistence.jpa.AccountJPA;
import it.kekw.clowngg.match.persistence.repository.AccountRepository;
import it.kekw.clowngg.riot.RiotMgrInterface;
import it.kekw.clowngg.riot.dto.SummonerDTO;


public class ClownMatchMgrImpl implements ClownMatchMgr {

    private RiotMgrInterface riotMgr;

    private String authHeaderKey;

    private String apiToken;

    @Autowired
    private AccountRepository accountRepository;


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
            AccountJPA jpa = new AccountJPA();
            jpa.setPuuid(dto.getPuuid());
            jpa.setGameName(dto.getGameName());
            accountRepository.save(jpa);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return dto.getPuuid();
    }

    @Override
    public String getGameNameByPuuid(String puuid) {
        AccountJPA acc = accountRepository.findByPuuid(puuid);   
        return acc.getGameName();
    }

    


    public void setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
    }


    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
