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
@Table(name = "locationNames")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class LocationNames implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idName", nullable = false)
    private Double idname;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "predictionID")
    private Prediction prediction;
    

   /* @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "locationNames")
    private Message  message;*/
    

    public LocationNames(Double idname, String name) {
        this.idname = idname;
        this.name = name;
    }

    public LocationNames(long id, Double idname, String name) {
        this.id = id;
        this.idname = idname;
        this.name = name;
        //this.message = message;
    }
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("idname", this.idname);
        json.put("name", this.name);
        return json;
    }

    public LocationNames() {
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
     * @return the idname
     */
    public Double getIdname() {
        return idname;
    }

    /**
     * @param idname the idname to set
     */
    public void setIdname(Double idname) {
        this.idname = idname;
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

    /*
    public Message getMessage() {
        return message;
    }

  
    public void setMessage(Message message) {
        this.message = message;
    }*/

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

    
}