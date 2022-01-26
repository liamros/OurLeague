package it.kekw.clowngg.match.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SUMMONER_INFO")
public class SummonerInfoJPA {
    
    @Id
    private Integer id;
    @Column(name = "GAME_NAME")
    private String gameName;
    @Column(name = "SUMMONER_LEVEL")
    private Integer summonerLevel;
    @Column(name = "SUMMONER_ICON_ID")
    private Integer summonerIconId;
    @OneToOne
    @JoinColumn(name = "ID")
    private AccountInfoJPA accountInfo;
    @OneToMany
    @JoinColumn(name = "ID")
    private RankInfoJPA rankInfo;

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public Integer getSummonerLevel() {
        return summonerLevel;
    }
    public void setSummonerLevel(Integer summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
    public Integer getSummonerIconId() {
        return summonerIconId;
    }
    public void setSummonerIconId(Integer summonerIconId) {
        this.summonerIconId = summonerIconId;
    }
    public AccountInfoJPA getAccountInfo() {
        return accountInfo;
    }
    public void setAccountInfo(AccountInfoJPA accountInfo) {
        this.accountInfo = accountInfo;
    }
    public RankInfoJPA getRankInfo() {
        return rankInfo;
    }
    public void setRankInfo(RankInfoJPA rankInfo) {
        this.rankInfo = rankInfo;
    }
    @Override
    public String toString() {
        return "SummonerInfoJPA [gameName=" + gameName + ", id=" + id + ", summonerIconId=" + summonerIconId
                + ", summonerLevel=" + summonerLevel + "]";
    }
    
}
