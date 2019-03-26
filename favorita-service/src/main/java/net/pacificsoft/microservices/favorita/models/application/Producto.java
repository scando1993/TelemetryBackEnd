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
@Table (name = "producto")
public class Producto{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "temp_max", nullable = false)
        private float temp_max;
        @Column(name = "temp_min", nullable = false)
	private float temp_min;
        @Column(name = "temp_max_ideal", nullable = false)
        private float temp_max_ideal;
        @Column(name = "temp_min_ideal", nullable = false)
        private float temp_min_ideal;
        @Column(name = "name", nullable = false)
        private String name;
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "producto")
        private Set<Ruta> rutas = new HashSet<>();
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(float temp_max) {
            this.temp_max = temp_max;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(float temp_min) {
            this.temp_min = temp_min;
        }

        public float getTemp_max_ideal() {
            return temp_max_ideal;
        }

        public void setTemp_max_ideal(float temp_max_ideal) {
            this.temp_max_ideal = temp_max_ideal;
        }

        public float getTemp_min_ideal() {
            return temp_min_ideal;
        }

        public void setTemp_min_ideal(float temp_min_ideal) {
            this.temp_min_ideal = temp_min_ideal;
        }

        public Set<Ruta> getRutas() {
            return rutas;
        }

        public void setRutas(Set<Ruta> rutas) {
            this.rutas = rutas;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
