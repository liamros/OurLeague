package it.our.league.app.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHOWCASE_RANKING")
public class ShowCaseRankingJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "STAT_NAME")
    private String statName;
    @Column(name = "SUMM_INFO_ID")
    private Integer summInfoId;
    @Column(name = "POSITION")
    private Integer position;
    @Column(name = "PREV_POSITION")
    private Integer prevPosition;
    @Column(name = "VALUE")
    private Float value;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name="SUMM_INFO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private SummonerInfoJPA summoner;

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public Integer getSummInfoId() {
        return summInfoId;
    }

    public void setSummInfoId(Integer summInfoId) {
        this.summInfoId = summInfoId;
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

    public SummonerInfoJPA getSummoner() {
        return summoner;
    }


    @Override
    public String toString() {
        return "ShowCaseDetailJPA [description=" + description + ", statName=" + statName + ", summInfoId=" + summInfoId
                + ", summoner=" + summoner + ", value=" + value + "]";
    }

    
}
