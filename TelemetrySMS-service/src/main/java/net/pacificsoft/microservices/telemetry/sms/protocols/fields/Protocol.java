
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Protocol {

    @SerializedName("sms_data")
    @Expose
    private SmsData smsData;

    public SmsData getSmsData() {
        return smsData;
    }

    public void setSmsData(SmsData smsData) {
        this.smsData = smsData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("smsData", smsData).toString();
    }

}
