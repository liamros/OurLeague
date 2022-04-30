package it.our.league.app.controller.dto;

import java.sql.Timestamp;

public class AppParticipantInfoDTO {
    
    private Integer summInfoId;
    private String puuid;
    private String matchId;
    private String role;
    private Integer queueTypeId;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer visionScore;
    private String championName;
    private Boolean win;
    private Timestamp creationTime;
    private Timestamp startTime;
    private Timestamp endTime;


    public Integer getSummInfoId() {
        return summInfoId;
    }
    public void setSummInfoId(Integer summInfoId) {
        this.summInfoId = summInfoId;
    }
    public String getPuuid() {
        return puuid;
    }
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
    public String getMatchId() {
        return matchId;
    }
    public Integer getQueueTypeId() {
        return queueTypeId;
    }
    public void setQueueTypeId(Integer queueTypeId) {
        this.queueTypeId = queueTypeId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    public Integer getKills() {
        return kills;
    }
    public void setKills(Integer kills) {
        this.kills = kills;
    }
    public Integer getDeaths() {
        return deaths;
    }
    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }
    public Integer getAssists() {
        return assists;
    }
    public void setAssists(Integer assists) {
        this.assists = assists;
    }
    public String getChampionName() {
        return championName;
    }
    public void setChampionName(String championName) {
        this.championName = championName;
    }
    public Boolean getWin() {
        return win;
    }
    public void setWin(Boolean win) {
        this.win = win;
    } 
    public Timestamp getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Integer getVisionScore() {
        return visionScore;
    }
    public void setVisionScore(Integer visionScore) {
        this.visionScore = visionScore;
    }


}
