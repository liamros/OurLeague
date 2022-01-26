package it.kekw.clowngg.match.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_INFO")
public class AccountInfoJPA {

    @Id
    private Integer id;
    @Column(name = "PUUID")
    private String puuid;
    @Column(name = "ENCRYPTED_SUMMONER_ID")
    private String encryptedSummonerId;
    @Column(name = "ACCOUNT_ID")
    private String accountId;

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    @Override
    public String toString() {
        return "AccountInfoJPA [accountId=" + accountId + ", encryptedSummonerId=" + encryptedSummonerId + ", id=" + id
                + ", puuid=" + puuid + "]";
    }
}
