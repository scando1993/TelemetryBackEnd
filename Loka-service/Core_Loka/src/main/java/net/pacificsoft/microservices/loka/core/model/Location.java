package net.pacificsoft.microservices.loka.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "LocationMessage")
public class Location extends Message {
    @Column
    private float latitude;
    @Column
    private float longitude;
    @Column
    private float accuracy;

    public Location() {
    }

    public Location(float latitude, float longitude, float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", accuracy=" + accuracy +
                '}';
    }
}
