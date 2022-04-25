package it.our.league.app.utility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppShowCaseDetailDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.MatchInfoJPA;
import it.our.league.app.impl.persistence.entity.RankInfoJPA;
import it.our.league.app.impl.persistence.entity.RelSummonerMatchJPA;
import it.our.league.app.impl.persistence.entity.ShowCaseDetailJPA;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.common.constants.RankedQueueType;
import it.our.league.common.constants.RankedTierType;
import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.Participant;
import it.our.league.riot.dto.RankInfo;
import it.our.league.riot.dto.Summoner;

public final class LeagueAppUtility {
    private LeagueAppUtility() {
    }

    public static SummonerInfoJPA generateSummonerInfoJpa(Summoner dto) {
        return generateSummonerInfoJpa(dto, null);
    }

    public static SummonerInfoJPA generateSummonerInfoJpa(Summoner dto, Integer id) {
        SummonerInfoJPA jpa = new SummonerInfoJPA();
        jpa.setGameName(dto.getName());
        jpa.setSummonerIconId(dto.getProfileIconId());
        jpa.setSummonerLevel(dto.getSummonerLevel());
        jpa.setId(id);
        jpa.setEncryptedSummonerId(dto.getEncryptedSummonerId());
        jpa.setPuuid(dto.getPuuid());
        jpa.setAccountId(dto.getAccountId());
        return jpa;
    }

    public static Summoner generateSummoner(SummonerInfoJPA jpa) {
        Summoner dto = new Summoner();
        dto.setAppId(jpa.getId());
        dto.setEncryptedSummonerId(jpa.getEncryptedSummonerId());
        dto.setAccountId(jpa.getAccountId());
        dto.setName(jpa.getGameName());
        dto.setProfileIconId(jpa.getSummonerIconId());
        dto.setPuuid(jpa.getPuuid());
        dto.setSummonerLevel(jpa.getSummonerLevel());
        return dto;
    }

    public static AppSummonerDTO generateAppSummonerDto(SummonerInfoJPA jpa) {
        return generateAppSummonerDto(jpa, null);
    }

    public static AppSummonerDTO generateAppSummonerDto(SummonerInfoJPA jpa, List<RankInfoJPA> ranks) {
        AppSummonerDTO dto = new AppSummonerDTO();
        dto.setSummInfoId(jpa.getId());
        dto.setEncryptedSummonerId(jpa.getEncryptedSummonerId());
        dto.setAccountId(jpa.getAccountId());
        dto.setName(jpa.getGameName());
        dto.setProfileIconId(jpa.getSummonerIconId());
        dto.setPuuid(jpa.getPuuid());
        dto.setSummonerLevel(jpa.getSummonerLevel());
        if (ranks == null)
            return dto;
        for (RankInfoJPA rank : ranks)
            dto.addRank(generateAppRankInfoDto(rank));
        return dto;
    }

    public static RankInfoJPA generateRankInfoJpa(RankInfo dto, Integer summonerInfoId) {
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

    public static AppRankInfoDTO generateAppRankInfoDto(RankInfoJPA jpa) {
        AppRankInfoDTO dto = new AppRankInfoDTO();
        dto.setSummInfoId(jpa.getSummInfoId());
        dto.setQueueTypeId(jpa.getQueueTypeId());
        dto.setTier(jpa.getTier());
        dto.setDivision(jpa.getDivision());
        dto.setWins(jpa.getWins());
        dto.setLosses(jpa.getLosses());
        dto.setLp(jpa.getLp());
        return dto;
    }

    public static AppShowCaseDetailDTO generateAppShowCaseDetailDTO(ShowCaseDetailJPA jpa, AppRankInfoDTO rank) {
        AppShowCaseDetailDTO dto = new AppShowCaseDetailDTO();
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

    public static Participant getParticipantByMatch(Match match, String puuid) {

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

    public static AppRankInfoDTO getHighestRankFromDto(List<AppRankInfoDTO> list) {
        AppRankInfoDTO rank = null;
        for (AppRankInfoDTO rankInfo : list) {
            if (rank != null) {
                int nextTierOrdinal = RankedTierType.valueOf(rankInfo.getTier()).ordinal();
                int currTierOrdinal = RankedTierType.valueOf(rank.getTier()).ordinal();
                if (nextTierOrdinal > currTierOrdinal)
                    rank = rankInfo;
                else if (nextTierOrdinal == currTierOrdinal) {
                    int nextDivOrdinal = RankedTierType.valueOf(rankInfo.getDivision()).ordinal();
                    int curreDivOrdinal = RankedTierType.valueOf(rank.getDivision()).ordinal();
                    if (nextDivOrdinal > curreDivOrdinal
                            || nextDivOrdinal == curreDivOrdinal && rankInfo.getLp() > rank.getLp())
                        rank = rankInfo;
                }
            } else
                rank = rankInfo;
        }
        return rank;
    }

    public static MatchInfoJPA generateMatchInfoJpa(Match dto) {

        MatchInfoJPA jpa = new MatchInfoJPA();
        jpa.setMatchId(dto.getMetadata().getMatchId());
        jpa.setGameCreationTime(new Timestamp(dto.getInfo().getGameCreation()));
        jpa.setGameStartTime(new Timestamp(dto.getInfo().getGameStartTimestamp()));
        jpa.setGameEndTime(new Timestamp(dto.getInfo().getGameEndTimestamp()));
        String[] arr = dto.getInfo().getGameVersion().split("\\.");
        StringBuffer patch = new StringBuffer(arr[0]);
        patch.append(".").append(arr[1]);
        jpa.setPatch(patch.toString());
        jpa.setQueueTypeId(dto.getInfo().getQueueId());
        return jpa;
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
