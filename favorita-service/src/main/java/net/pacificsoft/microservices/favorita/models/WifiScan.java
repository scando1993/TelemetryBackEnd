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

        
        @Column(name = "RSSI", nullable = false)
        private int RSSI;
        
        @Column(name = "formatoID", nullable = false)
	private String MAC;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "rawSensorDataID")
        private RawSensorData rawSensorData;

        public WifiScan() {
        }
        
        public WifiScan(int RSSI, String MAC) {
            this.RSSI = RSSI;
            this.MAC = MAC;
        }
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getRSSI() {
            return RSSI;
        }

        public void setRSSI(int RSSI) {
            this.RSSI = RSSI;
        }

        public String getMAC() {
            return MAC;
        }

        public void setMAC(String MAC) {
            this.MAC = MAC;
        }

        public RawSensorData getRawSensorData() {
            return rawSensorData;
        }

        public void setRawSensorData(RawSensorData rawSensorData) {
            this.rawSensorData = rawSensorData;
        }     
}
