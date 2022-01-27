package it.kekw.clowngg.match;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.kekw.clowngg.common.RestAdapter;
import it.kekw.clowngg.common.constants.RankedQueueType;
import it.kekw.clowngg.match.persistence.jpa.RankInfoJPA;
import it.kekw.clowngg.match.persistence.jpa.SummonerInfoJPA;
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
    private SummonerInfoRepository summonerRepository;
    @Autowired
    private RankInfoRepository rankRepository;

    @Override
    public String ping() {
        return "true";
    }

    @Override
    @Transactional
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
            summonerJpa.setId(summonerJpa.getId());
            summonerJpa.setEncryptedSummonerId(summonerDto.getId());
            summonerJpa.setPuuid(summonerDto.getPuuid());
            summonerJpa.setAccountId(summonerDto.getAccountId());
            summonerJpa = summonerRepository.save(summonerJpa);
            LOGGER.info("Persisted {}", summonerJpa);
            for (RankedInfoDTO rankedInfoDto : rankedInfoDtos) {
                Integer queueTypeId;
                try {
                    queueTypeId = RankedQueueType.valueOf(rankedInfoDto.getQueueType()).id();
                } catch (IllegalArgumentException e) {continue;}
                RankInfoJPA rankedJpa = new RankInfoJPA();
                rankedJpa.setSummInfoId(summonerJpa.getId());
                rankedJpa.setTier(rankedInfoDto.getTier());
                rankedJpa.setDivision(rankedInfoDto.getRank());
                rankedJpa.setLp(rankedInfoDto.getLeaguePoints());
                rankedJpa.setWins(rankedInfoDto.getWins());
                rankedJpa.setLosses(rankedInfoDto.getLosses());
                rankedJpa.setQueueTypeId(queueTypeId);
                rankedJpa = rankRepository.save(rankedJpa);
                LOGGER.info("Persisted {}", rankedJpa);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return summonerDto;
    }

    @Override
    public String getGameNameByPuuid(String puuid) {
        SummonerInfoJPA jpa = summonerRepository.findByPuuid(puuid);
        return jpa.getPuuid();
    }




    
    public void setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
