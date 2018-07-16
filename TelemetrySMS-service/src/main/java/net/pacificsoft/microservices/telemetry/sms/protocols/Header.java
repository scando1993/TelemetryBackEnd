package net.pacificsoft.microservices.telemetry.sms.protocols;

public class Header {
    public SSID SSID;
    public sw_version sw_version;
    public reason reason;
    public cycle_time cycle_time;
    public calibration calibration;

    public Header(net.pacificsoft.microservices.telemetry.sms.protocols.SSID SSID, net.pacificsoft.microservices.telemetry.sms.protocols.sw_version sw_version, net.pacificsoft.microservices.telemetry.sms.protocols.reason reason, net.pacificsoft.microservices.telemetry.sms.protocols.cycle_time cycle_time, net.pacificsoft.microservices.telemetry.sms.protocols.calibration calibration) {
        this.SSID = SSID;
        this.sw_version = sw_version;
        this.reason = reason;
        this.cycle_time = cycle_time;
        this.calibration = calibration;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.SSID getSSID() {
        return SSID;
    }

    public void setSSID(net.pacificsoft.microservices.telemetry.sms.protocols.SSID SSID) {
        this.SSID = SSID;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.sw_version getSw_version() {
        return sw_version;
    }

    public void setSw_version(net.pacificsoft.microservices.telemetry.sms.protocols.sw_version sw_version) {
        this.sw_version = sw_version;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.reason getReason() {
        return reason;
    }

    public void setReason(net.pacificsoft.microservices.telemetry.sms.protocols.reason reason) {
        this.reason = reason;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.cycle_time getCycle_time() {
        return cycle_time;
    }

    public void setCycle_time(net.pacificsoft.microservices.telemetry.sms.protocols.cycle_time cycle_time) {
        this.cycle_time = cycle_time;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.calibration getCalibration() {
        return calibration;
    }

    public void setCalibration(net.pacificsoft.microservices.telemetry.sms.protocols.calibration calibration) {
        this.calibration = calibration;
    }
}
