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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "status")
public class Status{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "batery")
        private int batery;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "last_transmision")
        private Date last_transmision;
        
        @Column(name = "signal_level")
        private double signal_level;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "last_update")
	private Date last_update;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;

        public Status(int batery, Date last_transmision, double signal_level, Date last_update) {
            this.batery = batery;
            this.last_transmision = last_transmision;
            this.signal_level = signal_level;
            this.last_update = last_update;
        }

        public Status() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getBatery() {
            return batery;
        }

        public void setBatery(int batery) {
            this.batery = batery;
        }

        public Date getLast_transmision() {
            return last_transmision;
        }

        public void setLast_transmision(Date last_transmision) {
            this.last_transmision = last_transmision;
        }

        public double getSignal_level() {
            return signal_level;
        }

        public void setSignal_level(double signal_level) {
            this.signal_level = signal_level;
        }

        public Date getLast_update() {
            return last_update;
        }

        public void setLast_update(Date last_update) {
            this.last_update = last_update;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }
}

        
        
