package net.pacificsoft.microservices.telemetry.sms.protocols;

public class sms_data {
  public Header Header;
  public Payload Payload;
  public Signature Signature;

    public net.pacificsoft.microservices.telemetry.sms.protocols.Header getHeader() {
        return Header;
    }

    public void setHeader(net.pacificsoft.microservices.telemetry.sms.protocols.Header header) {
        Header = header;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.Payload getPayload() {
        return Payload;
    }

    public void setPayload(net.pacificsoft.microservices.telemetry.sms.protocols.Payload payload) {
        Payload = payload;
    }

    public net.pacificsoft.microservices.telemetry.sms.protocols.Signature getSignature() {
        return Signature;
    }

    public void setSignature(net.pacificsoft.microservices.telemetry.sms.protocols.Signature signature) {
        Signature = signature;
    }
}
