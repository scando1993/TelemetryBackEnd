package net.pacificsoft.microservices.favorita.models.application;

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
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ciudad")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Ciudad implements Serializable{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "provinciaID")
        private Provincia provincia;

        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "ciudad")
        private Set<Bodega> bodegas = new HashSet<>();
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
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

        
        
        @Override
        public String toString(){
            return "Ciudad: "+id+" "+name;
        }
}
