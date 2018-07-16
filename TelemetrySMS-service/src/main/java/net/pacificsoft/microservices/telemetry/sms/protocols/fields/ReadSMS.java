
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ReadSMS {

    @SerializedName("Protocol")
    @Expose
    private Protocol protocol;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("protocol", protocol).toString();
    }

}
