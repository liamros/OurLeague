package it.kekw.clowngg.match.controller.dto;

public class ShowCaseDetailDTO {
    
    private String statName;
    private String summonerName;
    private Float value;
    private String description;

    private Integer profileIconNum;
    
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
    public Integer getProfileIconNum() {
        return profileIconNum;
    }
    public void setProfileIconNum(Integer profileIconNum) {
        this.profileIconNum = profileIconNum;
    }
    

}
