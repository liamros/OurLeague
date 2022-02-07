package it.kekw.clowngg.match.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.kekw.clowngg.match.ClownMatchMgr;
import it.kekw.clowngg.match.controller.dto.ShowCaseDetailDTO;
import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA;
import it.kekw.clowngg.match.impl.persistence.entity.ShowCaseDetailJPA;
import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;
import it.kekw.clowngg.match.impl.persistence.repository.RankInfoRepository;
import it.kekw.clowngg.match.impl.persistence.repository.ShowCaseDetailRepository;
import it.kekw.clowngg.match.impl.persistence.repository.SummonerInfoRepository;
import it.kekw.clowngg.riot.RiotMgrInterface;
import it.kekw.clowngg.riot.dto.RankInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public class ClownMatchMgrImpl implements ClownMatchMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClownMatchMgrImpl.class);

    private RiotMgrInterface riotManager;

    @Autowired
    private SummonerInfoRepository summonerRepository;
    @Autowired
    private RankInfoRepository rankRepository;
    @Autowired
    private ShowCaseDetailRepository showCaseDetailRepository;

    @Override
    public String ping() {
        return "true";
    }

    @Override
    @Transactional
    public SummonerDTO insertSummoner(String summonerName) {
        SummonerDTO summonerDto = null;
        try {
            summonerDto = riotManager.getAccountInfoBySummonerName(summonerName);
            List<RankInfoDTO> rankedInfoDtos = riotManager.getRankInfoByEncryptedSummonerId(summonerDto.getId());
            SummonerInfoJPA summonerJpa = ClownMatchMgrUtility.generateSummonerInfoJpa(summonerDto);
            summonerJpa = summonerRepository.save(summonerJpa);
            LOGGER.info("INFO: Persisted {}", summonerJpa);
            List<RankInfoJPA> rankJpas = new ArrayList<>();
            for (RankInfoDTO rankedInfoDto : rankedInfoDtos) {
                RankInfoJPA rankJpa;
                rankJpa = ClownMatchMgrUtility.generateRankedInfoJpa(rankedInfoDto, summonerJpa.getId());
                if (rankJpa == null)
                    continue;
                rankJpas.add(rankJpa);
                LOGGER.info("INFO: Entity to persist {}", rankJpa);
            }
            rankRepository.saveAll(rankJpas);
        } catch (Exception e) {
            LOGGER.error("ERROR: Error while performing insertSummoner", e);
            throw new RuntimeException();
        }
        return summonerDto;
    }

    @Override
    public String getGameNameByPuuid(String puuid) {
        SummonerInfoJPA jpa = summonerRepository.findByPuuid(puuid);
        return jpa.getGameName();
    }   
    
    @Override
    public List<Float> getWinRateBySummInfoId(Integer summInfoId) {
        List<Float> list = new ArrayList<Float>();
        List<RankInfoJPA> jpas = rankRepository.findBySummInfoId(summInfoId);
        for (RankInfoJPA rankInfoJPA : jpas) {
            list.add(rankInfoJPA.getWinrate());
        } 
        return list;
    }

    @Override
    @Transactional
    public void updateAllRanks() {

        Iterable<SummonerInfoJPA> list = summonerRepository.findAll();
        List<RankInfoJPA> jpas = new ArrayList<>();
        for (SummonerInfoJPA summonerJpa : list) {
            Integer id = summonerJpa.getId();
            String encryptedSummonerId = summonerJpa.getEncryptedSummonerId();
            List<RankInfoDTO> rankInfoDtos = riotManager.getRankInfoByEncryptedSummonerId(encryptedSummonerId);
            for (RankInfoDTO rankDto : rankInfoDtos) {
                RankInfoJPA rankJpa = ClownMatchMgrUtility.generateRankedInfoJpa(rankDto, id);
                if (rankJpa == null)
                    continue;
                jpas.add(rankJpa);
                LOGGER.info("INFO: Entity to persist {}", rankJpa);
            }

        }
        rankRepository.saveAll(jpas);
        LOGGER.info("INFO: Persisted rankInfoJpas");
    }

    @Override
    public List<ShowCaseDetailDTO> getShowCaseDetails() {
        
        Iterable<ShowCaseDetailJPA> list = showCaseDetailRepository.findAll();
        List<ShowCaseDetailDTO> dtos = new ArrayList<>(); 
        for (ShowCaseDetailJPA showCaseDetailJpa : list) {           
            dtos.add(ClownMatchMgrUtility.generateShowCaseDetailDTO(showCaseDetailJpa));
        }

        return dtos;
    
    }

    @Override
    public List<ShowCaseDetailDTO> setShowCaseDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getProfileIconImage(Integer profileIconNumber) {
        // TODO Auto-generated method stub
        return null;
    }


}
