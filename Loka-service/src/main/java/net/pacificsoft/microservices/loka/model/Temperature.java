package net.pacificsoft.microservices.loka.model;

public class Temperature {
    private long date;
    private float value;

    public Temperature() {
    }

    public Temperature(long date, float value) {
        this.date = date;
        this.value = value;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}
