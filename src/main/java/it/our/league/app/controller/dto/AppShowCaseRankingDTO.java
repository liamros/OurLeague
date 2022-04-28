package it.our.league.app.controller.dto;

public class AppShowCaseRankingDTO {
    
    private String statName;
    private String summonerName;
    private Float value;
    private String description;
    private Integer position;
    private Integer prevPosition;
    private Integer profileIconNum;
    
    // provisory
    private String queueType;
    private String tier;
    private String division;
    private Integer lp;
    private Integer wins;
    private Integer losses;
    
    public String getStatName() {
        return statName;
    }
    public void setStatName(String statName) {
        this.statName = statName;
    }
    public String getSummonerName() {
        return summonerName;
    }
    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }
    public Float getValue() {
        return value;
    }
    public void setValue(Float value) {
        this.value = value;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public Integer getPrevPosition() {
        return prevPosition;
    }
    public void setPrevPosition(Integer prevPosition) {
        this.prevPosition = prevPosition;
    }
    public Integer getProfileIconNum() {
        return profileIconNum;
    }
    public void setProfileIconNum(Integer profileIconNum) {
        this.profileIconNum = profileIconNum;
    }
    public String getQueueType() {
        return queueType;
    }
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }
    public String getTier() {
        return tier;
    }
    public void setTier(String tier) {
        this.tier = tier;
    }
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public Integer getLp() {
        return lp;
    }
    public void setLp(Integer lp) {
        this.lp = lp;
    }
    public Integer getWins() {
        return wins;
    }
    public void setWins(Integer wins) {
        this.wins = wins;
    }
    public Integer getLosses() {
        return losses;
    }
    public void setLosses(Integer losses) {
        this.losses = losses;
    }
    

}
