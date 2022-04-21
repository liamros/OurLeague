package it.our.league.app.impl;

import java.util.ArrayList;
import java.util.List;

import it.our.league.app.controller.dto.ShowCaseDetailDTO;
import it.our.league.app.impl.persistence.entity.MatchInfoJPA;
import it.our.league.app.impl.persistence.entity.RankInfoJPA;
import it.our.league.app.impl.persistence.entity.RelSummonerMatchJPA;
import it.our.league.app.impl.persistence.entity.ShowCaseDetailJPA;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.common.constants.RankedQueueType;
import it.our.league.common.constants.RankedTierType;
import it.our.league.riot.dto.MatchDTO;
import it.our.league.riot.dto.Participant;
import it.our.league.riot.dto.RankInfoDTO;
import it.our.league.riot.dto.SummonerDTO;

public final class LeagueAppUtility {
    private LeagueAppUtility() {
    }

    public static SummonerInfoJPA generateSummonerInfoJpa(SummonerDTO dto) {
        return generateSummonerInfoJpa(dto, null);
    }

    public static SummonerInfoJPA generateSummonerInfoJpa(SummonerDTO dto, Integer id) {
        SummonerInfoJPA jpa = new SummonerInfoJPA();
        jpa.setGameName(dto.getName());
        jpa.setSummonerIconId(dto.getProfileIconId());
        jpa.setSummonerLevel(dto.getSummonerLevel());
        jpa.setId(id);
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

    public static ShowCaseDetailDTO generateShowCaseDetailDTO(ShowCaseDetailJPA jpa, RankInfoJPA rank) {
        ShowCaseDetailDTO dto = new ShowCaseDetailDTO();
        dto.setStatName(jpa.getStatName());
        dto.setSummonerName(jpa.getSummoner().getGameName());
        dto.setValue(jpa.getValue());
        dto.setDescription(jpa.getDescription());
        dto.setProfileIconNum(jpa.getSummoner().getSummonerIconId());

        if (rank == null) {
            dto.setTier("UNRANKED");
            dto.setWins(0);
            dto.setLosses(0);
        } else {

            dto.setQueueType(RankedQueueType.getById(rank.getQueueTypeId()).name());
            dto.setTier(rank.getTier());
            dto.setDivision(rank.getDivision());
            dto.setLp(rank.getLp());
            dto.setWins(rank.getWins());
            dto.setLosses(rank.getLosses());
        }
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
    
    public static RankInfoJPA getHighestRank(List<RankInfoJPA> list) {
        RankInfoJPA rank = null;
        for (RankInfoJPA rankInfoJPA : list) {
            if (rank != null) {
                int nextTierOrdinal = RankedTierType.valueOf(rankInfoJPA.getTier()).ordinal();
                int currTierOrdinal = RankedTierType.valueOf(rank.getTier()).ordinal();
                if (nextTierOrdinal > currTierOrdinal)
                    rank = rankInfoJPA;
                else if (nextTierOrdinal == currTierOrdinal) {
                    int nextDivOrdinal = RankedTierType.valueOf(rankInfoJPA.getDivision()).ordinal();
                    int curreDivOrdinal = RankedTierType.valueOf(rank.getDivision()).ordinal();
                    if (nextDivOrdinal > curreDivOrdinal
                            || nextDivOrdinal == curreDivOrdinal && rankInfoJPA.getLp() > rank.getLp())
                        rank = rankInfoJPA;
                }
            } else
                rank = rankInfoJPA;
        }
        return rank;
    }

    public static List<MatchInfoJPA> generateMatchInfoJpas(List<String> matchIds) {

        List<MatchInfoJPA> list = new ArrayList<>();
        for(String matchId: matchIds) {
            MatchInfoJPA jpa = new MatchInfoJPA();
            jpa.setMatchId(matchId);
            list.add(jpa);
        }
        return list;
    }

    public static RelSummonerMatchJPA generateRelSummonerMatchJpa(int summInfoId, String matchId) {

        RelSummonerMatchJPA jpa = new RelSummonerMatchJPA();
        jpa.setSummInfoId(summInfoId);
        jpa.setMatchId(matchId);
        return jpa;
    }

    public static List<RelSummonerMatchJPA> generateRelSummonerMatchJpas(int summInfoId, List<String> matchIds) {

        List<RelSummonerMatchJPA> list = new ArrayList<>();
        for(String matchId: matchIds)
            list.add(generateRelSummonerMatchJpa(summInfoId, matchId));
        return list;
    }
}
