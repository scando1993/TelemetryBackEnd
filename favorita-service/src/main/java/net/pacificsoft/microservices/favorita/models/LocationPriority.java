package net.pacificsoft.microservices.favorita.models;

import javax.persistence.*;

@Entity
@Table(name = "locationPriority")
public class LocationPriority{
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;
        @Column(name = "priority", nullable = false)
        private int priority;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "groupid")
        private Group groupFamily;

        public LocationPriority(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        public LocationPriority(){}

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

    public Group getGroupFamily() {
        return groupFamily;
    }

    public void setGroupFamily(Group groupFamily) {
        this.groupFamily = groupFamily;
    }

        
}
