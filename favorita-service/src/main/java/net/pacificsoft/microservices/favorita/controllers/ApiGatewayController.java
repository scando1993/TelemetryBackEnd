
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
            final String urlPrediction = "http://localhost:2222/prediction";
            final String urlProbability = "http://localhost:2222/probability";
            final String urlLocationNames = "http://localhost:2222/locationNames";
            final String urlApiGoResponse = "http://localhost:2222/goApiResponse";
            final String urlMessage = "http://localhost:2222/message";
            final String urlMessaguess = "http://localhost:2222/messageGuess";


            final int defaultTrackingLocationGroup = 3;
            String endPoint;
            RestTemplate restTemplate = new RestTemplate();

            Device device = deviceRepository.findById(deviceId).get();

            device.getRawSensorDatas().add(rawData);
            rawData.setDevice(device);

            Set<Family> families = device.getGroup().getFamilies();
            JSONObject json1 = new JSONObject();
            JSONObject s = new JSONObject();
            JSONObject wifi = new JSONObject();
            json1.put("t", new Integer(1551981257));
            json1.put("f", "favorita");
            json1.put("d", "4836966");
            //json1.put("d",rawData.getEpoch());
            wifi.put("4c:5e:8c:bc:86:0a", -56);
            wifi.put("4c:5e:8c:bc:86:0b", -60);
            s.put("wifi", wifi);
            json1.put("s", s);
            JSONObject jData = new JSONObject();
            jData = new JSONObject(restTemplate.postForObject( uri, json1.toString(), String.class));
            /*
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
            */
            //obtening Data
            Boolean status = jData.getBoolean("success");
            JSONObject location_Names = jData.getJSONObject("message").getJSONObject("location_names");
            JSONArray guessess = jData.getJSONObject("message").getJSONArray("guesses");
            JSONArray predictions = jData.getJSONObject("message").getJSONArray("predictions");


            JSONObject temp = guessess.getJSONObject(0);
            String finalLocation = (String) temp.get("location");
            Double finalProbability = temp.getDouble("probability");

            //creating Tracting
            endPoint = "/" + device.getId() + "/" + defaultTrackingLocationGroup;
            JSONObject jsonTracking = createTrackingJson(rawData.getEpochDateTime(), finalLocation);
            JSONObject jsonTrackingResponse = new JSONObject(restTemplate.postForObject( urlTracking + endPoint, jsonTracking, Tracking.class));

            RawSensorData rawSave = rawDataRepository.save(rawData);


            //Creating MessageGuess
            MessageGuess messageGuess = new MessageGuess(finalLocation, finalProbability);
            JSONObject jsonResponseMessageGuess = new JSONObject(restTemplate.postForObject( urlMessaguess, messageGuess, MessageGuess.class));
            long idMessageGuess = (long)jsonResponseMessageGuess.get("id");

            //Creating Message
            endPoint = "/" + idMessageGuess;
            Message temp1 = new Message();
            JSONObject jsonResponseMessage = new JSONObject(restTemplate.postForObject( urlMessage + endPoint, temp1, Message.class));
            long idMessage = jsonResponseMessage.getLong("id");

            //Creating goApiResponse
            endPoint = "/" +idMessage + "/" + device.getId();
            GoApiResponse goApiResponse = new GoApiResponse(status);
            JSONObject jsonResponseGoApiResponse = new JSONObject(restTemplate.postForObject( urlApiGoResponse + endPoint, goApiResponse, GoApiResponse.class));
            long idGoApiResponse = jsonResponseGoApiResponse.getLong("id");

            //creating Predictions and Message
            jData.getJSONObject("message").getJSONObject("location_names");
            for(Object i: predictions){
                List<Object> locations = (((JSONObject)i).getJSONArray("locations")).toList();
                String predictionName = ((JSONObject)i).getString("name");
                List<Object> probabilites = (((JSONObject)i).getJSONArray("probabilities")).toList();

                for(int n = 0; n < locations.size(); n++){
                    String idName = (String) locations.get(n);
                    String nameIndexed = (String)location_Names.get(idName);
                    Double probabilityIndexed;
                    try{
                        probabilityIndexed = (Double)probabilites.get(n);
                    }
                    catch(Exception e){
                        String convertedToDouble = Integer.toString((Integer)probabilites.get(n));
                        convertedToDouble = convertedToDouble + ".0";
                        probabilityIndexed = Double.parseDouble(convertedToDouble);
                    }



                    Probabilities probability = new Probabilities(Double.parseDouble(idName), probabilityIndexed);
                    LocationNames locationNames = new LocationNames(Double.parseDouble(idName),nameIndexed);
                    Prediction prediction = new Prediction(predictionName);

                    //posting prediction
                    endPoint = "/" + idMessage;
                    JSONObject jsonResponsePrediction = new JSONObject(restTemplate.postForObject( urlPrediction + endPoint, prediction, Prediction.class));
                    long idPrediction = jsonResponsePrediction.getLong("id");

                    //posting probability and locationNames
                    endPoint = "/" + idPrediction;
                    JSONObject jsonResponseProbability = new JSONObject(restTemplate.postForObject( urlProbability + endPoint, probability, Probabilities.class));
                    JSONObject jsonResponseLocationNames = new JSONObject(restTemplate.postForObject( urlLocationNames, locationNames, LocationNames.class));
                }
            }




            //final return
            //return new ResponseEntity(rawData,HttpStatus.CREATED);

            return new ResponseEntity(goApiResponse,HttpStatus.CREATED);
        }
        catch(Exception e){
            String a = e + "\n" + e.getCause() + "\n";

            return new ResponseEntity<String>("It's not possible create new Data, the reason: \n" +
                    a,
                    HttpStatus.NOT_FOUND);

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
        json.put("location", location);
        json.put("dtm", dtmFormated);
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
