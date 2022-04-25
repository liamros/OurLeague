package it.our.league.app.controller.dto;

public class AppRankInfoDTO {

    private Integer summInfoId;
    private Integer queueTypeId;
    private String tier;
    private String division;
    private Integer lp;
    private Integer wins;
    private Integer losses;
    
    public Integer getSummInfoId() {
        return summInfoId;
    }
    public void setSummInfoId(Integer summInfoId) {
        this.summInfoId = summInfoId;
    }
    public Integer getQueueTypeId() {
        return queueTypeId;
    }
    public void setQueueTypeId(Integer queueTypeId) {
        this.queueTypeId = queueTypeId;
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
    public Float getWinrate() {
        return (((float)this.wins/((float)(this.wins+this.losses)))*100);
    }

    
}
