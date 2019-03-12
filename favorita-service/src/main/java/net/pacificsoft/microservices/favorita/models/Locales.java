package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "locales")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Locales implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "numLoc", nullable = false)
        private long numLoc;
        @Column(name = "length", nullable = false)
        private long length;
        @Column(name = "latitude", nullable = false)
        private long latitude;
        @Column(name = "name", nullable = false)
	private String name;
        @Column(name = "place", nullable = false)
        private String place;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ubicacionID")
        private Ubicacion ubication;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getNumLoc() {
            return numLoc;
        }

        public void setNumLoc(long numLoc) {
            this.numLoc = numLoc;
        }

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public long getLatitude() {
            return latitude;
        }

        public void setLatitude(long latitude) {
            this.latitude = latitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public Ubicacion getUbication() {
            return ubication;
        }

        public void setUbication(Ubicacion ubication) {
            this.ubication = ubication;
        }

        public String toString(){
            return "Locales: "+id+" "+numLoc + " "+length+" "+latitude+" "+name+" "+place;
        }
}
