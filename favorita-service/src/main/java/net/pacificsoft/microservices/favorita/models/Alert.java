package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "alerta")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Alert implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "type_alert", nullable = false)
	private String type_alert;
        @Column(name = "message", nullable = false)
        private String message;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "rutaID")
        private Ruta ruta;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTipoAlerta() {
            return type_alert;
        }

        public void setTipoAlerta(String tipo_Alerta) {
            this.type_alert = tipo_Alerta;
        }

        public String getMensaje() {
            return message;
        }

        public void setMensaje(String mensaje) {
            this.message = mensaje;
        }

        public Ruta getRuta() {
            return ruta;
        }

        public void setRuta(Ruta ruta) {
            this.ruta = ruta;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }
   
}
