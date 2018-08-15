package net.pacificsoft.microservices.telemetry.sms.protocols;

import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class ConverterSms {

    public static int HardConverter(String data, ReadSMS model, int offset) {
        String comb = "";
        for (char x : data.toCharArray()) {

            comb += Long.toHexString((((int) x) - offset));
        }
        //Long hex = Long.parseLong(comb,10);
        //System.out.println(comb);
        Long hex = Long.parseLong(comb, 16);

        return hex.intValue();
    }

    public static int SimpleConverterToAscii(String data, ReadSMS model, int offset) {
        String value = null;
        if (data.length() == 1) {
            value = String.valueOf(((int) data.toCharArray()[0]) - offset);
        }
//        else{
//            for (char x : data.toCharArray()){
//
//                comb+=Long.toHexString((((int) x) - offset));
//            }
//
//        }

        return Integer.parseInt(value);
    }


    public static String SimpleConverterFromHex(String data, ReadSMS model, int offset) {
        String comb = String.valueOf(Integer.parseInt(data,16)-offset);
        return comb;

    }

    public static String SimpleConverterFromHexToBin(String data, ReadSMS model, int offset) {
        String comb = Integer.toBinaryString(Integer.parseInt(data,16)-offset);
        return comb;

    }

    public static Date EpochConverter(String data) {
        long epoch = Long.parseLong(data,16)*1000;
        Date fecha = new Date(epoch);
        return fecha;
    }

    public static String[] SimpleConverterFromHexSubs(String data, int Ndivisions) {
        String regex = ".";
        String []dataHex = data.split("(?<=\\G"+StringUtils.repeat(regex,Ndivisions) +")");
        String[] dataDec = new String[dataHex.length];
        for (int i = 0; i<=dataHex.length-1;i++) {
            dataDec[i] = String.valueOf(Integer.parseInt(dataHex[i],16));
        }
        return dataDec;

    }
}
