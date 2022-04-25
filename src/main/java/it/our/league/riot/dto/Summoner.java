package it.our.league.riot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Summoner {

    @JsonProperty("id")
    private String encryptedSummonerId;
    private String accountId;
    private String puuid;
    private String name;
    private Integer profileIconId;
    private Long revisionDate;
    private Integer summonerLevel;
    
    public String getEncryptedSummonerId() {
    return encryptedSummonerId;
    }
    
    public void setEncryptedSummonerId(String id) {
    this.encryptedSummonerId = id;
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
    
    public Long getRevisionDate() {
    return revisionDate;
    }
    
    public void setRevisionDate(Long revisionDate) {
    this.revisionDate = revisionDate;
    }
    
    public Integer getSummonerLevel() {
    return summonerLevel;
    }
    
    public void setSummonerLevel(Integer summonerLevel) {
    this.summonerLevel = summonerLevel;
    }
    
    }