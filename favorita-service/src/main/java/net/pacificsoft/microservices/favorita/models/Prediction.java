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
import javax.persistence.Table;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "prediction")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class Prediction implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "messageID")
    private Message message;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "prediction")
    private Set<LocationNames> locationNames = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "prediction")
    private Set<Probabilities> probabilitieses = new HashSet<>();
    
    /*
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "prediction")
    private Set<LocationNames> locationNames = new HashSet<>();
    */

    public Prediction(String name) {
        this.name = name;
    }

    public Prediction() {
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("name", this.name);
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    public Set<LocationNames> getLocationNames() {
        return locationNames;
    }

    public void setLocationNames(Set<LocationNames> locationNames) {
        this.locationNames = locationNames;
    }

    public Set<Probabilities> getProbabilitieses() {
        return probabilitieses;
    }

    public void setProbabilitieses(Set<Probabilities> probabilitieses) {
        this.probabilitieses = probabilitieses;
    }


    

    
}