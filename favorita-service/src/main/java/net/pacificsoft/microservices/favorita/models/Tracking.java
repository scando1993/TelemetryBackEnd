package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "tracking")
public class Tracking {

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "location", nullable = false)
	private String location;
        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "deviceID")
        private Device device;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "locationGroupID")
        private LocationGroup locationGroup;
        
        @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "tracking")
        private Set<LocationPriority> locationPrioritys = new HashSet<>();
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Column(name = "dtm")
        private Date dtm;

        public Tracking(String location, Date dtm) {
            this.location = location;
            this.dtm = dtm;
        }
        public Tracking() {

        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public LocationGroup getLocationGroup() {
            return locationGroup;
        }

        public void setLocationGroup(LocationGroup locationGroup) {
            this.locationGroup = locationGroup;
        }
        
        public Date getDtm() {
            return dtm;
        }

        public void setDtm(Date dtm) {
            this.dtm = dtm;
        }

        public Set<LocationPriority> getLocationPrioritys() {
            return locationPrioritys;
        }

        public void setLocationPrioritys(Set<LocationPriority> locationPrioritys) {
            this.locationPrioritys = locationPrioritys;
        }
}
