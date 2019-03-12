package net.pacificsoft.springbootcrudrest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.sql.Time;
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
@Table (name = "ruta")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Ruta implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "start_date", nullable = false)
	private Date start_date;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "end_date", nullable = false)
	private Date end_date;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "furgonID")
        private Furgon furgon;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "productoID")
        private Producto producto;
        
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "localInicioID")
        private Locales localInicio;
        
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "localFinID")
        private Locales localFin;
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "ruta")
        private Set<Alerta> alertas = new HashSet<>();
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Date getStart_date() {
            return start_date;
        }

        public void setStart_date(Date start_date) {
            this.start_date = start_date;
        }

        public Date getEnd_date() {
            return end_date;
        }

        public void setEnd_date(Date end_date) {
            this.end_date = end_date;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public Furgon getFurgon() {
            return furgon;
        }

        public void setFurgon(Furgon furgon) {
            this.furgon = furgon;
        }

        public Locales getLocalInicio() {
            return localInicio;
        }

        public void setLocalInicio(Locales localInicio) {
            this.localInicio = localInicio;
        }

        public Locales getLocalFin() {
            return localFin;
        }

        public void setLocalFin(Locales localFin) {
            this.localFin = localFin;
        }

        public Set<Alerta> getAlertas() {
            return alertas;
        }

        public void setAlertas(Set<Alerta> alertas) {
            this.alertas = alertas;
        }   
        
        
        
}
