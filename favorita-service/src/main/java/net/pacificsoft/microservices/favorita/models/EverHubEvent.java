package net.pacificsoft.microservices.favorita.models;

import java.util.Date;

public class EverHubEvent {
    private String rawData;
    private String epochDateTime;
    private Date epoch;
    private String family;
    private String device;
    private Object wifi;
    private double temperature;
    private int battery;

    public EverHubEvent(String rawData, String epochDateTime, Date epoch, String family, String device, Object wifi, double temperature, int battery) {
        this.rawData = rawData;
        this.epochDateTime = epochDateTime;
        this.epoch = epoch;
        this.family = family;
        this.device = device;
        this.wifi = wifi;
        this.temperature = temperature;
        this.battery = battery;
    }

    public EverHubEvent() {
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getEpochDateTime() {
        return epochDateTime;
    }

    public void setEpochDateTime(String epochDateTime) {
        this.epochDateTime = epochDateTime;
    }

    public Date getEpoch() {
        return epoch;
    }

    public void setEpoch(Date epoch) {
        this.epoch = epoch;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Object getWifi() {
        return wifi;
    }

    public void setWifi(Object wifi) {
        this.wifi = wifi;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
