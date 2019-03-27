package net.pacificsoft.microservices.favorita.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "mac")
public class Mac{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "mac", nullable = false)
    private String mac;

    @Column(name = "family", nullable = false)
    private String family;

    public long getId() {
        return id;
    }

        public void setId(long id) {
            this.id = id;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getFamily() {
            return family;
        }

    public void setFamily(String family) {
        this.family = family;
    } 
}
