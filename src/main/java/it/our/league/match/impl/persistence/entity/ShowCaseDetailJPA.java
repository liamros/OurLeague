package it.our.league.match.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHOWCASE_DETAIL")
public class ShowCaseDetailJPA {

    @Id
    @Column(name = "STAT_NAME")
    private String statName;
    @Column(name = "SUMM_INFO_ID")
    private Integer summInfoId;
    @Column(name = "VALUE")
    private Float value;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name="SUMM_INFO_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private SummonerInfoJPA summoner;


    
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

    public void setSummoner(SummonerInfoJPA summoner) {
        this.summoner = summoner;
    }

    @Override
    public String toString() {
        return "ShowCaseDetailJPA [description=" + description + ", statName=" + statName + ", summInfoId=" + summInfoId
                + ", summoner=" + summoner + ", value=" + value + "]";
    }

    
}
