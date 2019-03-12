package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "ruta")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Ruta implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "start_date", nullable = false)
	private String start_date;
        
        @Column(name = "end_date", nullable = false)
	private String end_date;
        
        @Column(name = "start_hour", nullable = false)
	private String start_hour;
        
        @Column(name = "end_hour", nullable = false)
	private String end_hour;
        
        @Column(name = "temp_max_ap", nullable = false)
	private float temp_max_ap;
        
        @Column(name = "temp_min_ap", nullable = false)
	private float temp_min_ap;
        
        @Column(name = "temp_max_ideal", nullable = false)
	private float temp_max_ideal;
        
        @Column(name = "temp_min_ideal", nullable = false)
	private float temp_min_ideal;
        
        
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "furgonID")
        private Furgon furgon;
        
        
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }

    public float getTemp_max_ap() {
        return temp_max_ap;
    }

    public void setTemp_max_ap(float temp_max_ap) {
        this.temp_max_ap = temp_max_ap;
    }

    public float getTemp_min_ap() {
        return temp_min_ap;
    }

    public void setTemp_min_ap(float temp_min_ap) {
        this.temp_min_ap = temp_min_ap;
    }

    public float getTemp_max_ideal() {
        return temp_max_ideal;
    }

    public void setTemp_max_ideal(float temp_max_ideal) {
        this.temp_max_ideal = temp_max_ideal;
    }

    public float getTemp_min_ideal() {
        return temp_min_ideal;
    }

    public void setTemp_min_ideal(float temp_min_ideal) {
        this.temp_min_ideal = temp_min_ideal;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    
        public Furgon getFurgon() {
            return furgon;
        }

        public void setFurgon(Furgon furgon) {
            this.furgon = furgon;
        }

        @Override
        public String toString(){
            return start_date +" "+start_hour+" "+end_date+" "+end_hour;
        }
        
}
