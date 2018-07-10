package net.pacificsoft.microservices.telemetry.sms.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "devices")
public class Devices {
    @Id
    private ObjectId _id;;

    @Field(value="SSID")
    public long SSID;
    @Field(value="family")
    public int family;
    @Field(value="version")
    public int version;
    @Field(value="reason")
    public int reason;
    @Field(value="datalog_resolution")
    public int datalog_resolution;
    @Field(value="calibration_value")
    public String calibration_value;
    @Field(value="")
    public String hostname;
    @Field(value="battery_level")
    public int baterry_level;
    @Field(value="alarm_delay")
    public int alarm_delay;
    @Field(value = "send_frecuency")
    public int send_frecuency;
    @Field(value = "alarm_buffer")
    public boolean[] alarm_buffer;
    @Field(value = "country_code")
    public int country_code;
    @Field(value = "gsm_server_number")
    public long gsm_server_number;
    @Field(value = "gsm_number")
    public long gsm_number;
    @Field(value = "online_time")
    public int online_time;
    @Field(value = "wakeup_frequency")
    public int wakeup_frequency;



    public Devices() {
    }

    @PersistenceConstructor
    public Devices(int idnews, String title, String url, String publisher,
                String category, String story, String hostname, long timestamp,
                int views) {
        super();

    }

    @Override
    public String toString() {
        return null;
    }
}
