package net.pacificsoft.microservices.favorita.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locationGroup")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class LocationGroup implements Serializable{
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;
         
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "locationGroup")
        private Set<Tracking> trackings = new HashSet<>();
        
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

        public Set<Tracking> getTrackings() {
            return trackings;
        }

        public void setTrackings(Set<Tracking> trackings) {
            this.trackings = trackings;
        }   
}
