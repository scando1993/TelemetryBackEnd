package net.pacificsoft.microservices.favorita.models.application;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "provincia")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Provincia implements Serializable{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "zonaID")
        private Zona zona;
        
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "provincia")
        private Set<Ciudad> ciudades = new HashSet<>();

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }     

        public Zona getZona() {
            return zona;
        }

        public void setZona(Zona zona) {
            this.zona = zona;
        }

        public Set<Ciudad> getCiudades() {
            return ciudades;
        }

        public void setCiudades(Set<Ciudad> ciudades) {
            this.ciudades = ciudades;
        }


        @Override
        public String toString(){
            return "Provincia: "+id+" "+name;
        }
}
