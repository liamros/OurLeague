package it.kekw.clowngg.match.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class AccountJPA {

    @Id
    @Column(name = "PUUID")
    private String puuid;
    @Column(name = "GAME_NAME")
    private String gameName;


    public String getPuuid() {
        return puuid;
    }
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    @Override
    public String toString() {
        return "AccountJPA [gameName=" + gameName + ", puuid=" + puuid + "]";
    }
    
    
}
