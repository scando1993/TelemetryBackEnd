package net.pacificsoft.microservices.favorita.models;

import java.util.Date;
import net.pacificsoft.microservices.favorita.models.application.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import sun.security.util.Password;

@Entity
@Table (name = "configurationDevice")
public class ConfigurationDevice{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "resolution_time", nullable = false)
	private double resolution_time;
        
        @Column(name = "send_time", nullable = false)
        private double send_time;
        
        @Column(name = "ssid", nullable = false)
        private String ssid;
        
        @Column(name = "mac", nullable = false)
        private String mac;
        
        @ColumnTransformer(
        read="AES_DECRYPT(password, '${mms.encryption.key}')",
        write="AES_ENCRYPT(?, '${mms.encryption.key}')")
        @Column(name = "password", nullable = false)
        private String password;
        
        @Column(name = "delta_temperature", nullable = false)
        private double delta_temperature;
        
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "deviceID")
        private Device device;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public double getResolution_time() {
            return resolution_time;
        }

        public void setResolution_time(double resolution_time) {
            this.resolution_time = resolution_time;
        }

        public double getSend_time() {
            return send_time;
        }

        public void setSend_time(double send_time) {
            this.send_time = send_time;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public double getDelta_temperature() {
            return delta_temperature;
        }

        public void setDelta_temperature(double delta_temperature) {
            this.delta_temperature = delta_temperature;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }   
}
