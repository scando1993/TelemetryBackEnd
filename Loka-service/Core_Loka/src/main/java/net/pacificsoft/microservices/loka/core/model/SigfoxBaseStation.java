package net.pacificsoft.microservices.loka.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "SigfoxBaseStation")
public class SigfoxBaseStation {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column
    private int signalStrenght;

    @Column
    private int signalToNoiseRatio;

    @Column
    private float latitude;

    @Column
    private float longitude;

    public SigfoxBaseStation() {
    }

    public SigfoxBaseStation(String id, int signalStrenght, int signalToNoiseRatio, float latitude, float longitude) {
        this.id = id;
        this.signalStrenght = signalStrenght;
        this.signalToNoiseRatio = signalToNoiseRatio;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSignalStrenght() {
        return signalStrenght;
    }

    public void setSignalStrenght(int signalStrenght) {
        this.signalStrenght = signalStrenght;
    }

    public int getSignalToNoiseRatio() {
        return signalToNoiseRatio;
    }

    public void setSignalToNoiseRatio(int signalToNoiseRatio) {
        this.signalToNoiseRatio = signalToNoiseRatio;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "SigfoxBaseStation{" +
                "id='" + id + '\'' +
                ", signalStrenght=" + signalStrenght +
                ", signalToNoiseRatio=" + signalToNoiseRatio +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
