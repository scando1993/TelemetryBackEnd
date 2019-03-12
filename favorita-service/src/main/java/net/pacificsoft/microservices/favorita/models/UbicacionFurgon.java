package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "ubicacionFurgon")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class UbicacionFurgon implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ubicacionID", nullable = false)
        private Ubicacion ubication;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "furgonID", nullable = false)
        private Furgon furgon;
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Ubicacion getUbication() {
            return ubication;
        }

        public void setUbication(Ubicacion ubication) {
            this.ubication = ubication;
        }

        public Furgon getFurgon() {
            return furgon;
        }

        public void setFurgon(Furgon furgon) {
            this.furgon = furgon;
        }

}
