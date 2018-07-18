package net.pacificsoft.microservices.telemetry.sms.protocols;

import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;

import java.util.Arrays;

public class SMS {
    String[] SSID = new String[3];
    String[] sw_version = new String[3];
    String[] reason = new String[3];
    String[] cycle_time = new String[3];
    String[] calibration = new String[3];
    String[] Payload = new String[3];
    String[] Batery_level = new String[3];
    String[] Alarm_Delay = new String[3];
    String[] NotifyCycleH = new String[3];
    String[] Alarm_Buffer = new String[3];
    String[] CountryCode = new String[3];
    String[] TelNbr1 = new String[3];
    String[] TelNbr2 = new String[3];
    String[] Online_time = new String[3];
    String[] WakeUp_frequency = new String[3];
    String[] Send_time = new String[3];
    String[] CustomerID = new String[3];
    String[] Max_SMS = new String[3];
    String[] Gsm_Signal = new String[3];
    String[] Checksum = new String[3];
    //    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;
//    Boolean Sabotage_Valve = false;

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
        SSID[0]= sms_reader.getProtocol().getSmsData().getHeader().getSSID().getName();
        SSID[1]= sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getSSID().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getSSID().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getSSID().getLength()) ;
        SSID[2]= String.valueOf(ConverterSms.HardConverter(SSID[1],sms_reader,sms_reader.getProtocol().getSmsData().getHeader().getSSID().getOffset()));

        sw_version[0] =  sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getName() ;
        sw_version[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getLength()) ;
        sw_version[2] = String.valueOf(ConverterSms.SimpleConverterToAscii(sw_version[1],sms_reader , sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getOffset()));

        reason[0] = sms_reader.getProtocol().getSmsData().getHeader().getReason().getName()   ;
        reason[1] = sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getReason().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getReason().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getReason().getLength());
        reason[2] = reason[1];

        cycle_time[0] = sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getName()  ;
        cycle_time[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getPosSMS(), sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getLength());
        cycle_time[2] = String.valueOf(ConverterSms.SimpleConverterToAscii(cycle_time[1],sms_reader,sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getOffset()));

        calibration[0] = sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getName()  ;
        calibration[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getLength()) ;
        //System.out.println(calibration[1]);
        calibration [2] = ConverterSms.SimpleConverterFromHex(calibration[1],sms_reader,sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getOffset());

        Payload[0] = sms_reader.getProtocol().getSmsData().getPayload().getName()  ;
        Payload[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getPayload().getPosSMS(),sms_reader.getProtocol().getSmsData().getPayload().getPosSMS() + sms_reader.getProtocol().getSmsData().getPayload().getLength()) ;
        String PayloadData[]  = ConverterSms.SimpleConverterFromHexSubs(Payload[1],8);
        Payload[2] = Arrays.toString(PayloadData);

        Batery_level[0] = sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getName();
        Batery_level[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getLength());
        //System.out.println(Batery_level[1]);
        Batery_level[2] = ConverterSms.SimpleConverterFromHex(Batery_level[1],sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getBateryLevel().getOffset());

        Alarm_Delay[0] = sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getName();
        Alarm_Delay[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getLength());
        Alarm_Delay[2] = ConverterSms.SimpleConverterFromHex(Alarm_Delay[1],sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getAlarmDelay().getOffset());

        NotifyCycleH[0] = sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getName();
        NotifyCycleH[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getLength());
        NotifyCycleH[2] = ConverterSms.SimpleConverterFromHex(NotifyCycleH[1],sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getNotifyCycleH().getOffset());

        Alarm_Buffer[0] = sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getName();
        Alarm_Buffer[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getLength());
        Alarm_Buffer[2] = ConverterSms.SimpleConverterFromHexToBin(Alarm_Buffer[1],sms_reader,sms_reader.getProtocol().getSmsData().getSignature().getAlarmBuffer().getOffset());

        CountryCode[0] = sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getName();
        CountryCode[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getPosSMS(), sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getCountryCode().getLength());
        CountryCode[2] = CountryCode[1];

        TelNbr1[0] = sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getName();
        TelNbr1[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getTelNbr1().getLength());
        TelNbr1[2] = TelNbr1[1];

        TelNbr2[0] = sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getName();
        TelNbr2[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getTelNbr2().getLength());
        TelNbr2[2] = TelNbr2[1];

        Online_time[0] = sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getName();
        Online_time[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getPosSMS()+ sms_reader.getProtocol().getSmsData().getSignature().getOnlineTime().getLength());
        Online_time[2] = Online_time[1];

        WakeUp_frequency[0] = sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getName();
        WakeUp_frequency[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getWakeUpFrequency().getLength());
        WakeUp_frequency[2] = WakeUp_frequency[1];

        Send_time[0] = sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getName();
        Send_time[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getSendTime().getLength());
        Send_time[2] = Send_time[1];

        CustomerID[0] = sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getName();
        CustomerID[1] = sms.substring(sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getPosSMS(),sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getPosSMS() + sms_reader.getProtocol().getSmsData().getSignature().getCustomerID().getLength());
        CustomerID[2] = CustomerID[1];




    }

    @Override
    public String toString() {
        return "SMS{" +
                "\n"+ANSI_RED+"SSID ==> " +ANSI_RESET + ANSI_BLUE + SSID[0] + ": " + ANSI_RESET + SSID[2] +
                "\n"+ANSI_RED+"sw_version ==> " +ANSI_RESET + ANSI_BLUE + sw_version[0] + ": " + ANSI_RESET + sw_version[2] +
                "\n"+ANSI_RED+"reason ==> " +ANSI_RESET + ANSI_BLUE + reason[0] + ": " + ANSI_RESET + reason[2] +
                "\n"+ANSI_RED+"cycle_time ==> " +ANSI_RESET + ANSI_BLUE + cycle_time[0] + ": " + ANSI_RESET + cycle_time[2] +
                "\n"+ANSI_RED+"calibration ==> " +ANSI_RESET + ANSI_BLUE + calibration[0] + ": " + ANSI_RESET + calibration[2] +
                "\n"+ANSI_RED+"Payload ==> " +ANSI_RESET + ANSI_BLUE + Payload[0] + ": " + ANSI_RESET + Payload[2] +
                "\n"+ANSI_RED+"Batery_Level ==> " +ANSI_RESET + ANSI_BLUE + Batery_level[0] + ": " + ANSI_RESET + Batery_level[2] +
                "\n"+ANSI_RED+"Alarm_Delay ==> " +ANSI_RESET + ANSI_BLUE + Alarm_Delay[0] + ": " + ANSI_RESET + Alarm_Delay[2] +
                "\n"+ANSI_RED+"NotifyCycleH ==> " +ANSI_RESET + ANSI_BLUE + NotifyCycleH[0] + ": " + ANSI_RESET + NotifyCycleH[2] +
                "\n"+ANSI_RED+"Alarm_Buffer ==> " +ANSI_RESET + ANSI_BLUE + Alarm_Buffer[0] + ": " + ANSI_RESET + Alarm_Buffer[2] +
                "\n"+ANSI_RED+"CountryCode ==> " +ANSI_RESET + ANSI_BLUE + CountryCode[0] + ": " + ANSI_RESET + CountryCode[2] +
                "\n"+ANSI_RED+"TelNbr1 ==> " +ANSI_RESET + ANSI_BLUE + TelNbr1[0] + ": " + ANSI_RESET + TelNbr1[2] +
                "\n"+ANSI_RED+"TelNbr2 ==> " +ANSI_RESET + ANSI_BLUE + TelNbr2[0] + ": " + ANSI_RESET + TelNbr2[2] +
                "\n"+ANSI_RED+"Online_time ==> " +ANSI_RESET + ANSI_BLUE + Online_time[0] + ": " + ANSI_RESET + Online_time[2] +
                "\n"+ANSI_RED+"WakeUp_frequency ==> " +ANSI_RESET + ANSI_BLUE + WakeUp_frequency[0] + ": " + ANSI_RESET + WakeUp_frequency[2] +
                "\n"+ANSI_RED+"Send_time ==> " +ANSI_RESET + ANSI_BLUE + Send_time[0] + ": " + ANSI_RESET + Send_time[2] +
                "\n"+ANSI_RED+"CustomerID ==> " +ANSI_RESET + ANSI_BLUE + CustomerID[0] + ": " + ANSI_RESET + CustomerID[2] +



                '}';
    }
}
