package it.kekw.clowngg.match.impl;

import java.util.List;

import it.kekw.clowngg.common.constants.RankedQueueType;
import it.kekw.clowngg.match.controller.dto.ShowCaseDetailDTO;
import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA;
import it.kekw.clowngg.match.impl.persistence.entity.ShowCaseDetailJPA;
import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;
import it.kekw.clowngg.riot.dto.MatchDTO;
import it.kekw.clowngg.riot.dto.Participant;
import it.kekw.clowngg.riot.dto.RankInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public final class ClownMatchMgrUtility {
    private ClownMatchMgrUtility() {
    }

    public static SummonerInfoJPA generateSummonerInfoJpa(SummonerDTO dto) {
        SummonerInfoJPA jpa = new SummonerInfoJPA();
        jpa.setGameName(dto.getName());
        jpa.setSummonerIconId(dto.getProfileIconId());
        jpa.setSummonerLevel(dto.getSummonerLevel());
        jpa.setId(jpa.getId());
        jpa.setEncryptedSummonerId(dto.getId());
        jpa.setPuuid(dto.getPuuid());
        jpa.setAccountId(dto.getAccountId());
        return jpa;
    }

    public static RankInfoJPA generateRankedInfoJpa(RankInfoDTO dto, Integer summonerInfoId) {
        Integer queueTypeId;
        try {
            queueTypeId = RankedQueueType.valueOf(dto.getQueueType()).id();
        } catch (IllegalArgumentException e) {
            return null;
        }
        RankInfoJPA jpa = new RankInfoJPA();
        jpa.setSummInfoId(summonerInfoId);
        jpa.setTier(dto.getTier());
        jpa.setDivision(dto.getRank());
        jpa.setLp(dto.getLeaguePoints());
        jpa.setWins(dto.getWins());
        jpa.setLosses(dto.getLosses());
        jpa.setQueueTypeId(queueTypeId);
        return jpa;
    }

    public static ShowCaseDetailDTO generateShowCaseDetailDTO(ShowCaseDetailJPA jpa) {
        ShowCaseDetailDTO dto = new ShowCaseDetailDTO();
        dto.setStatName(jpa.getStatName());
        dto.setSummonerName(jpa.getSummoner().getGameName());
        dto.setValue(jpa.getValue());
        dto.setDescription(jpa.getDescription());
        dto.setProfileIconNum(jpa.getSummoner().getSummonerIconId());
        return dto;
    }

    public static Participant getParticipantByMatch(MatchDTO match, String puuid) {

        List<Participant> participants = match.getInfo().getParticipants();

        for (Participant participant : participants) {
            if (participant.getPuuid().equals(puuid))
                return participant;
        }
        return null;
    }
}
