package net.pacificsoft.microservices.telemetry.sms.protocols;

public class Payload {
    public String Name;
    public int Length;
    public int PosSMS;

    public Payload(String name, int length, int posSMS) {
        Name = name;
        Length = length;
        PosSMS = posSMS;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public int getPosSMS() {
        return PosSMS;
    }

    public void setPosSMS(int posSMS) {
        PosSMS = posSMS;
    }
}
