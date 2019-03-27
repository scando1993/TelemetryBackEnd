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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "zona")
public class Zona{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "name", nullable = false)
	private String name;
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "zona")
        private Set<Provincia> provincias = new HashSet<>();

        public Zona(String name) {
            this.name = name;
        }

        public Zona() {

        }

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

        public Set<Provincia> getProvincias() {
            return provincias;
        }

        public void setProvincias(Set<Provincia> provincias) {
            this.provincias = provincias;
        }
}
