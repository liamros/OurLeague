package it.kekw.clowngg.riot;

import java.util.List;

import it.kekw.clowngg.riot.dto.RankInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface RiotMgrInterface {

    public SummonerDTO getAccountInfoBySummonerName(String summonerName);

    public List<RankInfoDTO> getRankInfoByEncryptedSummonerId(String encryptedSummonerId);

}
