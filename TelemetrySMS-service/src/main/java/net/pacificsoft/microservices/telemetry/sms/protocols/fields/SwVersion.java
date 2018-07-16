
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SwVersion {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Length")
    @Expose
    private Integer length;
    @SerializedName("PosSMS")
    @Expose
    private Integer posSMS;
    @SerializedName("Range")
    @Expose
    private String range;
    @SerializedName("Range_Hex")
    @Expose
    private String rangeHex;
    @SerializedName("Offset")
    @Expose
    private Integer offset;

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getRangeHex() {
        return rangeHex;
    }

    public void setRangeHex(String rangeHex) {
        this.rangeHex = rangeHex;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("length", length).append("posSMS", posSMS).append("range", range).append("rangeHex", rangeHex).append("offset", offset).toString();
    }

}
