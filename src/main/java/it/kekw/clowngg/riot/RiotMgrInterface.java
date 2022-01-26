package it.kekw.clowngg.riot;

import it.kekw.clowngg.riot.dto.RankedInfoDTO;
import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface RiotMgrInterface {

    public SummonerDTO getAccountInfoBySummonerName(String summonerName);

    public RankedInfoDTO getRankedInfoByEncryptedSummonerId(String encryptedSummonerId);

}
