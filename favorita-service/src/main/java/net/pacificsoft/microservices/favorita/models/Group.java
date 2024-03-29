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
@Table(name = "groupFamily")
public class Group{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "groupFamily")
    private Set<Device> devices = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "groupFamily")
    private Set<Family> families = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "groupFamily")
    private Set<LocationGroup> locationGroups = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "groupFamily")
    private Set<LocationPriority> locationPrioritys = new HashSet<>();

    public Group() {
    }

    public Group(String name) {
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

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<Family> getFamilies() {
        return families;
    }

    public void setFamilies(Set<Family> families) {
        this.families = families;
    }

    public Set<LocationGroup> getLocationGroups() {
        return locationGroups;
    }

    public void setLocationGroups(Set<LocationGroup> locationGroups) {
        this.locationGroups = locationGroups;
    }

    public Set<LocationPriority> getLocationPrioritys() {
        return locationPrioritys;
    }

    public void setLocationPrioritys(Set<LocationPriority> locationPrioritys) {
        this.locationPrioritys = locationPrioritys;
    }
    
    
}
