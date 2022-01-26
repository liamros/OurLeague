package it.kekw.clowngg.match;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.kekw.clowngg.common.RestAdapter;
import it.kekw.clowngg.common.constants.RankedQueueType;
import it.kekw.clowngg.match.persistence.jpa.AccountInfoJPA;
import it.kekw.clowngg.match.persistence.jpa.QueueJPA;
import it.kekw.clowngg.match.persistence.jpa.RankInfoJPA;
import it.kekw.clowngg.match.persistence.jpa.SummonerInfoJPA;
import it.kekw.clowngg.match.persistence.repository.AccountInfoRepository;
import it.kekw.clowngg.match.persistence.repository.RankInfoRepository;
import it.kekw.clowngg.match.persistence.repository.SummonerInfoRepository;
import it.kekw.clowngg.riot.RiotMgrInterface;
import it.kekw.clowngg.riot.dto.RankedInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public class ClownMatchMgrImpl implements ClownMatchMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClownMatchMgrImpl.class);

    private RiotMgrInterface riotMgr;

    private String authHeaderKey;

    private String apiToken;

    @Autowired
    private AccountInfoRepository accountRepository;
    @Autowired
    private SummonerInfoRepository summonerRepository;
    @Autowired
    private RankInfoRepository rankRepository;

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
            RestAdapter.addHeader(authHeaderKey, apiToken);
            List<RankedInfoDTO> rankedInfoDtos = riotMgr.getRankedInfoByEncryptedSummonerId(summonerDto.getId());
            SummonerInfoJPA summonerJpa = new SummonerInfoJPA();
            summonerJpa.setGameName(summonerDto.getName());
            summonerJpa.setSummonerIconId(summonerDto.getProfileIconId());
            summonerJpa.setSummonerLevel(summonerDto.getSummonerLevel());
            summonerJpa = summonerRepository.save(summonerJpa);
            LOGGER.info("Persisted {}", summonerJpa);
            for (RankedInfoDTO rankedInfoDto : rankedInfoDtos) {
                RankInfoJPA rankedJpa = new RankInfoJPA();
                rankedJpa.setId(summonerJpa.getId());
                rankedJpa.setTier(rankedInfoDto.getTier());
                rankedJpa.setDivision(rankedInfoDto.getRank());
                rankedJpa.setLp(rankedInfoDto.getLeaguePoints());
                rankedJpa.setWins(rankedInfoDto.getWins());
                rankedJpa.setLosses(rankedInfoDto.getLosses());
                rankedJpa.setQueueTypeId(RankedQueueType.valueOf(rankedInfoDto.getQueueType()).id());
                rankedJpa = rankRepository.save(rankedJpa);
                LOGGER.info("Persisted {}", rankedJpa);
            }
            AccountInfoJPA accountJpa = new AccountInfoJPA();
            accountJpa.setId(summonerJpa.getId());
            accountJpa.setEncryptedSummonerId(summonerDto.getId());
            accountJpa.setPuuid(summonerDto.getPuuid());
            accountJpa.setAccountId(summonerDto.getAccountId());
            accountJpa = accountRepository.save(accountJpa);
            LOGGER.info("Persisted {}", accountJpa);
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
