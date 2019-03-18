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
@Table(name = "probabilities")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class Probabilities implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "nameID", nullable = false)
    private Double nameID;

    @Column(name = "probability", nullable = false)
    private Double probability;

	@JsonIgnore
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
	public Double getNameID() {
		return nameID;
	}

	/**
	 * @param name the name to set
	 */
	public void setNameID(Double nameID) {
		this.nameID = nameID;
	}

	/**
	 * @return the probability
	 */
	public Double getProbability() {
		return probability;
	}

	/**
	 * @param probability the probability to set
	 */
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