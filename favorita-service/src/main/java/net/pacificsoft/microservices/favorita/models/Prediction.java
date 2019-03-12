package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "prediction")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Prediction implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "probabilitiesID")
    private Probabilities probabilities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "messageID")
    private Message message;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "locationNamesID")
    private LocationNames locationNames;
    /*
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "prediction")
    private Set<LocationNames> locationNames = new HashSet<>();
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
     * @return the probabilities
     */
    public Probabilities getProbabilities() {
        return probabilities;
    }

    /**
     * @param probabilities the probabilities to set
     */
    public void setProbabilities(Probabilities probabilities) {
        this.probabilities = probabilities;
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

    /**
     * @return the locationNames
     */
    public LocationNames getLocationNames() {
        return locationNames;
    }

    /**
     * @param locationNames the locationNames to set
     */
    public void setLocationNames(LocationNames locationNames) {
        this.locationNames = locationNames;
    }

    

    
}