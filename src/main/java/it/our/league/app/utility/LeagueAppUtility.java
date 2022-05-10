package it.our.league.app.utility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.our.league.app.controller.dto.AppLineChartDTO;
import it.our.league.app.controller.dto.AppLineChartWrapperDTO;
import it.our.league.app.controller.dto.AppParticipantInfoDTO;
import it.our.league.app.controller.dto.AppRankInfoDTO;
import it.our.league.app.controller.dto.AppShowCaseRankingDTO;
import it.our.league.app.controller.dto.AppSummonerDTO;
import it.our.league.app.impl.persistence.entity.MatchInfoJPA;
import it.our.league.app.impl.persistence.entity.RankInfoJPA;
import it.our.league.app.impl.persistence.entity.RelSummonerMatchJPA;
import it.our.league.app.impl.persistence.entity.ShowCaseRankingJPA;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.common.constants.LeagueQueueType;
import it.our.league.common.constants.LeagueRoleType;
import it.our.league.common.constants.LineChartType;
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
            queueTypeId = LeagueQueueType.valueOf(dto.getQueueType()).id();
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

    public static AppShowCaseRankingDTO generateAppShowCaseDetailDTO(ShowCaseRankingJPA jpa, AppRankInfoDTO rank) {
        AppShowCaseRankingDTO dto = new AppShowCaseRankingDTO();
        dto.setStatName(jpa.getStatName());
        dto.setSummonerName(jpa.getSummoner().getGameName());
        dto.setValue(jpa.getValue());
        dto.setDescription(jpa.getDescription());
        dto.setPosition(jpa.getPosition());
        dto.setPrevPosition(jpa.getPrevPosition());
        dto.setProfileIconNum(jpa.getSummoner().getSummonerIconId());

        if (rank == null) {
            dto.setTier("Unranked");
            dto.setWins(0);
            dto.setLosses(0);
        } else {

            dto.setQueueType(LeagueQueueType.getById(rank.getQueueTypeId()).description());
            dto.setTier(RankedTierType.valueOf(rank.getTier()).description());
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

    public static AppParticipantInfoDTO generateAppParticipantInfoDto(RelSummonerMatchJPA rsm, MatchInfoJPA matchJpa, SummonerInfoJPA summonerJpa) {
        AppParticipantInfoDTO dto = new AppParticipantInfoDTO();

        dto.setSummInfoId(rsm.getSummInfoId());
        dto.setPuuid(summonerJpa.getPuuid());
        dto.setGameName(summonerJpa.getGameName());
        dto.setChampionName(rsm.getChampionName());
        dto.setQueueTypeId(matchJpa.getQueueTypeId());
        dto.setKills(rsm.getKills());
        dto.setDeaths(rsm.getDeaths());
        dto.setAssists(rsm.getAssists());
        dto.setMatchId(rsm.getMatchId());
        dto.setWin(rsm.getWin());
        dto.setCreationTime(matchJpa.getGameCreationTime());
        dto.setStartTime(matchJpa.getGameStartTime());
        dto.setEndTime(matchJpa.getGameEndTime());
        dto.setVisionScore(rsm.getVisionScore());
        dto.setRole(rsm.getRole());
        return dto;
    }
    public static Float getAverageKDA(List<AppParticipantInfoDTO> participantInfos) {

        Integer totalKills = 0;
        Integer totalDeaths = 0;
        Integer totalAssists = 0;
        for (AppParticipantInfoDTO participantInfo : participantInfos) {
            totalKills += participantInfo.getKills();
            totalDeaths += participantInfo.getDeaths();
            totalAssists += participantInfo.getAssists();
        }
        Float totalKDA = totalDeaths != 0 ? ((float) totalKills + (float) totalAssists)/(float) totalDeaths : 0;
        return totalKDA;
    }

    public static Float getAverageKDA(List<Match> matches, String puuid) {

        Integer totalKills = 0;
        Integer totalDeaths = 0;
        Integer totalAssists = 0;
        for (Match match : matches) {
            Participant partecipant = getParticipantByMatch(match, puuid);
            totalKills += partecipant.getKills();
            totalDeaths += partecipant.getDeaths();
            totalAssists += partecipant.getAssists();
        }
        Float totalKDA = totalDeaths != 0 ? ((float) totalKills + (float) totalAssists)/(float) totalDeaths : 0;
        return totalKDA;
    }

    public static void completeRelSummonerMatchJpa(RelSummonerMatchJPA rsm, Participant p) {
        String role = p.getIndividualPosition();
        if (role.equals(LeagueRoleType.ADC.getRiotNaming()))
            role = LeagueRoleType.ADC.getName();
        else if (role.equals(LeagueRoleType.MID.getRiotNaming()))
            role = LeagueRoleType.MID.getName();
        else if (role.equals(LeagueRoleType.SUPPORT.getRiotNaming()))
            role = LeagueRoleType.SUPPORT.getName();
        rsm.setChampionName(p.getChampionName());
        rsm.setRole(role);
        rsm.setKills(p.getKills());
        rsm.setDeaths(p.getDeaths());
        rsm.setAssists(p.getAssists());
        rsm.setVisionScore(p.getVisionScore());
        rsm.setWin(p.getWin());
    }

    public static Float calculateKDA(Integer kills, Integer deaths, Integer assists) {
        Float k = (float) kills;
        Float a = (float) assists;
        Float d = deaths == 0 ? (float) 0.5 : (float) deaths;
        return (k+a)/d;
    }

    public static Map<Integer, List<ShowCaseRankingJPA>> groupBySummId(List<ShowCaseRankingJPA> list) {
        Map<Integer, List<ShowCaseRankingJPA>> map = new HashMap<>();

        for (ShowCaseRankingJPA e : list) {
            List<ShowCaseRankingJPA> l = map.getOrDefault(e.getSummInfoId(), new ArrayList<>());
            l.add(e);
            map.putIfAbsent(e.getSummInfoId(), l);
        }
        return map;
    }

    public static AppLineChartWrapperDTO generateAppLineChartWrapperDTO(LineChartType type, List<AppLineChartDTO> charts, Integer minY, Integer maxY) {
        AppLineChartWrapperDTO out = new AppLineChartWrapperDTO();
        out.setName(type.getName());
        out.setxUnit(type.getX());
        out.setyUnit(type.getY());
        out.setFormat(type.getFormat());
        out.setCharts(charts);
        out.setMinY(minY);
        out.setMaxY(maxY);
        return out;
    }
}
