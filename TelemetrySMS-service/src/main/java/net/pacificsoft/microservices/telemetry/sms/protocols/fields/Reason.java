
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Reason {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Length")
    @Expose
    private Integer length;
    @SerializedName("PosSMS")
    @Expose
    private Integer posSMS;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPosSMS() {
        return posSMS;
    }

    public void setPosSMS(Integer posSMS) {
        this.posSMS = posSMS;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("length", length).append("posSMS", posSMS).toString();
    }

}
