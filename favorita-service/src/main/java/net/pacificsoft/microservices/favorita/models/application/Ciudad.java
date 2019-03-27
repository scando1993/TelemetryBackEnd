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
@Table(name = "ciudad")
public class Ciudad{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "name", nullable = false)
	private String name;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "provinciaID")
        private Provincia provincia;
                
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "ciudad")
        private Set<Bodega> bodegas = new HashSet<>();
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "ciudad")
        private Set<Locales> locales = new HashSet<>();
        
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

        public Provincia getProvincia() {
            return provincia;
        }

        public void setProvincia(Provincia provincia) {
            this.provincia = provincia;
        }

        public Set<Bodega> getBodegas() {
            return bodegas;
        }

        public void setBodegas(Set<Bodega> bodegas) {
            this.bodegas = bodegas;
        }

        public Set<Locales> getLocales() {
            return locales;
        }

        public void setLocales(Set<Locales> locales) {
            this.locales = locales;
        }
}
