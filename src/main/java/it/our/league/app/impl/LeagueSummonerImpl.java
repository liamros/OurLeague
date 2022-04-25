package it.our.league.app.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.our.league.app.LeagueSummonerManager;
import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.RankInfoJPA;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.app.impl.persistence.repository.RankInfoRepository;
import it.our.league.app.impl.persistence.repository.SummonerInfoRepository;
import it.our.league.app.utility.LeagueAppUtility;
import it.our.league.riot.IDdragon;
import it.our.league.riot.RiotManagerInterface;
import it.our.league.riot.dto.RankInfo;
import it.our.league.riot.dto.Summoner;
import net.coobird.thumbnailator.Thumbnails;

public class LeagueSummonerImpl implements LeagueSummonerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueSummonerImpl.class);

    private RiotManagerInterface riotManager;

    private IDdragon ddragonApi;

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
    public AppSummonerDTO insertSummoner(String summonerName) {
        AppSummonerDTO response = null;
        try {
            Summoner summonerDto = riotManager.getAccountInfoBySummonerName(summonerName);
            List<RankInfo> rankedInfoDtos = riotManager.getRankInfoByEncryptedSummonerId(summonerDto.getEncryptedSummonerId());
            SummonerInfoJPA summonerJpa = LeagueAppUtility.generateSummonerInfoJpa(summonerDto);
            summonerJpa = summonerRepository.save(summonerJpa);
            response = LeagueAppUtility.generateAppSummonerDto(summonerJpa);
            LOGGER.info("INFO: Persisted {}", summonerJpa);
            List<RankInfoJPA> rankJpas = new ArrayList<>();
            for (RankInfo rankedInfoDto : rankedInfoDtos) {
                RankInfoJPA rankJpa;
                rankJpa = LeagueAppUtility.generateRankInfoJpa(rankedInfoDto, summonerJpa.getId());
                if (rankJpa == null)
                    continue;
                rankJpas.add(rankJpa);
                response.addRank(LeagueAppUtility.generateAppRankInfoDto(rankJpa));
                LOGGER.info("INFO: Entity to persist {}", rankJpa);
            }
            rankRepository.saveAll(rankJpas);
        } catch (Exception e) {
            LOGGER.error("ERROR: Error while performing insertSummoner", e);
            throw new RuntimeException();
        }
        return response;
    }

    @Override
    public String getGameNameByPuuid(String puuid) {
        SummonerInfoJPA jpa = summonerRepository.findByPuuid(puuid);
        return jpa.getGameName();
    }

    @Override
    @Transactional
    public void updateAllRanks() {
        Iterable<SummonerInfoJPA> list = summonerRepository.findAll();
        this.updateAllRanks(list);
    }

    @Transactional
    private void updateAllRanks(Iterable<SummonerInfoJPA> summInfoJpas) {

        List<RankInfoJPA> jpas = new ArrayList<>();
        for (SummonerInfoJPA summonerJpa : summInfoJpas) {
            Integer id = summonerJpa.getId();
            String encryptedSummonerId = summonerJpa.getEncryptedSummonerId();
            List<RankInfo> rankInfoDtos = riotManager.getRankInfoByEncryptedSummonerId(encryptedSummonerId);
            for (RankInfo rankDto : rankInfoDtos) {
                RankInfoJPA rankJpa = LeagueAppUtility.generateRankInfoJpa(rankDto, id);
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
    @Transactional
    public void updateAllSummoners() {
        Iterable<SummonerInfoJPA> list = summonerRepository.findAll();
        for (SummonerInfoJPA jpa : list) {
            Summoner dto = riotManager.getAccountInfoBySummonerName(jpa.getGameName());
            jpa.setSummonerLevel(dto.getSummonerLevel());
            jpa.setSummonerIconId(dto.getProfileIconId());
            summonerRepository.updateSummonerLvlAndIcon(jpa.getId(), jpa.getSummonerLevel(), jpa.getSummonerIconId());
            LOGGER.info("INFO: Updated SummonerInfo {}", jpa);
        }
        updateAllRanks(list);
    }

    @Override
    public byte[] getProfileIconImage(String profileIconNumber) throws IOException {
        byte[] byteArray;
        try {
            byteArray = ddragonApi.getProfileIcon(profileIconNumber);
        } catch (Exception e) {
            if (e.getCause().getMessage().contains("Status Message: 403")) 
                byteArray = ddragonApi.getProfileIcon("0");
            else
                throw e;
        }
        InputStream in = new ByteArrayInputStream(byteArray);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(in)
                .size(100, 100)
                .outputFormat("PNG")
                .outputQuality(1)
                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        return data;
    }

    @Override
    public Integer getSummonerIdByPuuid(String puuid) {
        return summonerRepository.getSummonerIdByPuuid(puuid);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
    public List<AppSummonerDTO> getAllSummoners() {
        List<AppSummonerDTO> out = new ArrayList<>();
        summonerRepository.findAll().forEach(jpa -> out.add(LeagueAppUtility.generateAppSummonerDto(jpa, jpa.getRankInfo())));
        return out;
    }

    @Override
    public List<AppRankInfoDTO> getRanksByPuuid(String puuid) {
        Integer summInfoId = summonerRepository.getSummonerIdByPuuid(puuid);
        List<AppRankInfoDTO> response = new ArrayList<>();
        rankRepository.findBySummInfoId(summInfoId).forEach(jpa -> {
            response.add(LeagueAppUtility.generateAppRankInfoDto(jpa));
        });;
        return response;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
    public AppSummonerDTO getLowestWinrateSummoner() {
        RankInfoJPA rank = rankRepository.getLowerWinRate();
        SummonerInfoJPA summoner = summonerRepository.findById(rank.getSummInfoId()).get();
        AppSummonerDTO dto = LeagueAppUtility.generateAppSummonerDto(summoner, summoner.getRankInfo());
        return dto;
    }

}
