package it.our.league.riot;

import java.util.List;

import it.our.league.riot.dto.MatchDTO;
import it.our.league.riot.dto.RankInfoDTO;
import it.our.league.riot.dto.SummonerDTO;

public interface RiotManagerInterface {

    public SummonerDTO getAccountInfoBySummonerName(String summonerName);

    public List<RankInfoDTO> getRankInfoByEncryptedSummonerId(String encryptedSummonerId);

    public List<String> getMatchIdsByPuuid(String puuid, String queueType, Integer count, Long startTime);

    public MatchDTO getMatchById(String matchId);

}
