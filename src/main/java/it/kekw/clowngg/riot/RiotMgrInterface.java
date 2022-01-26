package it.kekw.clowngg.riot;

import java.util.List;

import it.kekw.clowngg.riot.dto.RankedInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface RiotMgrInterface {

    public SummonerDTO getAccountInfoBySummonerName(String summonerName);

    public List<RankedInfoDTO> getRankedInfoByEncryptedSummonerId(String encryptedSummonerId);

}
