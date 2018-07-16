package net.pacificsoft.microservices.telemetry.sms.protocols;

import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;

import java.util.Arrays;

public class SMS {
    String[] SSID = new String[2];
    String[] sw_version = new String[2];
    String[] reason = new String[2];
    String[] cycle_time = new String[2];
    String[] calibration = new String[2];
    String[] Payload = new String[2];
    String[] Signature = new String[2];

    public SMS(ReadSMS sms_reader , String sms) {
        SSID[0]= sms_reader.getProtocol().getSmsData().getHeader().getSSID().getName();
        SSID[1]= sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getSSID().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getSSID().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getSSID().getLength()) ;
        sw_version[0] =  sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getName() ;
        sw_version[1] =  sms.substring(sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getPosSMS(),sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getPosSMS() + sms_reader.getProtocol().getSmsData().getHeader().getSwVersion().getLength()) ;
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
                "SSID=" + Arrays.toString(SSID) +
                ", sw_version=" + Arrays.toString(sw_version) +
                ", reason=" + Arrays.toString(reason) +
                ", cycle_time=" + Arrays.toString(cycle_time) +
                ", calibration=" + Arrays.toString(calibration) +
                ", Payload=" + Arrays.toString(Payload) +
                ", Signature=" + Arrays.toString(Signature) +
                '}';
    }
}
