package it.kekw.clowngg.match.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.kekw.clowngg.common.RestAdapter;
import it.kekw.clowngg.match.ClownMatchMgr;
import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA;
import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;
import it.kekw.clowngg.match.impl.persistence.repository.RankInfoRepository;
import it.kekw.clowngg.match.impl.persistence.repository.SummonerInfoRepository;
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
            SummonerInfoJPA summonerJpa = ClownMatchMgrUtility.generateSummonerInfoJpa(summonerDto);
            summonerJpa = summonerRepository.save(summonerJpa);
            LOGGER.info("Persisted {}", summonerJpa);
            for (RankedInfoDTO rankedInfoDto : rankedInfoDtos) {
                RankInfoJPA rankJpa;
                try {
                    rankJpa = ClownMatchMgrUtility.generateRankedInfoJpa(rankedInfoDto, summonerJpa.getId());
                } catch (IllegalArgumentException e) {continue;}
                
                rankJpa = rankRepository.save(rankJpa);
                LOGGER.info("Persisted {}", rankJpa);
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
