package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table (name = "status")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Status implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "batery", nullable = false)
        private float batery;
        
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        @Column(name = "last_transmision", nullable = false)
        private Date last_transmision;
        
        @Column(name = "signal_level", nullable = false)
        private float signal_level;
        
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        @Column(name = "last_update", nullable = false)
	private Date last_update;

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public float getBatery() {
            return batery;
        }

        public void setBatery(float batery) {
            this.batery = batery;
        }

        public Date getLast_transmision() {
            return last_transmision;
        }

        public void setLast_transmision(Date last_transmision) {
            this.last_transmision = last_transmision;
        }

        public float getSignal_level() {
            return signal_level;
        }

        public void setSignal_level(float signal_level) {
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
