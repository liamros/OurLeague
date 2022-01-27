package it.kekw.clowngg.match.impl;

import it.kekw.clowngg.common.constants.RankedQueueType;
import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA;
import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;
import it.kekw.clowngg.riot.dto.RankedInfoDTO;
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

    public static RankInfoJPA generateRankedInfoJpa(RankedInfoDTO dto, int summonerInfoId) throws IllegalArgumentException {
        Integer queueTypeId = RankedQueueType.valueOf(dto.getQueueType()).id();
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
}
