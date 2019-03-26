package net.pacificsoft.microservices.favorita.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @OneToMany(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                mappedBy = "message")
        private Set<Prediction> predictions = new HashSet<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "guessesID")
        private MessageGuess messageGuess;

        @OneToOne(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "message")
        private GoApiResponse goApiResponse;

        /*@OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "locationNamesID")
        private LocationNames locationNames;*/

        public Message() {
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public Set<Prediction> getPredictions() {
                return predictions;
        }

        public void setPredictions(Set<Prediction> predictions) {
                this.predictions = predictions;
        }

        public MessageGuess getMessageGuess() {
                return messageGuess;
        }

        public void setMessageGuess(MessageGuess messageGuess) {
                this.messageGuess = messageGuess;
        }

        public GoApiResponse getGoApiResponse() {
                return goApiResponse;
        }

        public void setGoApiResponse(GoApiResponse goApiResponse) {
                this.goApiResponse = goApiResponse;
        }
}