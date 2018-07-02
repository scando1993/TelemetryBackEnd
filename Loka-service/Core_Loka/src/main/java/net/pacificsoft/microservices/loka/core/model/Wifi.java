package net.pacificsoft.microservices.loka.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "WifiMessage")
public class Wifi extends Message{

    @Column
    private String mac = "";
    @Column
    private int rssi = -1;
    @Column
    private int channel = -1;

    public Wifi() {
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getChannel() {
        return this.channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
