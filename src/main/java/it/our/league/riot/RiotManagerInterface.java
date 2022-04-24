package it.our.league.riot;

import java.util.List;

import it.our.league.riot.dto.Match;
import it.our.league.riot.dto.RankInfo;
import it.our.league.riot.dto.Summoner;

public interface RiotManagerInterface {

    public Summoner getAccountInfoBySummonerName(String summonerName);

    public List<RankInfo> getRankInfoByEncryptedSummonerId(String encryptedSummonerId);

    public List<String> getMatchIdsByPuuid(String puuid, String queueType, Integer count, Long startTimeInSeconds, Integer startIndex);

    public Match getMatchById(String matchId);

}
