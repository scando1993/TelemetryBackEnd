
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @SerializedName("SSID")
    @Expose
    private SSID sSID;
    @SerializedName("sw_version")
    @Expose
    private SwVersion swVersion;
    @SerializedName("reason")
    @Expose
    private Reason reason;
    @SerializedName("cycle_time")
    @Expose
    private CycleTime cycleTime;
    @SerializedName("calibration")
    @Expose
    private Calibration calibration;

    public SSID getSSID() {
        return sSID;
    }

    public void setSSID(SSID sSID) {
        this.sSID = sSID;
    }

    public SwVersion getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(SwVersion swVersion) {
        this.swVersion = swVersion;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Calibration getCalibration() {
        return calibration;
    }

    public void setCalibration(Calibration calibration) {
        this.calibration = calibration;
    }

}
