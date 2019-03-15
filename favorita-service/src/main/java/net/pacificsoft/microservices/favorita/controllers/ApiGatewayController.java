
package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiGatewayController {


    @Autowired
    private RawSensorDataRepository rawDataRepository;

    @Autowired
    private DeviceRepository deviceRepository;


    @PostMapping("/rawData/{deviceid}")
    public ResponseEntity createCiudad(
            @PathVariable(value = "deviceid") Long deviceId,
            @Valid @RequestBody RawSensorData rawData) {
        try{
            Device device = deviceRepository.findById(deviceId).get();
            device.getRawSensorDatas().add(rawData);
            rawData.setDevice(device);


            final String uri = "http://104.209.196.204:9090/track";


            JSONObject json1 = new JSONObject();
            JSONObject s = new JSONObject();
            JSONObject wifi = new JSONObject();
            json1.put("t", new Integer(1551981257));
            json1.put("f", "favorita");
            json1.put("d", "4836966");
            wifi.put("4c:5e:8c:bc:86:0a", -56);
            wifi.put("4c:5e:8c:bc:86:0b", -60);
            s.put("wifi", wifi);
            json1.put("s", s);
            RestTemplate restTemplate = new RestTemplate();
            JSONObject jData = new JSONObject(restTemplate.postForObject( uri, json1.toString(), String.class));
            JSONObject asd = jData.getJSONObject("message").getJSONArray("guesses").getJSONObject(0);
            String location = (String) asd.get("location");
            String probability = (String) asd.get("probability");
            //JSONObject jGuesses = jMessage.getJSONObject("predictions");
            System.out.println("======================");
            System.out.println(jData.toString());
            System.out.println("----------------------");
            System.out.println(asd);
            System.out.println(asd.get("location"));
            //System.out.println(asd.get(0));
            System.out.println("NNNNNNNNNNNNNNNNNNNNNN");
            //Tracking t = device.getTrackings()



            RawSensorData rawSave = rawDataRepository.save(rawData);
            return new ResponseEntity(rawData,HttpStatus.CREATED);

        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Data", HttpStatus.NOT_FOUND);

        }
    }
}
