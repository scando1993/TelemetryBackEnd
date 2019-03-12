package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Device implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        @Column(name = "family", nullable = false)
	private String family;
        @Column(name = "name", nullable = false)
        private String name;

        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Ruta> rutas = new HashSet<>();
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Tracking> trackings = new HashSet<>();
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<RawSensorData> rawSensorDatas = new HashSet<>();
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Alert> alerts = new HashSet<>();
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Telemetry> telemetries = new HashSet<>();
        
        @JsonIgnore
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Status status;
        
        /*@OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "featureID")
        private Features features;*/
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Ruta> getRutas() {
            return rutas;
        }

        public void setRutas(Set<Ruta> rutas) {
            this.rutas = rutas;
        }

        public Set<Tracking> getTrackings() {
            return trackings;
        }

        public void setTrackings(Set<Tracking> trackings) {
            this.trackings = trackings;
        }

        public Set<RawSensorData> getRawSensorDatas() {
            return rawSensorDatas;
        }

        public void setRawSensorDatas(Set<RawSensorData> rawSensorDatas) {
            this.rawSensorDatas = rawSensorDatas;
        }

        public Set<Alert> getAlerts() {
            return alerts;
        }

        public void setAlerts(Set<Alert> alerts) {
            this.alerts = alerts;
        }

        public Set<Telemetry> getTelemetries() {
            return telemetries;
        }

        public void setTelemetries(Set<Telemetry> telemetries) {
            this.telemetries = telemetries;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        /*public Features getFeatures() {
            return features;
        }

        public void setFeatures(Features features) {
            this.features = features;
        }*/
        
        @Override
        public String toString(){
            return "Bodega: "+id+" "+family+" "+name;
        }
}
