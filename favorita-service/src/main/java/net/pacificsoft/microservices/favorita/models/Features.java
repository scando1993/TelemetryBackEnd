package net.pacificsoft.microservices.favorita.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "features")
public class Features{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "tracking", nullable = false)
	private boolean tracking;
        @Column(name = "tipo_sensores", nullable = false)
        private String tipo_sensores;
        @Column(name = "modos_transmision", nullable = false)
        private String modos_transmision;
        @Column(name = "version", nullable = false)
        private String version;
        @Column(name = "firmware", nullable = false)
        private String firmware;
        @Column(name = "modelo", nullable = false)
        private String modelo;
        @Column(name = "alimentacion", nullable = false)
        private String alimentacion;
        @Column(name = "nivel_voltaje", nullable = false)
        private double nivel_voltaje;
        @Column(name = "memoria", nullable = false)
        private boolean memoria;
        
        /*@JsonIgnore
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "features")
        @JsonIdentityReference(alwaysAsId = true)
        private Device device;*/

        public Features(boolean tracking, String tipo_sensores, 
                String modos_transmision, String version, String firmware, 
                String modelo, String alimentacion, double nivel_voltaje, 
                boolean memoria) {
            this.tracking = tracking;
            this.tipo_sensores = tipo_sensores;
            this.modos_transmision = modos_transmision;
            this.version = version;
            this.firmware = firmware;
            this.modelo = modelo;
            this.alimentacion = alimentacion;
            this.nivel_voltaje = nivel_voltaje;
            this.memoria = memoria;
        }

        public Features() {
        }

        public Features(long id, boolean tracking,String tipo_sensores,
                String modos_transmision, 
                String version, String firmware, String modelo, 
                String alimentacion, double nivel_voltaje, 
                boolean memoria, Device device) {
            
            this.id = id;
            this.tracking = tracking;
            this.tipo_sensores = tipo_sensores;
            this.modos_transmision = modos_transmision;
            this.version = version;
            this.firmware = firmware;
            this.modelo = modelo;
            this.alimentacion = alimentacion;
            this.nivel_voltaje = nivel_voltaje;
            this.memoria = memoria;
            //this.device = device;
        }
 
        

        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public boolean isTracking() {
            return tracking;
        }

        public void setTracking(boolean tracking) {
            this.tracking = tracking;
        }

        public String getTipo_sensores() {
            return tipo_sensores;
        }

        public void setTipo_sensores(String tipo_sensores) {
            this.tipo_sensores = tipo_sensores;
        }

        public String getModos_transmision() {
            return modos_transmision;
        }

        public void setModos_transmision(String modos_transmision) {
            this.modos_transmision = modos_transmision;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getFirmware() {
            return firmware;
        }

        public void setFirmware(String firmware) {
            this.firmware = firmware;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getAlimentacion() {
            return alimentacion;
        }

        public void setAlimentacion(String alimentacion) {
            this.alimentacion = alimentacion;
        }

        public double getNivel_voltaje() {
            return nivel_voltaje;
        }

        public void setNivel_voltaje(double nivel_voltaje) {
            this.nivel_voltaje = nivel_voltaje;
        }

        public boolean isMemoria() {
            return memoria;
        }

        public void setMemoria(boolean memoria) {
            this.memoria = memoria;
        }

        /*public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }*/
   
}
