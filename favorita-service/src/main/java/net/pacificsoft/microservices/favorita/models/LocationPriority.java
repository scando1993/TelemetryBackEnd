package net.pacificsoft.springbootcrudrest.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locationPriority")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class LocationPriority implements Serializable{
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
        @Column(name = "name", nullable = false)
	    private String name;
        @Column(name = "priority", nullable = false)
        private int priority;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "trackingID")
        private Tracking tracking;
        
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

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public Tracking getTracking() {
            return tracking;
        }

        public void setTracking(Tracking tracking) {
            this.tracking = tracking;
        }

        
}
