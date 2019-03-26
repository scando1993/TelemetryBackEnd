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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity
@Table(name = "messageGuess")
public class MessageGuess{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "probability", nullable = false)
    private Double probability;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "messageGuess")
    private Set<Message> messages = new HashSet<>();

    public MessageGuess(String location, Double probability) {
        this.location = location;
        this.probability = probability;
    }

    public MessageGuess() {
    }
    
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("location", this.location);
        json.put("probability", this.probability);
        return json;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

}