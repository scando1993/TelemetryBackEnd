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
@Table (name = "ubicacion")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Ubicacion implements Serializable{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "zone", nullable = false)
        private String zone;
        @Column(name = "regional", nullable = false)
	private String regional;
        @Column(name = "province", nullable = false)
        private String province;
        @Column(name = "city", nullable = false)
        private String city;
        
        @JsonIgnore
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "ubication")
        private Set<UbicacionFurgon> ubicacionFurgons = new HashSet<>();

        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "ubication")
        private Set<Bodega> bodegas = new HashSet<>();
        
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "ubication")
        private Set<Locales> locales = new HashSet<>();
        
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "ubication")
        private Set<Formato> formatos = new HashSet<>();
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
        
        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getRegional() {
            return regional;
        }

        public void setRegional(String regional) {
            this.regional = regional;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Set<UbicacionFurgon> getUbicacionFurgons() {
            return ubicacionFurgons;
        }

        public void setUbicacionFurgons(Set<UbicacionFurgon> ubicacionFurgons) {
            this.ubicacionFurgons = ubicacionFurgons;
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

        public Set<Formato> getFormatos() {
            return formatos;
        }

        public void setFormatos(Set<Formato> formatos) {
            this.formatos = formatos;
        }

            @Override
        public String toString(){
            String a = "{\"id\":46,\"nombre\":\"Miguel\",\"empresa\":\"Autentia\"}";
            //return "{\"id\":"+id +",\"zone\":\""+zone+"\",\"regional\":\""+regional+"\",province:\""+province+"\",city:\""+city+"\"}";
            return a;
        }

}
