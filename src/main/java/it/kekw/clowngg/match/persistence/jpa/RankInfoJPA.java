package it.kekw.clowngg.match.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "RANK_INFO")
@IdClass(RankInfoJPA.PrimaryKeys.class)
public class RankInfoJPA {

    @Data
    public static class PrimaryKeys implements Serializable {
        private Integer id;
        private Integer queueTypeId;
    }

    // @EmbeddedId
    // private RankInfoJPA key;
    @Id
    @Column(name = "ID")
    private Integer id;
    @Id
    @Column(name = "QUEUE_TYPE_ID", insertable = false, updatable = false)
    private Integer queueTypeId;
    @Column(name = "TIER")
    private String tier;
    @Column(name = "DIVISION")
    private String division;
    @Column(name = "LP")
    private Integer lp;
    @Column(name = "WINS")
    private Integer wins;
    @Column(name = "LOSSES")
    private Integer losses;
    @ManyToOne
    @JoinColumn(name="ID", insertable = false, updatable = false)
    private SummonerInfoJPA summoner;
    // @ManyToOne
    // @JoinColumn(name = "QUEUE_TYPE_ID")
    // private QueueJPA queue;

    // public RankInfoJPA getKey() {
    // return key;
    // }

    // public void setKey(RankInfoJPA key) {
    // this.key = key;
    // }

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
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(Integer queueTypeId) {
        this.queueTypeId = queueTypeId;
    }

    public SummonerInfoJPA getSummoner() {
        return summoner;
    }

    public void setSummoner(SummonerInfoJPA summoner) {
        this.summoner = summoner;
    }

    // public QueueJPA getQueue() {
    // return queue;
    // }
    // public void setQueue(QueueJPA queue) {
    // this.queue = queue;
    // }
    // @Override
    // public String toString() {
    //     return "RankInfoJPA [division=" + division + ", key=" + key + ", losses=" + losses + ", lp=" + lp + ", tier="
    //             + tier + ", wins=" + wins + "]";
    // }
}
