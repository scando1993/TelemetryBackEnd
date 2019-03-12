package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "furgon")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Furgon implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "numFurgon", nullable = false)
        private long numFurgon;
        
        //@JsonIgnore
        @Column(name = "name", nullable = false)
	private String name;
        
        @JsonIgnore
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "furgon")
        private Set<UbicacionFurgon> ubicacionFurgons = new HashSet<>();
        
        @JsonIgnore
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "furgon")
        private Ruta ruta;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getNumFurgon() {
            return numFurgon;
        }

        public void setNumFurgon(long numFurgon) {
            this.numFurgon = numFurgon;
        } 

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<UbicacionFurgon> getUbicacionFurgons() {
            return ubicacionFurgons;
        }

        public void setUbicacionFurgons(Set<UbicacionFurgon> ubicacionFurgons) {
            this.ubicacionFurgons = ubicacionFurgons;
        }

        public Ruta getRuta() {
            return ruta;
        }

        public void setRuta(Ruta ruta) {
            this.ruta = ruta;
        } 
}
