package net.pacificsoft.microservices.telemetry.sms.protocols;

import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;

import java.util.ArrayList;

public class ConverterSms {

    public static int HardConverter (String data, ReadSMS model, int offset){
        String comb = "";
        for (char x : data.toCharArray()){

            comb+=Long.toHexString((((int) x) - offset));
        }
        //Long hex = Long.parseLong(comb,10);
        //System.out.println(comb);
        Long hex = Long.parseLong(comb,16);

        return hex.intValue();
    }

    public static int SimpleConverter (String data, ReadSMS model, int offset){
        String value =null;
        if (data.length() == 1 ){
            value = String.valueOf(((int) data.toCharArray()[0]) - offset);
            System.out.println(data.toCharArray()[0]);
            System.out.println((int) data.toCharArray()[0]);

        }



        return Integer.parseInt(value);


    }


}
