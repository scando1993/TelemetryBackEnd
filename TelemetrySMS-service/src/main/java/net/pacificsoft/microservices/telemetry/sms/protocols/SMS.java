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
    String[] Signature = new String[3];

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
        sw_version[2] = String.valueOf(ConverterSms.SimpleConverter(sw_version[1],sms_reader , sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getOffset()));

        reason[0] = sms_reader.getProtocol().getSmsData().getHeader().getReason().getName()   ;
        reason[1] = sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getReason().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getReason().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getReason().getLength());

        cycle_time[0] = sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getName()  ;
        cycle_time[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getPosSMS(), sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getCycleTime().getLength());

        calibration[0] = sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getName()  ;
        calibration[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getCalibration().getLength()) ;

        Payload[0] = sms_reader.getProtocol().getSmsData().getPayload().getName()  ;
        Payload[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getPayload().getPosSMS(),sms_reader.getProtocol().getSmsData().getPayload().getPosSMS() + sms_reader.getProtocol().getSmsData().getPayload().getLength()) ;

        Signature[0] = "Signature";
        Signature[1] = sms.substring(sms_reader.getProtocol().getSmsData().getPayload().getPosSMS() + sms_reader.getProtocol().getSmsData().getPayload().getLength()+1)  ;


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
                "\n"+ANSI_RED+"Signature ==> " +ANSI_RESET + ANSI_BLUE + Signature[0] + ": " + ANSI_RESET + Signature[2] +
                '}';
    }
}
