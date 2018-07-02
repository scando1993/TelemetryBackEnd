package net.pacificsoft.microservices.loka.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "AnalogMessage")
public class Analog extends Message {
    @Column
    private int port;

    @Column
    private int value;

    public Analog() {
    }

    public Analog(int port, int value) {
        this.port = port;
        this.value = value;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Analog{" +
                "port=" + port +
                ", value=" + value +
                '}';
    }
}
