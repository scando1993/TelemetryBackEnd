package net.pacificsoft.microservices.favorita.models.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
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
import javax.persistence.Table;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.Device;

@Entity
@Table (name = "ruta")
public class Ruta{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "America/Guayaquil")
        @Column(name = "start_date", nullable = false)
	private Date start_date;
        
        @Column(name = "status")
	private String status;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "America/Guayaquil")
        @Column(name = "end_date", nullable = false)
	private Date end_date;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "furgonID")
        private Furgon furgon;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "productoID")
        private Producto producto;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "localInicioID")
        private Locales localInicio;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "localFinID")
        private Locales localFin;
        
        @JsonIgnore
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "ruta")
        private Set<Alerta> alertas = new HashSet<>();

        public Ruta(Date start_date, Date end_date) {
            this.start_date = start_date;
            this.end_date = end_date;
        }

        public Ruta() {
        }

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }   
}
