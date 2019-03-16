package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table (name = "goApiResponse")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class GoApiResponse implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "sucess", nullable = false)
    private boolean sucess;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "messageID")
    private Message message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deviceID")
    private Device device;

    public GoApiResponse(boolean sucess) {
        this.sucess = sucess;
    }

    public GoApiResponse() {
    }
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("success", this.sucess);
        return json;
    }
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
    public boolean getSucess() {
        return sucess;
    }

    /**
     * @param sucess the sucess to set
     */
    public void setSucess(boolean sucess) {
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    
}
