package net.pacificsoft.microservices.loka.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {
    private long date;
    private float accuracy;
    private float latitude;
    private float longitude;
    private float battery;
    private float temperature;
    private long to;

    public Position() {
    }

    public Position(long date, float accuracy, float latitude, float longitude) {
        this.date = date;
        this.accuracy = accuracy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position(long date, float accuracy, float latitude, float longitude, float battery, float temperature, long to) {
        this.date = date;
        this.accuracy = accuracy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery = battery;
        this.temperature = temperature;
        this.to = to;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
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

    public float getBattery() {
        return battery;
    }

    public void setBattery(float battery) {
        this.battery = battery;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Position{" +
                "date=" + date +
                ", accuracy=" + accuracy +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", battery=" + battery +
                ", temperature=" + temperature +
                ", to=" + to +
                '}';
    }
}
