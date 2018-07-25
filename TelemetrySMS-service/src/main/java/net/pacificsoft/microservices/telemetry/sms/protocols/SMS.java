package net.pacificsoft.microservices.telemetry.sms.protocols;

import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SMS {
    Timestamp _id ;
    List<String> SSID = new ArrayList();
    List<String> sw_version = new ArrayList();
    List<String> reason = new ArrayList();
    List<String> cycle_time = new ArrayList();
    List<String> calibration = new ArrayList();
    List<String> Payload = new ArrayList();
    List<String> Batery_level = new ArrayList();
    List<String> Alarm_Delay = new ArrayList();
    List<String> NotifyCycleH = new ArrayList();
    List<String> Alarm_Buffer = new ArrayList();
    List<String> CountryCode = new ArrayList();
    List<String> TelNbr1 = new ArrayList();
    List<String> TelNbr2 = new ArrayList();
    List<String> Online_time = new ArrayList();
    List<String> WakeUp_frequency = new ArrayList();
    List<String> Send_time = new ArrayList();
    List<String> CustomerID = new ArrayList();
    List<String> Max_SMS = new ArrayList();
    List<String> Gsm_Signal = new ArrayList();
    List<String> Checksum = new ArrayList();
    //    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;

    public Timestamp getId() {
        return _id;
    }

    public void setId(Timestamp id) {
        _id = id;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public SMS(ReadSMS sms_reader , String sms) {
        SSID.add(sms_reader.getProtocol().getSmsData().getHeader().getSSID().getName());
        SSID.add (sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getSSID().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getSSID().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getSSID().getLength()) );
        SSID.add (String.valueOf(ConverterSms.HardConverter(SSID.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getHeader().getSSID().getOffset())));

        sw_version.add ( sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getName() );
        sw_version.add ( sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getLength())) ;
        sw_version.add (String.valueOf(ConverterSms.SimpleConverterToAscii(sw_version.get(1),sms_reader , sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getOffset())));

        reason.add (sms_reader.getProtocol().getSmsData().getHeader().getReason().getName() )  ;
        reason.add (sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getReason().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getReason().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getReason().getLength()));
        reason.add (reason.get(1));

        cycle_time.add (sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getName())  ;
        cycle_time.add  (sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getPosSMS(), sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getLength()));
        cycle_time.add (String.valueOf(ConverterSms.SimpleConverterToAscii(cycle_time.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getOffset())));

        calibration.add (sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getName())  ;
        calibration.add  (sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getLength())) ;
        //System.out.println(calibration.get(1)));
        calibration .add (ConverterSms.SimpleConverterFromHex(calibration.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getOffset()));

        Payload.add (sms_reader.getProtocol().getSmsData().getPayload().getName() ) ;
        Payload.add  (sms.substring(sms_reader.getProtocol().getSmsData().getPayload().getPosSMS(),sms_reader.getProtocol().getSmsData().getPayload().getPosSMS() + sms_reader.getProtocol().getSmsData().getPayload().getLength()) );
        String PayloadData[]  = ConverterSms.SimpleConverterFromHexSubs(Payload.get(1),8);
        Payload.add (Arrays.toString(PayloadData));

        Batery_level.add (sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getName());
        Batery_level.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getLength()));
        //System.out.println(Batery_level.get(1)));
        Batery_level.add (String.valueOf(ConverterSms.SimpleConverterToAscii(Batery_level.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getOffset())));

        Alarm_Delay.add (sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getName());
        Alarm_Delay.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getLength()));
        Alarm_Delay.add (String.valueOf(ConverterSms.SimpleConverterToAscii(Alarm_Delay.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getOffset())));

        NotifyCycleH.add (sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getName());
        NotifyCycleH.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getLength()));
        NotifyCycleH.add (ConverterSms.SimpleConverterFromHex(NotifyCycleH.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getOffset()));

        Alarm_Buffer.add (sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getName());
        Alarm_Buffer.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getLength()));
        Alarm_Buffer.add (ConverterSms.SimpleConverterFromHexToBin(Alarm_Buffer.get(1),sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getOffset()));

        CountryCode.add (sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getName());
        CountryCode.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getPosSMS(), sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getLength()));
        CountryCode.add (CountryCode.get(1));

        TelNbr1.add (sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getName());
        TelNbr1.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getLength()));
        TelNbr1.add (TelNbr1.get(1));

        TelNbr2.add (sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getName());
        TelNbr2.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getLength()));
        TelNbr2.add (TelNbr2.get(1));

        Online_time.add (sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getName());
        Online_time.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getPosSMS()+ sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getLength()));
        Online_time.add (Online_time.get(1));

        WakeUp_frequency.add (sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getName());
        WakeUp_frequency.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getLength()));
        WakeUp_frequency.add (WakeUp_frequency.get(1));

        Send_time.add (sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getName());
        Send_time.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getLength()));
        Send_time.add (Send_time.get(1));

        CustomerID.add (sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getName());
        CustomerID.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getLength()));
        CustomerID.add (CustomerID.get(1));

        Max_SMS.add (sms_reader.getProtocol().getSmsData().getSignature().getMaxSMS().getName());
        Max_SMS.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getMaxSMS().getPosSMS(), sms_reader.getProtocol().getSmsData().getSignature().getMaxSMS().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getMaxSMS().getLength()));
        Max_SMS.add (Max_SMS.get(1));

        Gsm_Signal.add (sms_reader.getProtocol().getSmsData().getSignature().getGsmSignal().getName());
        Gsm_Signal.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getGsmSignal().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getGsmSignal().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getGsmSignal().getLength()));
        Gsm_Signal.add (Gsm_Signal.get(1));

        Checksum.add (sms_reader.getProtocol().getSmsData().getSignature().getChecksum().getName());
        Checksum.add (sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getChecksum().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getChecksum().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getChecksum().getLength()));
        Checksum.add (Checksum.get(1));

    }

    @Override
    public String toString() {
        return "SMS{" +
                "\n"+ANSI_RED+"SSID ==> " +ANSI_RESET + ANSI_BLUE + SSID.get(0) + ": " + ANSI_RESET + SSID.get(2) +
                "\n"+ANSI_RED+"sw_version ==> " +ANSI_RESET + ANSI_BLUE + sw_version.get(0) + ": " + ANSI_RESET + sw_version.get(2) +
                "\n"+ANSI_RED+"reason ==> " +ANSI_RESET + ANSI_BLUE + reason.get(0) + ": " + ANSI_RESET + reason.get(2) +
                "\n"+ANSI_RED+"cycle_time ==> " +ANSI_RESET + ANSI_BLUE + cycle_time.get(0) + ": " + ANSI_RESET + cycle_time.get(2) +
                "\n"+ANSI_RED+"calibration ==> " +ANSI_RESET + ANSI_BLUE + calibration.get(0) + ": " + ANSI_RESET + calibration.get(2) +
                "\n"+ANSI_RED+"Payload ==> " +ANSI_RESET + ANSI_BLUE + Payload.get(0) + ": " + ANSI_RESET + Payload.get(2) +
                "\n"+ANSI_RED+"Batery_Level ==> " +ANSI_RESET + ANSI_BLUE + Batery_level.get(0) + ": " + ANSI_RESET + Batery_level.get(2) +
                "\n"+ANSI_RED+"Alarm_Delay ==> " +ANSI_RESET + ANSI_BLUE + Alarm_Delay.get(0) + ": " + ANSI_RESET + Alarm_Delay.get(2) +
                "\n"+ANSI_RED+"NotifyCycleH ==> " +ANSI_RESET + ANSI_BLUE + NotifyCycleH.get(0) + ": " + ANSI_RESET + NotifyCycleH.get(2) +
                "\n"+ANSI_RED+"Alarm_Buffer ==> " +ANSI_RESET + ANSI_BLUE + Alarm_Buffer.get(0) + ": " + ANSI_RESET + Alarm_Buffer.get(2) +
                "\n"+ANSI_RED+"CountryCode ==> " +ANSI_RESET + ANSI_BLUE + CountryCode.get(0) + ": " + ANSI_RESET + CountryCode.get(2) +
                "\n"+ANSI_RED+"TelNbr1 ==> " +ANSI_RESET + ANSI_BLUE + TelNbr1.get(0) + ": " + ANSI_RESET + TelNbr1.get(2) +
                "\n"+ANSI_RED+"TelNbr2 ==> " +ANSI_RESET + ANSI_BLUE + TelNbr2.get(0) + ": " + ANSI_RESET + TelNbr2.get(2) +
                "\n"+ANSI_RED+"Online_time ==> " +ANSI_RESET + ANSI_BLUE + Online_time.get(0) + ": " + ANSI_RESET + Online_time.get(2) +
                "\n"+ANSI_RED+"WakeUp_frequency ==> " +ANSI_RESET + ANSI_BLUE + WakeUp_frequency.get(0) + ": " + ANSI_RESET + WakeUp_frequency.get(2) +
                "\n"+ANSI_RED+"Send_time ==> " +ANSI_RESET + ANSI_BLUE + Send_time.get(0) + ": " + ANSI_RESET + Send_time.get(2) +
                "\n"+ANSI_RED+"CustomerID ==> " +ANSI_RESET + ANSI_BLUE + CustomerID.get(0) + ": " + ANSI_RESET + CustomerID.get(2) +
                "\n"+ANSI_RED+"Max_SMS ==> " +ANSI_RESET + ANSI_BLUE + Max_SMS.get(0) + ": " + ANSI_RESET + Max_SMS.get(2) +
                "\n"+ANSI_RED+"Gsm_Signal ==> " +ANSI_RESET + ANSI_BLUE + Gsm_Signal.get(0) + ": " + ANSI_RESET + Gsm_Signal.get(2) +
                "\n"+ANSI_RED+"Checksum ==> " +ANSI_RESET + ANSI_BLUE + Checksum.get(0) + ": " + ANSI_RESET + Checksum.get(2) +



                '}';
    }
}
