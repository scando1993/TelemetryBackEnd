package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "device")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Device implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        @Column(name = "family", nullable = false)
	private String family;
        @Column(name = "name", nullable = false)
        private String name;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "trackingID")
        private Tracking tracking;
        
        @JsonIgnore
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "device")
        private Ruta ruta;
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Tracking getTracking() {
            return tracking;
        }

        public void setTracking(Tracking tracking) {
            this.tracking = tracking;
        }

        public Ruta getRuta() {
            return ruta;
        }

        public void setRuta(Ruta ruta) {
            this.ruta = ruta;
        }
        
        @Override
        public String toString(){
            return "Bodega: "+id+" "+family+" "+name;
        }
}
