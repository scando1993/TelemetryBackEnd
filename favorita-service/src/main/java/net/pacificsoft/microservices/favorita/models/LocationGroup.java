package net.pacificsoft.microservices.favorita.models;

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
@Table(name = "locationGroup")
public class LocationGroup{
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        
        @Column(name = "name", nullable = false)
	private String name;
         
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "locationGroup")
        private Set<Tracking> trackings = new HashSet<>();

        public LocationGroup(){
        }
        public LocationGroup(String name) {
            this.name = name;
        }

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
