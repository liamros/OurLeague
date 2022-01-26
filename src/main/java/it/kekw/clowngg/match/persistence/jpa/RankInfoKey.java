package it.kekw.clowngg.match.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RankInfoKey implements Serializable {
    
    @Column(name = "ID")
    private Integer id;
    @Column(name = "QUEUE_TYPE_ID")
    private Integer queueTypeId;

    
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
    @Override
    public String toString() {
        return "RankInfoKey [id=" + id + ", queueTypeId=" + queueTypeId + "]";
    }

    
}
