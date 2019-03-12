package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "features")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Features implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "tracking", nullable = false)
	private boolean tracking;
        @Column(name = "identificador", nullable = false)
        private String mensaje;
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
        private float nivel_voltaje;
        @Column(name = "memoria", nullable = false)
        private boolean memoria;
        
        /*@JsonIgnore
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "features")
        private Device device;*/

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

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
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

        public float getNivel_voltaje() {
            return nivel_voltaje;
        }

        public void setNivel_voltaje(float nivel_voltaje) {
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
