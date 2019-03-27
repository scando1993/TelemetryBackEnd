package net.pacificsoft.microservices.favorita.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity
@Table(name = "prediction")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "messageID")
    private Message message;
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "prediction")
    private Set<LocationNames> locationNames = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "prediction")
    private Set<Probabilities> probabilitieses = new HashSet<>();
    
    /*
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "prediction")
    @JsonIdentityReference(alwaysAsId = true)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMessage() {
        return message;
    }

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