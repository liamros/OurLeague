package it.kekw.clowngg.match.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.kekw.clowngg.common.constants.ShowCaseType;
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
    public void updateShowCaseDetails() {

        List<ShowCaseDetailJPA> listShowCaseUpdated = new ArrayList<>();
        listShowCaseUpdated.addAll(Arrays.asList(generateLowerWinRate(), generateLowerKda()));

        showCaseDetailRepository.saveAll(listShowCaseUpdated);
        LOGGER.info("INFO: Persisted {}", listShowCaseUpdated);
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
    public List<MatchDTO> getMatchesByPuuid(String puuid, String queueType, Integer count) {

        List<String> matchesIds = new ArrayList<>();
        List<MatchDTO> matches = new ArrayList<>();
        try {
            matchesIds = riotManager.getMatchIdsByPuuid(puuid, queueType, count);
            for (String matchId : matchesIds) {
                matches.add(riotManager.getMatchById(matchId));
            }

        } catch (Exception e) {
            LOGGER.error("ERROR: Error while performing getMatchesBySummInfoId", e);
            throw new RuntimeException();
        }
        return matches;
    }

    private ShowCaseDetailJPA generateLowerWinRate() {

        ShowCaseDetailJPA showCaseJpa = new ShowCaseDetailJPA();
        RankInfoJPA rankJpa = rankRepository.getLowerWinRate();
        showCaseJpa.setStatName(ShowCaseType.WORST_WR.statName());
        showCaseJpa.setSummInfoId(rankJpa.getSummInfoId());
        showCaseJpa.setValue(rankJpa.getWinrate());
        showCaseJpa.setDescription("Lowest WinRate");
        return showCaseJpa;
    }

    private ShowCaseDetailJPA generateLowerKda() {

        ShowCaseDetailJPA showCaseJpa = new ShowCaseDetailJPA();
        Iterable<SummonerInfoJPA> summoners = summonerRepository.findAll();
        Participant participant = getParticipantWithLowerKda(summoners);

        showCaseJpa.setStatName(ShowCaseType.WORST_KDA.statName());
        for (SummonerInfoJPA summoner : summoners) {
            if (participant.getPuuid().equals(summoner.getPuuid()))
                showCaseJpa.setSummInfoId(summoner.getId());
        }
        showCaseJpa.setValue(Float.valueOf(String.valueOf(participant.getChallenges().getKda())));
        showCaseJpa.setDescription("Lowest KDA");
        return showCaseJpa;
    }

    private Participant getParticipantWithLowerKda(Iterable<SummonerInfoJPA> summoners) {

        List<MatchDTO> matches = new ArrayList<>();
        Double lowerKda = (double) showCaseDetailRepository.findByStatName(ShowCaseType.WORST_KDA.statName())
                .getValue();
        Participant participant = null;

        for (MatchDTO matchDTO : matches) {
            for (SummonerInfoJPA summoner : summoners) {
                if (lowerKda == null || ClownMatchMgrUtility.getParticipantByMatch(matchDTO, summoner.getPuuid())
                        .getChallenges().getKda() < lowerKda)
                    lowerKda = ClownMatchMgrUtility.getParticipantByMatch(matchDTO, summoner.getPuuid()).getChallenges()
                            .getKda();
                participant = ClownMatchMgrUtility.getParticipantByMatch(matchDTO, summoner.getPuuid());
            }
        }
        return participant;
    }

}
