package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Message implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "message")
    private Set<Prediction> predictions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guessesID")
    private MessageGuess messageGuess;

    @OneToOne(cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           mappedBy = "message")
    private GoApiResponse goApiResponse;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locationNamesID")
    private LocationNames locationNames;

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
         * @return the predictions
         */
        public Set<Prediction> getPredictions() {
                return predictions;
        }

        /**
         * @param predictions the predictions to set
         */
        public void setPredictions(Set<Prediction> predictions) {
                this.predictions = predictions;
        }

        /**
         * @return the messageGuess
         */
        public MessageGuess getMessageGuess() {
                return messageGuess;
        }

        /**
         * @param messageGuess the messageGuess to set
         */
        public void setMessageGuess(MessageGuess messageGuess) {
                this.messageGuess = messageGuess;
        }

        /**
         * @return the goApiResponse
         */
        public GoApiResponse getGoApiResponse() {
                return goApiResponse;
        }

        /**
         * @param goApiResponse the goApiResponse to set
         */
        public void setGoApiResponse(GoApiResponse goApiResponse) {
                this.goApiResponse = goApiResponse;
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