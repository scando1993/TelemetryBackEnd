
package net.pacificsoft.microservices.telemetry.sms.protocols.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Signature {

    @SerializedName("Batery_Level")
    @Expose
    private BateryLevel bateryLevel;
    @SerializedName("Alarm_Delay")
    @Expose
    private AlarmDelay alarmDelay;
    @SerializedName("NotifyCycleH")
    @Expose
    private NotifyCycleH notifyCycleH;
    @SerializedName("Alarm_Buffer")
    @Expose
    private AlarmBuffer alarmBuffer;
    @SerializedName("Country_Code")
    @Expose
    private CountryCode countryCode;
    @SerializedName("TelNbr1")
    @Expose
    private TelNbr1 telNbr1;
    @SerializedName("TelNbr2")
    @Expose
    private TelNbr2 telNbr2;
    @SerializedName("Online_Time")
    @Expose
    private OnlineTime onlineTime;
    @SerializedName("WakeUp_frequency")
    @Expose
    private WakeUpFrequency wakeUpFrequency;
    @SerializedName("Send_time")
    @Expose
    private SendTime sendTime;
    @SerializedName("CustomerID")
    @Expose
    private CustomerID customerID;
    @SerializedName("Max_SMS")
    @Expose
    private MaxSMS maxSMS;
    @SerializedName("Gsm_Signal")
    @Expose
    private GsmSignal gsmSignal;
    @SerializedName("Checksum")
    @Expose
    private Checksum checksum;
    @SerializedName("Epoch_time_sent")
    @Expose
    private Epoch_time_sent epoch_time_sent;

    public BateryLevel getBateryLevel() {
        return bateryLevel;
    }

    public void setBateryLevel(BateryLevel bateryLevel) {
        this.bateryLevel = bateryLevel;
    }

    public AlarmDelay getAlarmDelay() {
        return alarmDelay;
    }

    public void setAlarmDelay(AlarmDelay alarmDelay) {
        this.alarmDelay = alarmDelay;
    }

    public NotifyCycleH getNotifyCycleH() {
        return notifyCycleH;
    }

    public void setNotifyCycleH(NotifyCycleH notifyCycleH) {
        this.notifyCycleH = notifyCycleH;
    }

    public AlarmBuffer getAlarmBuffer() {
        return alarmBuffer;
    }

    public void setAlarmBuffer(AlarmBuffer alarmBuffer) {
        this.alarmBuffer = alarmBuffer;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public TelNbr1 getTelNbr1() {
        return telNbr1;
    }

    public void setTelNbr1(TelNbr1 telNbr1) {
        this.telNbr1 = telNbr1;
    }

    public TelNbr2 getTelNbr2() {
        return telNbr2;
    }

    public void setTelNbr2(TelNbr2 telNbr2) {
        this.telNbr2 = telNbr2;
    }

    public OnlineTime getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(OnlineTime onlineTime) {
        this.onlineTime = onlineTime;
    }

    public WakeUpFrequency getWakeUpFrequency() {
        return wakeUpFrequency;
    }

    public void setWakeUpFrequency(WakeUpFrequency wakeUpFrequency) {
        this.wakeUpFrequency = wakeUpFrequency;
    }

    public SendTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(SendTime sendTime) {
        this.sendTime = sendTime;
    }

    public CustomerID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(CustomerID customerID) {
        this.customerID = customerID;
    }

    public MaxSMS getMaxSMS() {
        return maxSMS;
    }

    public void setMaxSMS(MaxSMS maxSMS) {
        this.maxSMS = maxSMS;
    }

    public GsmSignal getGsmSignal() {
        return gsmSignal;
    }

    public void setGsmSignal(GsmSignal gsmSignal) {
        this.gsmSignal = gsmSignal;
    }

    public Checksum getChecksum() {
        return checksum;
    }

    public void setChecksum(Checksum checksum) {
        this.checksum = checksum;
    }

    public Epoch_time_sent getEpoch() {
        return epoch_time_sent;
    }

    public void setEpoch(Epoch_time_sent epoch_time_sent) {
        this.epoch_time_sent = epoch_time_sent;
    }

}
