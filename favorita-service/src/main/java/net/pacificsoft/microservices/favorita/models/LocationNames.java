package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locationNames")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class LocationNames implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idName", nullable = false)
    private long idname;

    @Column(name = "name", nullable = false)
    private String name;

    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "predictionID")
    private Prediction prediction;
    */
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "locationNames")
    private Set<Prediction> prediction = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "locationNames")
    private Message  message;

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
    public long getIdname() {
        return idname;
    }

    /**
     * @param idname the idname to set
     */
    public void setIdname(long idname) {
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

    /**
     * @return the prediction
     */
    public Set<Prediction> getPrediction() {
        return prediction;
    }

    /**
     * @param prediction the prediction to set
     */
    public void setPrediction(Set<Prediction> prediction) {
        this.prediction = prediction;
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