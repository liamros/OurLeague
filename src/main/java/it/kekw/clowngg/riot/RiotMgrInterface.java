package it.kekw.clowngg.riot;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import it.kekw.clowngg.riot.dto.SummonerDTO;

public interface RiotMgrInterface {

    public SummonerDTO getSummonerInfoBySummonerName(String summonerName)
            throws ClientProtocolException, IOException, Exception;

}
