package it.our.league.app.impl.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RANK_INFO")
@IdClass(RankInfoJPA.PrimaryKeys.class)
public class RankInfoJPA {

    @SuppressWarnings("unused")
    public static class PrimaryKeys implements Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Integer summInfoId;
        private Integer queueTypeId;
        public PrimaryKeys() {
        }   
        public PrimaryKeys(Integer summInfoId, Integer queueTypeId){
            this.summInfoId = summInfoId;
            this.queueTypeId = queueTypeId;
        }   
    }

    @Id
    @Column(name = "SUMM_INFO_ID")
    private Integer summInfoId;
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
    @JoinColumn(name="SUMM_INFO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private SummonerInfoJPA summoner;

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

    public SummonerInfoJPA getSummoner() {
        return summoner;
    }

    public void setSummoner(SummonerInfoJPA summoner) {
        this.summoner = summoner;
    }

    @Override
    public String toString() {
        return "RankInfoJPA [division=" + division + ", losses=" + losses + ", lp=" + lp + ", queueTypeId="
                + queueTypeId + ", summInfoId=" + summInfoId + ", summoner=" + summoner + ", tier=" + tier + ", wins="
                + wins + "]";
    }

}
