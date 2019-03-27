package net.pacificsoft.microservices.favorita.models;

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
@Table(name = "family")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupid")
    private Group groupFamily;

    public Family(String name) {
        this.name = name;
    }

    public Family() {
    }

    public Group getGroupFamily() {
        return groupFamily;
    }

    public void setGroupFamily(Group groupFamily) {
        this.groupFamily = groupFamily;
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

    public Group getGroup() {
        return groupFamily;
    }

    public void setGroup(Group group) {
        this.groupFamily = group;
    }
}
