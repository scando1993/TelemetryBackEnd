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
@Table (name = "wifiScan")
public class WifiScan{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    	private long id;
        
        @Column(name = "rssi", nullable = false)
        private int rssi;
        
        @Column(name = "formatoID", nullable = false)
	    private String mac;
                
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "rawSensorDataID")
        private RawSensorData rawSensorData;

        public WifiScan() {
        }
        
        public WifiScan(int rssi, String mac) {
            this.rssi = rssi;
            this.mac = mac;
        }
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getRssi() {
            return rssi;
        }

        public void setRssi(int rssi) {
            this.rssi = rssi;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public RawSensorData getRawSensorData() {
            return rawSensorData;
        }

        public void setRawSensorData(RawSensorData rawSensorData) {
            this.rawSensorData = rawSensorData;
        }     
}
