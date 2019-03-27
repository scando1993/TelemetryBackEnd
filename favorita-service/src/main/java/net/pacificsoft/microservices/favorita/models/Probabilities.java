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
@Table(name = "probabilities")
public class Probabilities{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "nameID", nullable = false)
        private Double nameID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "probilityID")
    private Prediction prediction;

        public Probabilities(Double nameID, Double probability) {
            this.nameID = nameID;
            this.probability = probability;
        }

    public Probabilities() {
    }
    
    public JSONObject toJson(){
            JSONObject json = new JSONObject();
            json.put("idname", this.nameID);
            json.put("probability", this.probability);
            return json;
    }

    public long getId() {
            return id;
    }

    public void setId(long id) {
            this.id = id;
    }

    public Double getNameID() {
            return nameID;
    }

    public void setNameID(Double nameID) {
            this.nameID = nameID;
    }

    public Double getProbability() {
            return probability;
    }

    public void setProbability(Double probability) {
            this.probability = probability;
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }   
}