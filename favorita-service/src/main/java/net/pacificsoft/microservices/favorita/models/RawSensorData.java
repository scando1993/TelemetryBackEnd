package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity
@Table (name = "rawSensorData")
public class RawSensorData{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "epoch", nullable = false)
        private long epoch;
        
        @Column(name = "temperature", nullable = false)
        private double temperature;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "epochDateTime", nullable = false)
        private Date epochDateTime;
        
        @Column(name = "rawData", nullable = false)
        private String rawData;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID", nullable = false)
        private Device device;

        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "rawSensorData")
        @JsonIdentityReference(alwaysAsId = true)
        private Set<WifiScan> wifiScans = new HashSet<>();

        @OneToMany(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,
                mappedBy = "rawSensorData")
        @JsonIdentityReference(alwaysAsId = true)
        private Set<SigfoxMessage> sigfoxMessages = new HashSet<>();
    /*
    public RawSensorData(long epoch, float temperature, Date epochDateTime, String rawData) {
        this.epoch = epoch;
        this.temperature = temperature;
        this.epochDateTime = epochDateTime;
        this.rawData = rawData;
    }
    */
        public RawSensorData(){}

        public RawSensorData(long epoch, double temperature, Date epochDateTime, String rawData) {
            this.epoch = epoch;
            this.temperature = temperature;
            this.epochDateTime = epochDateTime;
            this.rawData = rawData;
        }      

        public RawSensorData(long id, long epoch, double temperature, Date epochDateTime, String rawData, Device device) {
            this.id = id;
            this.epoch = epoch;
            this.temperature = temperature;
            this.epochDateTime = epochDateTime;
            this.rawData = rawData;
            this.device = device;
        }
        public JSONObject toJSON(){
            JSONObject json = new JSONObject();
            json.put("epoch", this.epoch);
            json.put("epochDateTime", this.epochDateTime);
            json.put("rawData", this.rawData);
            json.put("temperature",this.temperature);
            return json;
        }

        

        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getEpoch() {
            return epoch;
        }

        public void setEpoch(long epoch) {
            this.epoch = epoch;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getRawData() {
            return rawData;
        }

        public void setRawData(String rawData) {
            this.rawData = rawData;
        }

        public Date getEpochDateTime() {
            return epochDateTime;
        }

        public void setEpochDateTime(Date epochDateTime) {
            this.epochDateTime = epochDateTime;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public Set<WifiScan> getWifiScans() {
            return wifiScans;
        }

        public void setWifiScans(Set<WifiScan> wifiScans) {
            this.wifiScans = wifiScans;
        }

        public Set<SigfoxMessage> getSigfoxMessages() {
            return sigfoxMessages;
        }

        public void setSigfoxMessages(Set<SigfoxMessage> sigfoxMessages) {
            this.sigfoxMessages = sigfoxMessages;
        }   
}
