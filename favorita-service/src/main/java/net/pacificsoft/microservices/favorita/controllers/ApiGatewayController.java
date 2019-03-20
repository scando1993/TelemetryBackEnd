
package net.pacificsoft.microservices.favorita.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.repository.*;
import org.json.JSONArray;
import org.slf4j.Marker;
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
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiGatewayController {


    @Autowired
    private RawSensorDataRepository rawDataRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ProbabilitiesRepository probabilitiesRepository;
    @Autowired
    private PredictionsRepository predictionsRepository;
    @Autowired
    private LocationNamesRepository locationNamesRepository;
    @Autowired
    private MessageRepository messageRepository;


    final String uri = "http://104.209.196.204:9090/track";
    //final String uri = "http://172.16.2.130:8005/track";
    final String urlTracking = "http://localhost:2222/tracking";
    final String urlRawSensorData = "http://localhost:2222/rawSensorData";
    final String urlPrediction = "http://localhost:2222/prediction";
    final String urlProbability = "http://localhost:2222/probability";
    final String urlLocationNames = "http://localhost:2222/locationNames";
    final String urlApiGoResponse = "http://localhost:2222/goApiResponse";
    final String urlMessage = "http://localhost:2222/message";
    final String urlMessaguess = "http://localhost:2222/messageGuess";
    final String urlTelemetry = "http://localhost:2222/telemetria";
    final String urlWifiSensor = "http://localhost:2222/wifiScan";
    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);


    @PostMapping("/rawData/{deviceid}")
    public ResponseEntity createCiudad(
            @PathVariable(value = "deviceid") Long deviceId,
            @Valid @RequestBody RawSensorData rawData) {
        try{

            logger.info("Ya en el post");
            final int defaultTrackingLocationGroup = 3;
            String endPoint;
            RestTemplate restTemplate = new RestTemplate();

            /*
            if(!deviceRepository.existsById(rawData.getEpoch())){
                return new ResponseEntity("No existe dispositivo asociado",HttpStatus.NOT_FOUND);
            }
            */

            Device device = deviceRepository.findById(deviceId).get();

            device.getRawSensorDatas().add(rawData);
            rawData.setDevice(device);
            RawSensorData rawSave = rawDataRepository.save(rawData);
            deviceRepository.save(device);

            /*
            RawSensorData rawSensorData = new RawSensorData(111,11,new Date(123213),"something");
            JSONObject jRawSensorData = rawSensorData.toJSON();
            endPoint = "/" + deviceId;
            RawSensorData rawDatas = (restTemplate.postForObject( urlRawSensorData + endPoint, jRawSensorData.toMap(), RawSensorData.class));
            long a = rawDatas.getId();
            */

            Set<Family> families = device.getGroup().getFamilies();
            JSONObject json1 = new JSONObject();
            JSONObject s = new JSONObject();
            JSONObject wifi = new JSONObject();
            json1.put("t", new Integer(1551981257));
            //json1.put("f", "favorita");
            json1.put("d", "4836966");
            //json1.put("d",rawData.getEpoch());
            wifi.put("4c:5e:8c:bc:86:0a", -56);
            wifi.put("4c:5e:8c:bc:86:0b", -60);
            s.put("wifi", wifi);
            json1.put("s", s);
            JSONObject jData = new JSONObject();
            jData = new JSONObject(restTemplate.postForObject( uri, json1.toString(), String.class));

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
            Boolean status = jData.getBoolean("success");
            JSONObject location_Names = jData.getJSONObject("message").getJSONObject("location_names");
            JSONArray guessess = jData.getJSONObject("message").getJSONArray("guesses");
            JSONArray predictions = jData.getJSONObject("message").getJSONArray("predictions");


            JSONObject temp = guessess.getJSONObject(0);
            String finalLocation = (String) temp.get("location");
            Double finalProbability = temp.getDouble("probability");


            //creatinig Telemetry
            //if(rawData.getTemperature() != null){
                endPoint = "/" +deviceId;
                JSONObject jsonTelemtry = createTelemetryJson(rawData.getEpochDateTime(),"temperature",rawData.getTemperature());
                JSONObject jsonTelemtryResponse = new JSONObject(restTemplate.postForObject( urlTelemetry + endPoint, jsonTelemtry.toMap(), Telemetria.class));
            //}


            //creating Tracting
            endPoint = "/" + device.getId() + "/" + defaultTrackingLocationGroup;
            JSONObject jsonTracking = createTrackingJson(rawData.getEpochDateTime(), finalLocation);
            JSONObject jsonTrackingResponse = new JSONObject(restTemplate.postForObject( urlTracking + endPoint, jsonTracking.toMap(), Tracking.class));


            //Creating MessageGuess
            MessageGuess messageGuess = new MessageGuess(finalLocation, finalProbability);
            JSONObject jsonResponseMessageGuess = new JSONObject(restTemplate.postForObject( urlMessaguess, messageGuess, MessageGuess.class));
            long idMessageGuess = (long)jsonResponseMessageGuess.get("id");

            //Creating Message
            endPoint = "/" + idMessageGuess;
            Message temp1 = new Message();
            Message jsonResponseMessage = (Message)(restTemplate.postForObject( urlMessage + endPoint, temp1, Message.class));
            //long idMessage = jsonResponseMessage.getLong("id");
            long idMessage = jsonResponseMessage.getId();

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

                Prediction prediction = new Prediction(predictionName);
                List<LocationNames> listLocationNames = new ArrayList<>();
                List<Probabilities> listProbabilities = new ArrayList<>();

                //posting prediction
                endPoint = "/" + idMessage;
                JSONObject jsonResponsePrediction = new JSONObject(restTemplate.postForObject( urlPrediction + endPoint, prediction, Prediction.class));
                long idPrediction = jsonResponsePrediction.getLong("id");
               /*
                prediction.setMessage(jsonResponseMessage);
                jsonResponseMessage.getPredictions().add(prediction);
                prediction = predictionsRepository.save(prediction);
                messageRepository.save(jsonResponseMessage);
                long idPrediction = prediction.getId();
                */
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

                    //posting probability and locationNames
                    endPoint = "/" + idPrediction;
                    //JSONObject jsonResponseProbability = new JSONObject(restTemplate.postForObject( urlProbability + endPoint, probability, Probabilities.class));
                    //JSONObject jsonResponseLocationNames = new JSONObject(restTemplate.postForObject( urlLocationNames, locationNames, LocationNames.class));
                    prediction.getProbabilitieses().add(probability);
                    probability.setPrediction(prediction);

                    prediction.getLocationNames().add(locationNames);
                    locationNames.setPrediction(prediction);

                    listProbabilities.add(probability);
                    listLocationNames.add(locationNames);

                }
                predictionsRepository.save(prediction);
                probabilitiesRepository.saveAll(listProbabilities);
                locationNamesRepository.saveAll(listLocationNames);
            }
            System.gc();

            return new ResponseEntity(goApiResponse,HttpStatus.CREATED);
        }
        catch(Exception e){
            String a = e + "\n" + e.getCause() + "\n";
            logger.error("Error faltal");
            return new ResponseEntity<String>("It's not possible create new Data, the reason: \n" +
                    a,
                    HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/track")
    public ResponseEntity Storage(
            @Valid @RequestBody String dataBody) {
        try{



            final int defaultTrackingLocationGroup = 3;
            String endPoint;
            RestTemplate restTemplate = new RestTemplate();

            /*
            if(!deviceRepository.existsById(rawData.getEpoch())){
                return new ResponseEntity("No existe dispositivo asociado",HttpStatus.NOT_FOUND);
            }
            */
            dataBody = "[" + dataBody + "]";
            JSONArray j = new JSONArray(dataBody);

            JSONObject jDataBody = j.getJSONObject(0);
            JSONArray wifis = (JSONArray)jDataBody.remove("wifi");
            JSONObject wifiList = getWifiMACs(wifis);

            int batteryLevel = (int)jDataBody.remove("battery");
            String deviceName = (String)jDataBody.remove("device");
            String familyDevice = (String)jDataBody.remove("family");
            String dtmS = (String) jDataBody.remove("EpochDateTime");
            if (dtmS.length() > 16){
                dtmS = dtmS.substring(0,16);
            }
            jDataBody.put("epochDateTime", dtmS);
            SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            Date epochDateTime = as.parse(dtmS);
            double temperature = jDataBody.getDouble("temperature");

            //------log
            logger.info("All data have been parsed correctly");
            //-------

            //getting Device
            Device device = new Device();
            if(deviceRepository.existsByName(deviceName)){
                logger.info("Device found");
                device= (deviceRepository.findByName(deviceName)).get(0);

            }
            else {
                logger.warn("Device no found in database, setting relations to device = 'unknown'");
                device = (deviceRepository.findByName("unknown")).get(0);
            }
            Set<Family> families = device.getGroup().getFamilies();
            if(families.size() == 0){
                logger.error("Device is not associated with any family");
            }
            long deviceId = device.getId();



            //creating request to send Go server
            JSONObject json1 = new JSONObject();
            JSONObject s = new JSONObject();
            json1.put("t", jDataBody.getInt("epoch"));
            json1.put("d", deviceName);
            s.put("wifi", wifiList);
            json1.put("s", s);
            JSONObject jData = new JSONObject();
            jData = new JSONObject(restTemplate.postForObject( uri, json1.toString(), String.class));

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
            if(jData.getJSONObject("message").getJSONObject("location_names").isEmpty()){
                logger.error("Have not found a succesful data response from Go Server, maybe invalid family");
            }

            //obtening Data
            Boolean status = jData.getBoolean("success");
            JSONObject location_Names = jData.getJSONObject("message").getJSONObject("location_names");
            JSONArray guessess = jData.getJSONObject("message").getJSONArray("guesses");
            JSONArray predictions = jData.getJSONObject("message").getJSONArray("predictions");

            JSONObject temp = guessess.getJSONObject(0);
            String finalLocation = (String) temp.get("location");
            Double finalProbability = temp.getDouble("probability");

            logger.info("Go response successfully parse");

            //creating rawsSensorData
            JSONObject jRawSensorData = jDataBody;
            endPoint = "/" + deviceId;
            //JSONObject rawData = new JSONObject(restTemplate.postForObject( urlRawSensorData + endPoint, jRawSensorData.toMap(), RawSensorData.class));
            RawSensorData rawData = (restTemplate.postForObject( urlRawSensorData + endPoint, jRawSensorData.toMap(), RawSensorData.class));
            //RawSensorData rawData = (RawSensorData)(restTemplate.postForObject( urlRawSensorData + endPoint, jRawSensorData.toMap(), RawSensorData.class));
            logger.info("Raw data have been created");

            //creating wifiSensor
            endPoint = "/" + rawData.getId();
            //endPoint = "/" + rawData.getLong("id");
            postWifiScan(wifiList,urlWifiSensor + endPoint,restTemplate);
            logger.info("WifiSensor have been created");

            //creatinig Telemetry
            endPoint = "/" +deviceId;
            JSONObject jsonTelemtry = createTelemetryJson(epochDateTime,"temperature",temperature);
            JSONObject jsonTelemtryResponse = new JSONObject(restTemplate.postForObject( urlTelemetry + endPoint, jsonTelemtry.toMap(), Telemetria.class));
            logger.info("Telemetry have been creaed");


            //creating Tracting
            endPoint = "/" + device.getId() + "/" + defaultTrackingLocationGroup;
            JSONObject jsonTracking = createTrackingJson(epochDateTime, finalLocation);
            JSONObject jsonTrackingResponse = new JSONObject(restTemplate.postForObject( urlTracking + endPoint, jsonTracking.toMap(), Tracking.class));
            logger.info("Tracking have been creaed");

            //Creating MessageGuess
            MessageGuess messageGuess = new MessageGuess(finalLocation, finalProbability);
            JSONObject jsonResponseMessageGuess = new JSONObject(restTemplate.postForObject( urlMessaguess, messageGuess, MessageGuess.class));
            long idMessageGuess = (long)jsonResponseMessageGuess.get("id");
            logger.info("MessageGuess have been creaed");

            //Creating Message
            endPoint = "/" + idMessageGuess;
            Message temp1 = new Message();
            Message jsonResponseMessage = (Message)(restTemplate.postForObject( urlMessage + endPoint, temp1, Message.class));
            //long idMessage = jsonResponseMessage.getLong("id");
            long idMessage = jsonResponseMessage.getId();
            logger.info("Message have been creaed");

            //Creating goApiResponse
            endPoint = "/" +idMessage + "/" + device.getId();
            GoApiResponse goApiResponse = new GoApiResponse(status);
            JSONObject jsonResponseGoApiResponse = new JSONObject(restTemplate.postForObject( urlApiGoResponse + endPoint, goApiResponse, GoApiResponse.class));
            long idGoApiResponse = jsonResponseGoApiResponse.getLong("id");
            logger.info("goApiResponse have been creaed");

            //creating Predictions and Message
            jData.getJSONObject("message").getJSONObject("location_names");
            for(Object i: predictions){
                List<Object> locations = (((JSONObject)i).getJSONArray("locations")).toList();
                String predictionName = ((JSONObject)i).getString("name");
                List<Object> probabilites = (((JSONObject)i).getJSONArray("probabilities")).toList();

                Prediction prediction = new Prediction(predictionName);
                List<LocationNames> listLocationNames = new ArrayList<>();
                List<Probabilities> listProbabilities = new ArrayList<>();

                //posting prediction
                endPoint = "/" + idMessage;
                JSONObject jsonResponsePrediction = new JSONObject(restTemplate.postForObject( urlPrediction + endPoint, prediction, Prediction.class));
                long idPrediction = jsonResponsePrediction.getLong("id");
               /*
                prediction.setMessage(jsonResponseMessage);
                jsonResponseMessage.getPredictions().add(prediction);
                prediction = predictionsRepository.save(prediction);
                messageRepository.save(jsonResponseMessage);
                long idPrediction = prediction.getId();
                */
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

                    //posting probability and locationNames
                    endPoint = "/" + idPrediction;
                    //JSONObject jsonResponseProbability = new JSONObject(restTemplate.postForObject( urlProbability + endPoint, probability, Probabilities.class));
                    //JSONObject jsonResponseLocationNames = new JSONObject(restTemplate.postForObject( urlLocationNames, locationNames, LocationNames.class));
                    prediction.getProbabilitieses().add(probability);
                    probability.setPrediction(prediction);

                    prediction.getLocationNames().add(locationNames);
                    locationNames.setPrediction(prediction);

                    listProbabilities.add(probability);
                    listLocationNames.add(locationNames);

                }
                predictionsRepository.save(prediction);
                probabilitiesRepository.saveAll(listProbabilities);
                locationNamesRepository.saveAll(listLocationNames);
            }
            logger.info("All predictions,, locationNames and probabilities have been creaed");
            System.gc();

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

    private JSONObject createTelemetryJson(Date dtm, String name, double value){
        SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String dtmFormated = as.format(dtm);

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("dtm", dtmFormated);
        json.put("value", value);
        return json;
    };
    private JSONObject createRawSensorDataJson (JSONObject json){
        SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Integer dtmS = (Integer) json.remove("EpochDateTime");
        Date dtm = new Date(dtmS);
        String dtmFormated = as.format(dtm);
        json.put("epochDateTime", dtmFormated);
        return json;
    }

    private void postWifiScan (JSONObject wifiList, String url, RestTemplate restTemplate) throws Exception{
        Iterator<String> iterator = wifiList.keys();
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            int rssi = wifiList.getInt(key);
            WifiScan wifiScan = new WifiScan(rssi,key);
            WifiScan wifiScan1  = restTemplate.postForObject(url, wifiScan, WifiScan.class);
        }
    }
    private JSONObject getWifiMACs(JSONArray wifi){
        Iterator<Object> iterator = wifi.iterator();
        JSONObject json = new JSONObject();
        while (iterator.hasNext()){
            JSONObject temp = (JSONObject)iterator.next();
            String mac = (temp).getString("MAC");
            int rssi = (temp).getInt("rssi");
            json.put(mac, rssi);
        }
        return json;
    }

}
