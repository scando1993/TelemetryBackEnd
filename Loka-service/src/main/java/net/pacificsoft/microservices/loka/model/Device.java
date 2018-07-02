package net.pacificsoft.microservices.loka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
    private long Id;
    private String Name;
    private String Color;
    private boolean Moving;
    private float Battery;
    private long LastCommunication;
    private float Temperature;
    private Position position;
    private List<Temperature> temperatures;
    private String unity;
    private List<Position> positions;

    public Device(long id, String name, String color, boolean moving, float battery, long lastCommunication, float temperature, Position position, List<net.pacificsoft.microservices.loka.model.Temperature> temperatures, String unity, List<Position> positions) {
        Id = id;
        Name = name;
        Color = color;
        Moving = moving;
        Battery = battery;
        LastCommunication = lastCommunication;
        Temperature = temperature;
        this.position = position;
        this.temperatures = temperatures;
        this.unity = unity;
        this.positions = positions;
    }

    public Device() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public boolean isMoving() {
        return Moving;
    }

    public void setMoving(boolean moving) {
        Moving = moving;
    }

    public float getBattery() {
        return Battery;
    }

    public void setBattery(float battery) {
        Battery = battery;
    }

    public long getLastCommunication() {
        return LastCommunication;
    }

    public void setLastCommunication(long lastCommunication) {
        LastCommunication = lastCommunication;
    }

    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float temperature) {
        Temperature = temperature;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<net.pacificsoft.microservices.loka.model.Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<net.pacificsoft.microservices.loka.model.Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Device{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Color='" + Color + '\'' +
                ", Moving=" + Moving +
                ", Battery=" + Battery +
                ", LastCommunication=" + LastCommunication +
                ", Temperature=" + Temperature +
                ", position=" + position +
                ", temperatures=" + temperatures +
                ", unity='" + unity + '\'' +
                ", positions=" + positions +
                '}';
    }
}

