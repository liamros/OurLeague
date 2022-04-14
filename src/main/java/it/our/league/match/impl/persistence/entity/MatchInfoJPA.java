package it.our.league.match.impl.persistence.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MATCH_INFO")
public class MatchInfoJPA {


    
    @Id
    @Column(name = "MATCH_ID")
    private String matchId;
    @Column(name = "QUEUE_TYPE_ID")
    private Integer queueTypeId;
    @Column(name = "PATCH")
    private String patch;
    @Column(name = "GAME_CREATION_TIME")
    private Timestamp gameCreationTime;
    @Column(name = "GAME_START_TIME")
    private Timestamp gameStartTime;
    @Column(name = "GAME_END_TIME")
    private Timestamp gameEndTime;
    @OneToMany
    @JoinColumn(name="MATCH_ID", referencedColumnName = "MATCH_ID", insertable = false, updatable = false)
    private List<RelSummonerMatchJPA> relSummonerMatches;
    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
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
    public Timestamp getGameStartTime() {
        return gameStartTime;
    }
    public void setGameStartTime(Timestamp gameStartTime) {
        this.gameStartTime = gameStartTime;
    }
    public Timestamp getGameEndTime() {
        return gameEndTime;
    }
    public void setGameEndTime(Timestamp gameEndTime) {
        this.gameEndTime = gameEndTime;
    }
    public List<RelSummonerMatchJPA> getRelSummonerMatches() {
        return relSummonerMatches;
    }
    @Override
    public String toString() {
        return "MatchInfoJPA [gameCreationTime=" + gameCreationTime + ", gameEndTime=" + gameEndTime
                + ", gameStartTime=" + gameStartTime + ", matchId=" + matchId + ", patch=" + patch + ", queueTypeId="
                + queueTypeId + "]";
    }
}
