package net.pacificsoft.microservices.telemetry.sms.protocols;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.pacificsoft.microservices.telemetry.sms.Yaml2jsonApplication;
import net.pacificsoft.microservices.telemetry.sms.protocols.fields.ReadSMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadSms {

    public static SMS SmsProcessor() throws IOException {
        //Estableciendo paths de archivos
        String PathRemote = "http://localhost:8081/config-server/default/master/familia_8_5.yml";
        String PathYaml = "TelemetrySMS-service/src/main/resources/familia_8_5.yml";
        String PathJson = "TelemetrySMS-service/src/main/resources/familia_8_5.json";
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
        return sms;




    }

}
