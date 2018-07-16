
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SmsData {

    @SerializedName("Header")
    @Expose
    private Header header;
    @SerializedName("Payload")
    @Expose
    private Payload payload;
    @SerializedName("Signature")
    @Expose
    private Object signature;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Object getSignature() {
        return signature;
    }

    public void setSignature(Object signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("header", header).append("payload", payload).append("signature", signature).toString();
    }

}
