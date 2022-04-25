package it.our.league.app.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class AppSummonerDTO {
    

    private Integer summInfoId;
    private String encryptedSummonerId;
    private String accountId;
    private String puuid;
    private String name;
    private Integer profileIconId;
    private Integer summonerLevel;
    private List<AppRankInfoDTO> ranks;

    public AppSummonerDTO() {
        this.ranks = new ArrayList<>();
    } 

    public Integer getSummInfoId() {
        return summInfoId;
    }
    public void setSummInfoId(Integer summInfoId) {
        this.summInfoId = summInfoId;
    }
    public String getEncryptedSummonerId() {
        return encryptedSummonerId;
    }
    public void setEncryptedSummonerId(String encryptedSummonerId) {
        this.encryptedSummonerId = encryptedSummonerId;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getPuuid() {
        return puuid;
    }
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getProfileIconId() {
        return profileIconId;
    }
    public void setProfileIconId(Integer profileIconId) {
        this.profileIconId = profileIconId;
    }
    public Integer getSummonerLevel() {
        return summonerLevel;
    }
    public void setSummonerLevel(Integer summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
    public List<AppRankInfoDTO> getRanks() {
        return ranks;
    }
    public void setRanks(List<AppRankInfoDTO> ranks) {
        this.ranks = ranks;
    }
    public void addRank(AppRankInfoDTO rank) {
        this.ranks.add(rank);
    }

    

}
