package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "goApiResponse")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class GoApiResponse implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "sucess", nullable = false)
    private int sucess;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "messageID")
    private Message message;

    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deviceID")
    private Device device;
    */
    

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the sucess
     */
    public int getSucess() {
        return sucess;
    }

    /**
     * @param sucess the sucess to set
     */
    public void setSucess(int sucess) {
        this.sucess = sucess;
    }

    /**
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    
}
