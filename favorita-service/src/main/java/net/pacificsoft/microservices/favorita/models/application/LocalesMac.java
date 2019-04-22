package net.pacificsoft.microservices.favorita.models.application;

import net.pacificsoft.microservices.favorita.models.*;
import javax.persistence.*;


@Entity
@Table(name = "locales_mac")
public class LocalesMac{
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        @Column(name = "ssid", nullable = false)
	private String ssid;
        @Column(name = "mac", nullable = false)
	private String mac;
        @Column(name = "password", nullable = false)
	private String password;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "localesid")
        private Locales locales;

        public LocalesMac(String ssid, String mac, String password) {
            this.ssid = ssid;
            this.mac = mac;
            this.password = password;
        }
        public LocalesMac(){}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public Locales getLocales() {
            return locales;
        }

        public void setLocales(Locales locales) {
            this.locales = locales;
        }     

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }    
}
