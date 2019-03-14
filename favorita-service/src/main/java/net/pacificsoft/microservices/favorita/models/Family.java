package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "family")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class Family implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
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
