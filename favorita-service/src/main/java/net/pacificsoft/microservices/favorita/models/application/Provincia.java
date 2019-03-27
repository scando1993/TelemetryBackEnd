package net.pacificsoft.microservices.favorita.models.application;

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

@Entity
@Table(name = "provincia")
public class Provincia{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "name", nullable = false)
	private String name;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "zonaID")
        private Zona zona;
                
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
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
}
