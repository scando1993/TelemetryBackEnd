package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table (name = "status")
public class Status{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "batery")
        private Integer batery;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "lastTransmision")
        private Date lastTransmision;
        
        @Column(name = "signalLevel")
        private Double signalLevel;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "lastUpdate")
	private Date lastUpdate;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;

        public Status(Integer batery, Date lastTransmision, Double signalLevel, Date lastUpdate) {
            this.batery = batery;
            this.lastTransmision = lastTransmision;
            this.signalLevel = signalLevel;
            this.lastUpdate = lastUpdate;
        }

        public Status() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Integer getBatery() {
            return batery;
        }

        public void setBatery(Integer batery) {
            this.batery = batery;
        }

        public Date getLastTransmision() {
            return lastTransmision;
        }

        public void setLastTransmision(Date lastTransmision) {
            this.lastTransmision = lastTransmision;
        }

        public Double getSignalLevel() {
            return signalLevel;
        }

        public void setSignalLevel(Double signalLevel) {
            this.signalLevel = signalLevel;
        }

        public Date getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(Date lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }
}

        
        
