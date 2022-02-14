package it.kekw.clowngg.match.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import it.kekw.clowngg.riot.IDdragon;
import it.kekw.clowngg.riot.RiotMgrInterface;
import it.kekw.clowngg.riot.dto.MatchDTO;
import it.kekw.clowngg.riot.dto.Participant;
import it.kekw.clowngg.riot.dto.RankInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;
import net.coobird.thumbnailator.Thumbnails;

public class ClownMatchMgrImpl implements ClownMatchMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClownMatchMgrImpl.class);

    private RiotMgrInterface riotManager;

    private IDdragon ddragonApi;

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
    public Integer getKdaByMatch(MatchDTO match, String puuid) {

        Participant participant = getParticipantByMatch(match, puuid);
        return participant.getChallenges().getKda();
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
            List<RankInfoJPA> ranks = rankRepository.findBySummInfoId(showCaseDetailJpa.getSummInfoId());
            // provisory
            RankInfoJPA highestRank = ClownMatchMgrUtility.getHighestRank(ranks);
            dtos.add(ClownMatchMgrUtility.generateShowCaseDetailDTO(showCaseDetailJpa, highestRank));
        }
        return dtos;
    }

    @Override
    public void insertShowCaseDetails() {

        ShowCaseDetailJPA showCaseJpa = new ShowCaseDetailJPA();
        RankInfoJPA rankJpa = rankRepository.getLowerWinRate();
        showCaseJpa.setStatName("Worst WinRate");
        showCaseJpa.setSummInfoId(rankJpa.getSummInfoId());
        showCaseJpa.setValue(rankJpa.getWinrate());
        showCaseJpa.setDescription("Lowest WinRate");

        showCaseDetailRepository.save(showCaseJpa);
        LOGGER.info("Persisted {}", showCaseJpa);

    }

    @Override
    public byte[] getProfileIconImage(String profileIconNumber) throws IOException {
        byte[] byteArray = ddragonApi.getProfileIcon(profileIconNumber);
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
    @Transactional
    public List<MatchDTO> getMatchesByPuuid(String puuid, String rankedType, Integer count) {

        List<String> matchesIds = new ArrayList<>();
        List<MatchDTO> matches = new ArrayList<>();
        try {
            matchesIds = riotManager.getMatchIdsByPuuid(puuid, rankedType, count);
            for (String matchId : matchesIds) {
                matches.add(riotManager.getMatchById(matchId));
            }

        } catch (Exception e) {
            LOGGER.error("ERROR: Error while performing getMatchesBySummInfoId", e);
            throw new RuntimeException();
        }
        return matches;
    }

    public Participant getParticipantByMatch(MatchDTO match, String puuid) {

        List<Participant> participants = match.getInfo().getParticipants();

        for (Participant participant : participants) {
            if (participant.getPuuid().equals(puuid))
                return participant;
        }
        return null;
    }

}
