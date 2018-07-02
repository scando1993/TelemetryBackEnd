package net.pacificsoft.microservices.loka.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "GpsMessage")
public class Gps extends Message {
    @Column
    private float latitude;

    @Column
    private float longitude;

    @Column
    private float speed;

    public Gps() {
    }

    public Gps(float latitude, float longitude, float speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Gps{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                '}';
    }
}
