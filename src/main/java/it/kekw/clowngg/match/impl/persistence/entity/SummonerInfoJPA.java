package it.kekw.clowngg.match.impl.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SUMMONER_INFO")
public class SummonerInfoJPA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "GAME_NAME")
    private String gameName;
    @Column(name = "SUMMONER_LEVEL")
    private Integer summonerLevel;
    @Column(name = "SUMMONER_ICON_ID")
    private Integer summonerIconId;
    @Column(name = "PUUID")
    private String puuid;
    @Column(name = "ENCRYPTED_SUMMONER_ID")
    private String encryptedSummonerId;
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SUMM_INFO_ID", referencedColumnName = "ID")
    private List<RankInfoJPA> rankInfo;

    
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
    public String getPuuid() {
        return puuid;
    }
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
    public String getEncryptedSummonerId() {
        return encryptedSummonerId;
    }
    public void setEncryptedSummonerId(String encryptedSummonerId) {
        this.encryptedSummonerId = encryptedSummonerId;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public List<RankInfoJPA> getRankInfo() {
        return rankInfo;
    }
    public void setRankInfo(List<RankInfoJPA> rankInfo) {
        this.rankInfo = rankInfo;
    }
    @Override
    public String toString() {
        return "SummonerInfoJPA [accountId=" + accountId + ", encryptedSummonerId=" + encryptedSummonerId
                + ", gameName=" + gameName + ", id=" + id + ", puuid=" + puuid + ", rankInfo=" + rankInfo
                + ", summonerIconId=" + summonerIconId + ", summonerLevel=" + summonerLevel + "]";
    }    
}
