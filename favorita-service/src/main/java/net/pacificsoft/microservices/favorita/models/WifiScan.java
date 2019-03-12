package net.pacificsoft.microservices.favorita.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wifiScan")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class WifiScan implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "RSSI", nullable = false)
        private int RSSI;
        @Column(name = "formatoID", nullable = false)
	private String MAC;
        
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "rawSensorDataID")
        private RawSensorData rawSensorData;
        
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
