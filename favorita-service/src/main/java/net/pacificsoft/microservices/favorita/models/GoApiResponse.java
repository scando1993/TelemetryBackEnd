package net.pacificsoft.microservices.favorita.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity
@Table (name = "goApiResponse")
public class GoApiResponse{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "sucess", nullable = false)
    private boolean sucess;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageID")
    private Message message;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviceID")
    private Device device;

    public GoApiResponse(boolean sucess) {
        this.sucess = sucess;
    }

    public GoApiResponse() {
    }
    
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("success", this.sucess);
        return json;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public boolean getSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }    
}
