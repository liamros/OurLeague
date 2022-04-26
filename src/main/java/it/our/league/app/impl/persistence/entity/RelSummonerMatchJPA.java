package it.our.league.app.impl.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "REL_SUMMONER_MATCH")
@IdClass(RelSummonerMatchJPA.PrimaryKeys.class)
public class RelSummonerMatchJPA {
    
    @Data
    @SuppressWarnings("unused")
    public static class PrimaryKeys implements Serializable {
        
        private Integer summInfoId;
        private String matchId;
        public PrimaryKeys() {
        }   
        public PrimaryKeys(Integer summInfoId, String matchId){
            this.matchId = matchId;
            this.summInfoId = summInfoId;
        }   
    }

    @Id
    @Column(name = "SUMM_INFO_ID")
    private Integer summInfoId;
    @Id
    @Column(name = "MATCH_ID")
    private String matchId;
    @Column(name = "KILLS")
    private Integer kills;
    @Column(name = "DEATHS")
    private Integer deaths;
    @Column(name = "ASSISTS")
    private Integer assists;
    @Column(name = "CHAMPION_NAME")
    private String championName;
    @Column(name = "WIN")
    private Boolean win;
    @Column(name = "UPDATE_TIME")
    private Timestamp updateTime;
    @ManyToOne
    @JoinColumn(name="SUMM_INFO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private SummonerInfoJPA summoner;
    @ManyToOne
    @JoinColumn(name="MATCH_ID", referencedColumnName = "MATCH_ID", insertable = false, updatable = false)
    private MatchInfoJPA match;
    public Integer getSummInfoId() {
        return summInfoId;
    }
    public void setSummInfoId(Integer summInfoId) {
        this.summInfoId = summInfoId;
    }
    public String getMatchId() {
        return matchId;
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
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    public SummonerInfoJPA getSummoner() {
        return summoner;
    }
    public MatchInfoJPA getMatch() {
        return match;
    }
    @Override
    public String toString() {
        return "RelSummonerMatchJPA [matchId=" + matchId + ", summInfoId=" + summInfoId + ", updateTime=" + updateTime
                + ", win=" + win + "]";
    }

}
