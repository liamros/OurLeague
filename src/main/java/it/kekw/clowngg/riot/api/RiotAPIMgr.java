package it.kekw.clowngg.riot.api;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import it.kekw.clowngg.riot.api.dto.SummonerDTO;

public class RiotAPIMgr {

    final static private ObjectMapper MAPPER = new ObjectMapper();

    private String apiToken;

    private String summonerUrl;

    private String authHeaderKey;

    


    // TODO : Firebase persistence + exception handling + make it pretty
    public SummonerDTO getSummonerInfoBySummonerName(String summonerName) throws ClientProtocolException, IOException, Exception {


        SummonerDTO dto = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String url = MessageFormat.format(summonerUrl, summonerName);
            HttpGet request = new HttpGet(url);
            request.addHeader(authHeaderKey, apiToken);

            CloseableHttpResponse response = httpClient.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    dto = MAPPER.readValue(convertHttpEntityToJson(entity), SummonerDTO.class);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return dto;
    }

    // TODO : create specific exception for missing response + think of a flexible way to generalize rest calls
    private String convertHttpEntityToJson(HttpEntity entity) throws Exception {
        if (entity != null) {

            InputStream contentStream = entity.getContent();

            if (contentStream != null) {
                return IOUtils.toString(contentStream, "UTF-8");
            } else {
                throw new Exception();
            }

        } else {
            throw new Exception();
        }
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void setSummonerUrl(String summonerUrl) {
        this.summonerUrl = summonerUrl;
    }

    public void setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
    }


}
