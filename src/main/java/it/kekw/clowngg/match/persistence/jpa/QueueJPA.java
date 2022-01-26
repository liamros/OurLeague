package it.kekw.clowngg.match.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QUEUE")
public class QueueJPA {

    @Id
    @Column(name = "QUEUE_TYPE_ID")
    private Integer queueTypeId;
    @Column(name = "QUEUE_TYPE")
    private String queueType;


    public Integer getQueueTypeId() {
        return queueTypeId;
    }
    public void setQueueTypeId(Integer queueTypeId) {
        this.queueTypeId = queueTypeId;
    }
    public String getQueueType() {
        return queueType;
    }
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }
    @Override
    public String toString() {
        return "QueueJPA [queueType=" + queueType + ", queueTypeId=" + queueTypeId + "]";
    }   
}
