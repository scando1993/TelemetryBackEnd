package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table (name = "rawSensorData")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RawSensorData implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "epoch", nullable = false)
        private long epoch;
        @Column(name = "goAPIResponseID", nullable = false)
        private long goAPIResponseID;
        @Column(name = "furgonID", nullable = false)
        private long furgonID;
        @Column(name = "temperature", nullable = false)
        private float temperature;
        @Column(name = "sensorDataID", nullable = false)
        private long sensorDataID;
        @Column(name = "rawData", nullable = false)
	private String rawData;
        @Column(name = "epochDateTime", nullable = false)
        private Date epochDateTime;
        @Column(name = "family", nullable = false)
        private String family;
        @Column(name = "device", nullable = false)
        private String device;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "wifiScanID", nullable = false)
        private WifiScan wifiScan;
        
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

        public long getGoAPIResponseID() {
            return goAPIResponseID;
        }

        public void setGoAPIResponseID(long goAPIResponseID) {
            this.goAPIResponseID = goAPIResponseID;
        }

        public long getFurgonID() {
            return furgonID;
        }

        public void setFurgonID(long furgonID) {
            this.furgonID = furgonID;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public long getSensorDataID() {
            return sensorDataID;
        }

        public void setSensorDataID(long sensorDataID) {
            this.sensorDataID = sensorDataID;
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

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }
}
