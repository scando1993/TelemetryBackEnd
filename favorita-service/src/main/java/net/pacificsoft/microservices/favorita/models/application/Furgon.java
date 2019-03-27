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
@Table (name = "furgon")
public class Furgon {

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "numFurgon", nullable = false)
        private long numFurgon;
        
        @Column(name = "name", nullable = false)
	private String name;
        
        @OneToMany(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "furgon")
        private Set<Ruta> rutas = new HashSet<>();

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

        public Set<Ruta> getRutas() {
            return rutas;
        }

        public void setRutas(Set<Ruta> rutas) {
            this.rutas = rutas;
        }
}
