package it.kekw.clowngg.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.kekw.clowngg.common.RestAdapter;
import it.kekw.clowngg.common.constants.RankedQueueType;
import it.kekw.clowngg.match.persistence.jpa.AccountInfoJPA;
import it.kekw.clowngg.match.persistence.repository.AccountRepository;
import it.kekw.clowngg.riot.RiotMgrInterface;
import it.kekw.clowngg.riot.dto.RankedInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public class ClownMatchMgrImpl implements ClownMatchMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClownMatchMgrImpl.class);

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
    public SummonerDTO insertSummoner(String summonerName) {
        SummonerDTO summonerDto = null;
        try {
            RestAdapter.addHeader(authHeaderKey, apiToken);
            summonerDto = riotMgr.getAccountInfoBySummonerName(summonerName);
            RankedInfoDTO rankedInfoDto = riotMgr.getRankedInfoByEncryptedSummonerId(summonerDto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return summonerDto;
    }

    @Override
    public String getGameNameByPuuid(String puuid) {
        AccountInfoJPA acc = accountRepository.findByPuuid(puuid);
        return acc.getEncryptedSummonerId();
    }




    
    public void setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
