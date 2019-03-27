package net.pacificsoft.microservices.favorita.models.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bodega")
public class Bodega{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ciudadID")
        private Ciudad ciudad;

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

        public Ciudad getCiudad() {
            return ciudad;
        }

        public void setCiudad(Ciudad ciudad) {
            this.ciudad = ciudad;
        }
}
