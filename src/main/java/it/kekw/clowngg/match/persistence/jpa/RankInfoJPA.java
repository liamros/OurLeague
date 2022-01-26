package it.kekw.clowngg.match.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RANK_INFO")
public class RankInfoJPA {

    @EmbeddedId
    private RankInfoJPA key;
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
    @JoinColumn(name = "QUEUE_TYPE_ID")
    private QueueJPA queue;


    public RankInfoJPA getKey() {
        return key;
    }
    public void setKey(RankInfoJPA key) {
        this.key = key;
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
    public QueueJPA getQueue() {
        return queue;
    }
    public void setQueue(QueueJPA queue) {
        this.queue = queue;
    }
    @Override
    public String toString() {
        return "RankInfoJPA [division=" + division + ", key=" + key + ", losses=" + losses + ", lp=" + lp + ", tier="
                + tier + ", wins=" + wins + "]";
    } 
}
