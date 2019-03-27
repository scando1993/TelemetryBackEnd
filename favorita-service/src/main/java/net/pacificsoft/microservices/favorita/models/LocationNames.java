package net.pacificsoft.microservices.favorita.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity
@Table(name = "locationNames")
public class LocationNames{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idName", nullable = false)
    private Double idname;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predictionID")
    private Prediction prediction;
    

   /* @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "locationNames")
    @JsonIdentityReference(alwaysAsId = true)
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
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public Double getIdname() {
        return idname;
    }
    
    public void setIdname(Double idname) {
        this.idname = idname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }    
}