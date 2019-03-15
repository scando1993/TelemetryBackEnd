
package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.repository.*;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
            final String uri = "http://104.209.196.204:9090/track";
            final String urlTracking = "http://localhost:2222/tracking";
            final String urlApiGoResponse = "http://localhost:2222/goApiResponse";
            final int defaultTrackingLocationGroup = 1;
            RestTemplate restTemplate = new RestTemplate();

            Device device = deviceRepository.findById(deviceId).get();
            device.getRawSensorDatas().add(rawData);
            rawData.setDevice(device);

            Set<Family> families = device.getGroup().getFamilies();
            JSONObject json1 = new JSONObject();
            JSONObject s = new JSONObject();
            JSONObject wifi = new JSONObject();
            json1.put("t", new Integer(1551981257));
            //json1.put("f", "favorita");
            //json1.put("d", "4836966");
            json1.put("d",rawData.getEpoch());
            wifi.put("4c:5e:8c:bc:86:0a", -56);
            wifi.put("4c:5e:8c:bc:86:0b", -60);
            s.put("wifi", wifi);
            json1.put("s", s);
            JSONObject jData = new JSONObject();
            for(Family family:families){
                json1.put("f",family.getName());
                jData = new JSONObject(restTemplate.postForObject( uri, json1.toString(), String.class));
                Boolean empty = jData.getJSONObject("message").getJSONObject("location_names").isEmpty();
                if(empty){
                    json1.remove("f");
                }
                else{
                    break;
                }
            }

            //obtening Data
            JSONObject location_Names = jData.getJSONObject("message").getJSONObject("location_names");
            JSONArray guessess = jData.getJSONObject("message").getJSONArray("guesses");
            JSONArray predictions = jData.getJSONObject("message").getJSONArray("predictions");


            JSONObject temp = guessess.getJSONObject(0);
            String FinalLocation = (String) temp.get("location");
            String FinalProbability = (String) temp.get("probability");

            //creating Tracting
            String endPoint = "/" + device.getId() + "/" + defaultTrackingLocationGroup;
            JSONObject jsonTracking = createTrackingJson(rawData.getEpochDateTime(), FinalLocation);
            JSONObject jsonTrackingResponse = new JSONObject(restTemplate.postForObject( urlTracking + endPoint, jsonTracking.toString(), String.class));

            //creating LocationNames
            jData.getJSONObject("message").getJSONObject("location_names");
            for(Object i: predictions){
                List<Object> locations = (((JSONObject)i).getJSONArray("locations")).toList();
                String PredictionName = ((JSONObject)i).getString("name");
                List<Object> probabilites = (((JSONObject)i).getJSONArray("probabilites")).toList();

            }
            //LocationNames locationNames = new LocationNames();
            //JSONObject jsonTrackingResponse = new JSONObject(restTemplate.postForObject( urlTracking + endPoint, jsonTracking.toString(), String.class));

            //creating ApiGoResponse
            RawSensorData rawSave = rawDataRepository.save(rawData);

            //final return
            return new ResponseEntity(rawData,HttpStatus.CREATED);

        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Data", HttpStatus.NOT_FOUND);

        }
    }
    @PostMapping("/CreateDeviceFamily")
    public ResponseEntity s(
            @Valid @RequestBody String rawData) {
        try{
            final String urlDevice = "http://localhost:2222/device";
            RestTemplate restTemplate = new RestTemplate();

            JSONObject jsonRequest = new JSONObject(rawData);
            int groupID = (int)jsonRequest.remove("groupId");

            String endPoint = "/" + groupID;
            JSONObject jsonResponse = new JSONObject(restTemplate.postForObject( urlDevice + endPoint, jsonRequest.toString(), String.class));
            return new ResponseEntity(jsonResponse,HttpStatus.CREATED);

        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Data", HttpStatus.NOT_FOUND);

        }
    }


    private JSONObject createTrackingJson (Date dtm, String location){
        SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String dtmFormated = as.format(dtm);

        JSONObject json = new JSONObject();
        json.put("dtm", dtmFormated);
        json.put("location", location);
        return json;
    };

    private JSONObject createApiGoResponseJson (String success){
        JSONObject json = new JSONObject();
        json.put("success", success);
        return json;
    };

    private JSONObject createLocationNamesJson(long idName, String name){
        JSONObject json = new JSONObject();
        json.put("idName", idName);

        return json;
    };
}
