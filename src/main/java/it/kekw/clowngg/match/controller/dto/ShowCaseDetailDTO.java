package it.kekw.clowngg.match.controller.dto;

public class ShowCaseDetailDTO {
    
    private String statName;
    private String summonerName;
    private Float value;
    private String description;
    
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

}
