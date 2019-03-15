package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
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

import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Table (name = "device")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class Device implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        
        @Column(name = "family", nullable = false)
	private String family;
        
        @Column(name = "name", nullable = false)
        private String name;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "groupID")
        private Group groupFamily;

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
        private Set<Alerta> alertas = new HashSet<>();
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Telemetria> telemetrias = new HashSet<>();
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<GoApiResponse> goApiResponses = new HashSet<>();
        
        @JsonIgnore
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Status status;
        
        /*@OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "featureID")
        private Features features;*/

        public Device(String family, String name) {
            this.family = family;
            this.name = name;
        }
        public Device(){
        }

        public Device(long id, String family, String name, Group groupFamily, Status status) {
            this.id = id;
            this.family = family;
            this.name = name;
            this.groupFamily = groupFamily;
            this.status = status;
        }
        
        

        
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

        public Set<Alerta> getAlertas() {
            return alertas;
        }

        public void setAlertas(Set<Alerta> alertas) {
            this.alertas = alertas;
        }

        public Set<Telemetria> getTelemetrias() {
            return telemetrias;
        }

        public void setTelemetrias(Set<Telemetria> telemetrias) {
            this.telemetrias = telemetrias;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Group getGroup() {
            return groupFamily;
        }

           public void setGroup(Group group) {
            this.groupFamily = group;
        }

        
        public Group getGroupFamily(){
            return groupFamily;
        }

    public void setGroupFamily(Group groupFamily) {
        this.groupFamily = groupFamily;
    }

    public Set<GoApiResponse> getGoApiResponses() {
        return goApiResponses;
    }

    /*public Features getFeatures() {
    return features;
    }
    public void setFeatures(Features features) {
    this.features = features;
    }*/
    public void setGoApiResponses(Set<GoApiResponse> goApiResponses) {
        this.goApiResponses = goApiResponses;
    }

    @Override
    public String toString() {
        return "Bodega: "+id+" "+family+" "+name;
    }
}
