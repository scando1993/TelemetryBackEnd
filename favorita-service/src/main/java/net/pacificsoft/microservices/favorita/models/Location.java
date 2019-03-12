package net.pacificsoft.microservices.favorita.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "location")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Location implements Serializable{
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "groupID")
        private Group group;
        
        @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL,
            mappedBy = "location")
        private AliasLocation aliasLocation;
         
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
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public AliasLocation getAliasLocation() {
            return aliasLocation;
        }

        public void setAliasLocation(AliasLocation aliasLocation) {
            this.aliasLocation = aliasLocation;
        }
}
