package net.pacificsoft.microservices.loka.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "NetworkInformationMessage")
public class NetworkInformation extends Message {
    @Column
    private int sequenceNumber;

    @Column
    private String message;

    @OneToMany
    private List<SigfoxBaseStation> sigfoxBaseStationList;

    public NetworkInformation() {
    }

    public NetworkInformation(int sequenceNumber, String message, List<SigfoxBaseStation> sigfoxBaseStationList) {
        this.sequenceNumber = sequenceNumber;
        this.message = message;
        this.sigfoxBaseStationList = sigfoxBaseStationList;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SigfoxBaseStation> getSigfoxBaseStationList() {
        return sigfoxBaseStationList;
    }

    public void setSigfoxBaseStationList(List<SigfoxBaseStation> sigfoxBaseStationList) {
        this.sigfoxBaseStationList = sigfoxBaseStationList;
    }

}
