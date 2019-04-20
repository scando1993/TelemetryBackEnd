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
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "detailConfiguration")
public class DetailConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ssid", nullable = false)
    private String ssid;

    @Column(name = "mac", nullable = false)
    private String mac;

    @ColumnTransformer(
    read="AES_DECRYPT(password, '${mms.encryption.key}')",
    write="AES_ENCRYPT(?, '${mms.encryption.key}')")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "configurationID")
    private ConfigurationDevice configDevice;

    public DetailConfiguration(String ssid, String mac, String password) {
        this.ssid = ssid;
        this.mac = mac;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ConfigurationDevice getConfigDevice() {
        return configDevice;
    }

    public void setConfigDevice(ConfigurationDevice configDevice) {
        this.configDevice = configDevice;
    }
}
