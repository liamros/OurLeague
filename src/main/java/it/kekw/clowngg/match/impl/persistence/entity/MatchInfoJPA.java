package it.kekw.clowngg.match.impl.persistence.entity;

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
@Table(name = "MATCH_INFO")
@IdClass(MatchInfoJPA.PrimaryKeys.class)
public class MatchInfoJPA {

    @Data
    @SuppressWarnings("unused")
    public static class PrimaryKeys implements Serializable {
        private String matchId;
        private Integer summInfoId;
        public PrimaryKeys() {
        }   
        public PrimaryKeys(String matchId, Integer summInfoId){
            this.matchId = matchId;
            this.summInfoId = summInfoId;
        }   
    }

    
    @Id
    @Column(name = "MATCH_ID")
    private String matchId;
    @Id
    @Column(name = "SUMM_INFO_ID")
    private Integer summInfoId;
    @Column(name = "QUEUE_TYPE_ID")
    private Integer queueTypeId;
    @Column(name = "PATCH")
    private String patch;
    @Column(name = "GAME_CREATION_TIME")
    private Timestamp gameCreationTime;
    @ManyToOne
    @JoinColumn(name="SUMM_INFO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private SummonerInfoJPA summoner;
    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
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
    public String getPatch() {
        return patch;
    }
    public void setPatch(String patch) {
        this.patch = patch;
    }
    public Timestamp getGameCreationTime() {
        return gameCreationTime;
    }
    public void setGameCreationTime(Timestamp gameCreationTime) {
        this.gameCreationTime = gameCreationTime;
    }
    public SummonerInfoJPA getSummoner() {
        return summoner;
    }
    public void setSummoner(SummonerInfoJPA summoner) {
        this.summoner = summoner;
    }
    @Override
    public String toString() {
        return "MatchInfoJPA [gameCreationTime=" + gameCreationTime + ", matchId=" + matchId + ", patch=" + patch
                + ", queueTypeId=" + queueTypeId + ", summInfoId=" + summInfoId + "]";
    }

}
