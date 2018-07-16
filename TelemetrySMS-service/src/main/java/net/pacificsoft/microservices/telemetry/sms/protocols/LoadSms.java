package net.pacificsoft.microservices.telemetry.sms.protocols;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.pacificsoft.microservices.telemetry.sms.Yaml2jsonApplication;
import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadSms {

    public static void main(String[] args) throws IOException {

        //Estableciendo paths de archivos
        String PathRemote = "http://localhost:8081/config-server/default/master/telemetry_sms_service.yml";
        String PathYaml = "TelemetrySMS-service/src/main/resources/telemetry_sms_service.yml";
        String PathJson = "TelemetrySMS-service/src/main/resources/telemetry_sms_service.json";
        String PathSMS = "TelemetrySMS-service/src/main/resources/textosms.txt";

        //Obteniendo archivo yaml remoto y convirtiendolo a json
        Downloader.DownloadFile(PathRemote);
        Yaml2jsonApplication.convert2json(PathYaml);

        //Creando Clase que permite leer SMS
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(PathJson));
        ReadSMS sms_reader = gson.fromJson(reader, ReadSMS.class);

        //Obteniendo text del SMS
        String recievedSMS = new String(Files.readAllBytes(Paths.get(PathSMS)));
        SMS sms = new SMS(sms_reader,recievedSMS);
        System.out.println(sms);




    }
}
