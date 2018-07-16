package net.pacificsoft.microservices.telemetry.sms.protocols;

public class reason {
    public String Name;
    public int Length;

    public reason(String name, int length) {
        Name = name;
        Length = length;
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
}
