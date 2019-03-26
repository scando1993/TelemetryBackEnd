package net.pacificsoft.microservices.favorita.models;

import java.sql.Time;
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
@Table (name = "sigFoxMessage")
public class SigfoxMessage{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "seqNumber", nullable = false)
        private long seqNumber;
        @Column(name = "msgID", nullable = false)
	private int msgID;
        @Column(name = "device", nullable = false)
	private String device;
        @Column(name = "frame", nullable = false)
	private int frame;
        @Column(name = "time", nullable = false)
	private Time time;
        @Column(name = "payload1", nullable = false)
	private String payload1;
        @Column(name = "payload2", nullable = false)
	private String payload2;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "rawSensorDataID")
        private RawSensorData rawSensorData;
        
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getSeqNumber() {
            return seqNumber;
        }

        public void setSeqNumber(long seqNumber) {
            this.seqNumber = seqNumber;
        }

        public int getMsgID() {
            return msgID;
        }

        public void setMsgID(int msgID) {
            this.msgID = msgID;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public int getFrame() {
            return frame;
        }

        public void setFrame(int frame) {
            this.frame = frame;
        }

        public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public String getPayload1() {
            return payload1;
        }

        public void setPayload1(String payload1) {
            this.payload1 = payload1;
        }

        public String getPayload2() {
            return payload2;
        }

        public void setPayload2(String payload2) {
            this.payload2 = payload2;
        }

        public RawSensorData getRawSensorData() {
            return rawSensorData;
        }

        public void setRawSensorData(RawSensorData rawSensorData) {
            this.rawSensorData = rawSensorData;
        } 
        
}
