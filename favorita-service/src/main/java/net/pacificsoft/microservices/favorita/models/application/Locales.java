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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table (name = "locales")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Locales implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "numLoc", nullable = false)
        private long numLoc;
        @Column(name = "length", nullable = false)
        private long length;
        @Column(name = "latitude", nullable = false)
        private long latitude;
        @Column(name = "name", nullable = false)
	private String name;
        @Column(name = "family", nullable = false)
        private String family;
        

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ciudadID")
        private Ciudad ciudad;
        
        @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "localInicio")
        private Ruta rutaInicio;
        
        @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "localFin")
        private Ruta rutaFin;
        
        @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "locales")
        private Formato formato;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getNumLoc() {
            return numLoc;
        }

        public void setNumLoc(long numLoc) {
            this.numLoc = numLoc;
        }

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public long getLatitude() {
            return latitude;
        }

        public void setLatitude(long latitude) {
            this.latitude = latitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public Ciudad getCiudad() {
            return ciudad;
        }

        public void setCiudad(Ciudad ciudad) {
            this.ciudad = ciudad;
        }

        public Ruta getRutaInicio() {
            return rutaInicio;
        }

        public void setRutaInicio(Ruta rutaInicio) {
            this.rutaInicio = rutaInicio;
        }

        public Ruta getRutaFin() {
            return rutaFin;
        }

        public void setRutaFin(Ruta rutaFin) {
            this.rutaFin = rutaFin;
        }

        public Formato getFormato() {
            return formato;
        }

        public void setFormato(Formato formato) {
            this.formato = formato;
        }

        
        public String toString(){
            return "Locales: "+id+" "+numLoc + " "+length+" "+latitude+" "+name+" ";
        }
}
