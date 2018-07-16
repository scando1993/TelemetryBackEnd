package net.pacificsoft.microservices.telemetry.sms.protocols;

public class calibration {
    public String Name;
    public int Length;
    public int PosSMS;
    public int Range[];
    public int Range_Hex[];
    public int Offset;

    public calibration(String name, int length, int posSMS, int[] range, int[] range_Hex, int offset) {
        Name = name;
        Length = length;
        PosSMS = posSMS;
        Range = range;
        Range_Hex = range_Hex;
        Offset = offset;
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

    public int[] getRange() {
        return Range;
    }

    public void setRange(int[] range) {
        Range = range;
    }

    public int[] getRange_Hex() {
        return Range_Hex;
    }

    public void setRange_Hex(int[] range_Hex) {
        Range_Hex = range_Hex;
    }

    public int getOffset() {
        return Offset;
    }

    public void setOffset(int offset) {
        Offset = offset;
    }
}
