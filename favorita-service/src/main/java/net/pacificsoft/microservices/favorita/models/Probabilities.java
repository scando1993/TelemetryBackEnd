package net.pacificsoft.springbootcrudrest.model;

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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "probabilities")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Probabilities implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "nameID", nullable = false)
    private String name;

    @Column(name = "probability", nullable = false)
    private Double probability;

    @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "probabilities")
    private Set<Prediction> prediction = new HashSet<>();

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

    
    
}