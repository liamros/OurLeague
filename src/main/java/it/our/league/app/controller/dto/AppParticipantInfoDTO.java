package it.our.league.app.controller.dto;

public class AppParticipantInfoDTO {
    
    private Integer summInfoId;
    private String puuid;
    private String matchId;
    private Integer queueTypeId;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private String championName;
    private Boolean win;


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


}
