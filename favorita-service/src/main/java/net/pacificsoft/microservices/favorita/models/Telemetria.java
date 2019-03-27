package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Table (name = "telemetria")
public class Telemetria{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "dtm", nullable = false)
	private Date dtm;
        
        @Column(name = "name", nullable = false)
	private String name;
        
        @Column(name = "value", nullable = false)
	private double value;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "deviceID")
        private Device device;

        public Telemetria(Date Dtm, String name, double value) {
            this.dtm = Dtm;
            this.name = name;
            this.value = value;
        }

        public Telemetria() {
        }

        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Date getDtm() {
            return dtm;
        }

        public void setDtm(Date Dtm) {
            this.dtm = Dtm;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }        
}
