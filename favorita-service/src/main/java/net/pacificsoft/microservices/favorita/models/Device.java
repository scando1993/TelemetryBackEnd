package net.pacificsoft.microservices.favorita.models;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import net.pacificsoft.microservices.favorita.models.application.Ruta;

@Entity
@Table (name = "device")
public class Device{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        
        @Column(name = "family", nullable = false)
	private String family;
        
        @Column(name = "name", nullable = false)
        private String name;
        
        @Column(name = "uuid", nullable = true)
        private String uuid;
        
        @Column(name = "description")
        private String description;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "groupID")
        private Group groupFamily;

        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Ruta> rutas = new HashSet<>();
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Tracking> trackings = new HashSet<>();
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<RawSensorData> rawSensorDatas = new HashSet<>();
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Alerta> alertas = new HashSet<>();
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Telemetria> telemetrias = new HashSet<>();
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<GoApiResponse> goApiResponses = new HashSet<>();
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Set<Status> statuses = new HashSet<>();
        
         @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private ConfigurationDevice configDevice;
        
        /*@OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "featureID")
        private Features features;*/

        public Device(String family, String name) {
            this.family = family;
            this.name = name;
        }
        public Device(){
        }

        public Device(long id, String family, String name, Group groupFamily) {
            this.id = id;
            this.family = family;
            this.name = name;
            this.groupFamily = groupFamily;
            //this.status = status;
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

        public Set<Status> getStatuses() {
            return statuses;
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

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        } 

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }        

        public ConfigurationDevice getConfigDevice() {
            return configDevice;
        }

        public void setConfigDevice(ConfigurationDevice configDevice) {
            this.configDevice = configDevice;
        }  
}
