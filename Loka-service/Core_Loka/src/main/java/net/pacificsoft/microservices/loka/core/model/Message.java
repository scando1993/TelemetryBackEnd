package net.pacificsoft.microservices.loka.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Message implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id_;

    @Column(nullable = false)
    protected String src;

    @Column(nullable = false)
    protected String dst;

    @Column(nullable = false)
    protected long timestamp;

    @Column
    protected boolean ack;

    @Column(name = "id_core")
    protected String id;

    @Column
    protected boolean unique;

    public Message() {
    }

    public Message(String src, String dst, long timestamp) {
        this.src = src;
        this.dst = dst;
        this.timestamp = timestamp;
    }

    public Message(String src, String dst, long timestamp, boolean ack, String id, boolean unique) {
        this.src = src;
        this.dst = dst;
        this.timestamp = timestamp;
        this.ack = ack;
        this.id = id;
        this.unique = unique;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAck() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return "Message{" +
                "src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
