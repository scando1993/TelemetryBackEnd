package net.pacificsoft.microservices.telemetry.sms.protocols;

public class Protocol {
    private sms_data sms_data;

    public Protocol(net.pacificsoft.microservices.telemetry.sms.protocols.sms_data sms_data) {
        this.sms_data = sms_data;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.sms_data getSms_data() {
        return sms_data;
    }

    public void setSms_data(net.pacificsoft.microservices.telemetry.sms.protocols.sms_data sms_data) {
        this.sms_data = sms_data;
    }
}
