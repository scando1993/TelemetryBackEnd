
package net.pacificsoft.microservices.favorita.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gson.Gson;
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
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private TelemetriaRepository telemetriaRepository;
    @Autowired
    private AlertaRepository alertaRepository;
    @Autowired
    private MessageGuessRepository messageGuessRepository;
    @Autowired
    private GoApiResponseRepository goApiResponseRepository;
    @Autowired
    private WifiScanRepository wifiScanRepository;
    @Autowired
    private LocationGroupRepository locationGroupRepository;
    @Autowired
    private MacRepository macRepository;

    final String uri = "http://104.209.196.204:9090/track";
    //final String uri = "http://172.16.10.41:8005/track";
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
    final String urlAlert = "http://localhost:2222/alerta";
    final String urlFamily = "http://localhost:2222/family";
    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);
    private RestTemplate restTemplate = new RestTemplate();
    private SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    final long defaultTrackingLocationGroup = 1L;

    private String endPoint;

    final String formatApiInnerDate = "{\"device\": String,\n" +
            "        \"startDate\": \"yyyy-MM-ddTHH:MM\" or \"beginning\",\n" +
            "        \"endDate\": \"yyyy-MM-ddTHH:MM\" or \"now\"}";
    final String formatApiRequestJson =  "{\"rawData\":\"String\",\"EpochDateTime\":\"String\",\"epoch\":UnixDate/long,\n +" +
                                        "\"family\":\"String\",\"device\":\"String\",\"wifi\":[{\"MAC\":\"String\",\"rssi\":int},...}],\n +" +
                                      "\"temperature\":double,\"battery\":int}";

    @PostMapping("/track")
    public ResponseEntity Storage(
            @Valid @RequestBody String dataBody) {
        //Gson gson = new Gson();   
        //dataBody = "[" + dataBody + "]";
        //EverHubEvent everHubEvent = gson.fromJson(dataBody,EverHubEvent.class);
        try{
            boolean validDevice = true;

            JSONObject jDataBody;
            String rawData;
            Long epoch;
            JSONArray wifis;
            String deviceName;
            String familyDevice;
            Date epochDateTime;
            double temperature;
            int batteryLevel;
            try {
                jDataBody = new JSONObject(dataBody);
                rawData = jDataBody.getString("rawData");
                epoch = jDataBody.getLong("epoch");
                wifis = (JSONArray) jDataBody.remove("wifi");
                deviceName = (String) jDataBody.remove("device");
                familyDevice = (String) jDataBody.remove("family");
                String dtmS = (String) jDataBody.remove("EpochDateTime");
                epochDateTime = as.parse(dtmS);
                temperature = jDataBody.getDouble("temperature");
                batteryLevel = (int) jDataBody.remove("battery");
            }
            catch (Exception e){
                return new ResponseEntity("Expected JSON:\n" + formatApiRequestJson, HttpStatus.BAD_REQUEST);
            }
            //------log
            logger.info("All data have been parsed correctly");
            //-------

            //----------getting Device
            Device device;
            if(deviceRepository.existsByName(deviceName)){
                logger.info("Device found");
                device= (deviceRepository.findByName(deviceName)).get(0);
            }
            else {
                device = (deviceRepository.findByName("unknown")).get(0);
                logger.warn("Device no found in database, setting relations to device = 'unknown'");
                //creating alert
                Alerta alerta = new Alerta("Device error","Device: " + deviceName + " not found, continuing ith Device: unknown");
                postAlert(alerta, device);
                //validDevice = false;
            }
            //-----------creating rawsSensorData
            //RawSensorData rawSensorData = (RawSensorData)jDataBody.toMap();
            RawSensorData rawSensorData = new RawSensorData(epoch,temperature,epochDateTime,rawData);
            postRawSensorDara(rawSensorData,device);
            logger.info("Raw data have been storaged");

            //-------Obtainning MACS
            JSONObject wifiList = getWifiMACs(wifis);

            //-----------creating wifiSensor
            postWifiScans(wifiList,rawSensorData);
            logger.info("WifiSensor have been storaged");

            if(!validDevice){
                logger.error("Device not found returning 404");
                Alerta alert = new Alerta("Device error","Device: " + deviceName + " not found, saving data with deviceName: unknown");
                return new ResponseEntity(alert.toJson().toMap(), HttpStatus.NOT_FOUND);
            }

            //-----creatinig Telemetry
            Telemetria telemetry = new Telemetria(epochDateTime,"temperature",temperature);
            postTelemtry(telemetry,device);
            logger.info("Telemetry have been storaged");

            //-------getting Families
            String family = findFamilyMac(wifiList);
            family = "favorita";
            if(family.compareTo("") == 0){
                logger.error("Any MAC is associated with any family");
                Alerta alert = new Alerta("MAC error","MACs do not have any family");
                postAlert(alert,device);
                return new ResponseEntity(alert.toJson().toMap(),HttpStatus.PARTIAL_CONTENT);
            }
            /*
            Set<Family> families = device.getGroup().getFamilies();
            if(families.size() == 0){
                logger.error("Device is not associated with any family");
                Alerta alert = new Alerta("Device error","Device does not have any family");
                postAlert(alert,device);
                return new ResponseEntity(alert.toJson().toMap(),HttpStatus.PRECONDITION_FAILED);
            }
            */

            //-------creating request to send Go server
            JSONObject jsonRequesGoServer = new JSONObject();
            //----s
            JSONObject s = new JSONObject();
            jsonRequesGoServer.put("t", epoch);
            jsonRequesGoServer.put("d", deviceName);
            s.put("wifi", wifiList);
            jsonRequesGoServer.put("s", s);
            //----f
            jsonRequesGoServer.put("f", family);
            //----ready to be send
            logger.info("Data prepared to send");
            logger.info("sending data to: " + uri);
            logger.info("" + jsonRequesGoServer.toString());
            JSONObject jData;
            jData = new JSONObject(restTemplate.postForObject( uri, jsonRequesGoServer.toString(), String.class));
            /*
            for(Family family:families){
                jsonRequesGoServer.put("f",family.getName());
                jData = new JSONObject(restTemplate.postForObject( uri, jsonRequesGoServer.toString(), String.class));
                Boolean empty = jData.getJSONObject("message").getJSONObject("location_names").isEmpty();
                logger.info("" + empty);
                if(empty){
                    jsonRequesGoServer.remove("f");
                }
                else{
                    break;
                }
            }
            */

            logger.info("Successfull Responce");
            if(jData.getJSONObject("message").getJSONObject("location_names").isEmpty()){
                logger.error("Response is empty. Could not obtain a valid prediction, maybe invalid family");
                Alerta alert = new Alerta("Go Server error","Response is empty. Could not obtain a valid prediction, maybe invalid family");
                postAlert(alert,device);
                return new ResponseEntity(alert.toJson().toMap(),HttpStatus.PRECONDITION_FAILED);
            }

            //---------obtening Data
            Boolean status = jData.getBoolean("success");
            JSONObject location_Names = jData.getJSONObject("message").getJSONObject("location_names");
            JSONArray guessess = jData.getJSONObject("message").getJSONArray("guesses");
            JSONArray predictions = jData.getJSONObject("message").getJSONArray("predictions");

            JSONObject temp = guessess.getJSONObject(0);
            String finalLocation = (String) temp.get("location");
            Double finalProbability = temp.getDouble("probability");

            logger.info("Go response successfully parse");

            //---------creating Tracting
            Tracking tracking = new Tracking(finalLocation, epochDateTime);
            postTracking(tracking,device,defaultTrackingLocationGroup);
            logger.info("Tracking have been storaged");

            //-------Creating MessageGuess
            MessageGuess messageGuess = new MessageGuess(finalLocation, finalProbability);
            postMessageGuess(messageGuess);
            logger.info("MessageGuess have been storaged");

            //---------Creating Message
            Message message = new Message();
            postMessage(message, messageGuess);
            logger.info("Message have been storaged");

            //--------Creating goApiResponse
            GoApiResponse goApiResponse = new GoApiResponse(status);
            postGoApiResponse(goApiResponse,message,device);
            logger.info("goApiResponse have been storaged");

            //------------creating Predictions, LocationNames and Probabilities
            jData.getJSONObject("message").getJSONObject("location_names");
            for(Object i: predictions){
                List<Object> locations = (((JSONObject)i).getJSONArray("locations")).toList();
                String predictionName = ((JSONObject)i).getString("name");
                List<Object> probabilites = (((JSONObject)i).getJSONArray("probabilities")).toList();

                Prediction prediction = new Prediction(predictionName);
                List<LocationNames> listLocationNames = new ArrayList<>();
                List<Probabilities> listProbabilities = new ArrayList<>();

                //------------posting prediction

                prediction.setMessage(message);
                message.getPredictions().add(prediction);
                //prediction = predictionsRepository.save(prediction);
                //messageRepository.save(message);


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
                    //endPoint = "/" + idPrediction;
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
                messageRepository.save(message);
                probabilitiesRepository.saveAll(listProbabilities);
                locationNamesRepository.saveAll(listLocationNames);

            }
            logger.info("All predictions, locationNames and probabilities have been storaged");
            logger.info("All data have been Successfully storaged");
            System.gc();

            return new ResponseEntity(goApiResponse,HttpStatus.CREATED);
        }
        catch(Exception e){
            String a = e + "\n" + e.getCause() + "\n";

            return new ResponseEntity<String>("It's not possible create new Data, the reason: \n" +
                    a,
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    /*
    Structure of getTrackingInnerDate json
    {
        "device": String,
        "startDate": "yyyy-MM-ddTHH:MM" or "beginning",
        "endDate": "yyyy-MM-ddTHH:MM" or "now"
    }
     */

    @GetMapping("/getTrackingInnerDate")
    public ResponseEntity getTrackingInnerDate(
            @Valid @RequestBody String data) {
        try{
            JSONObject jData = new JSONObject(data);
            String deviceName = jData.getString("device");
            String startDate = jData.getString("startDate");
            String endDate = jData.getString("endDate");
            Device device = new Device();
            if(deviceRepository.existsByName(deviceName)){
                device = deviceRepository.findByName(deviceName).get(0);
            }
            else{
                return new ResponseEntity("No device Found", HttpStatus.NOT_FOUND);
            }
            Date start;
            Date end;
            try {
                if (startDate.compareTo("beginning") != 0)
                    start = as.parse(startDate);
                else
                    start = new Date();
                if (endDate.compareTo("now") != 0)
                    end = as.parse(endDate);
                else
                    end = new Date();
            }
            catch (Exception e){
                logger.error("Bad Date");
                return new ResponseEntity<String>("Make sure that you had sent the Date Format\n" + formatApiInnerDate, HttpStatus.BAD_REQUEST);
            }
            ArrayList<Tracking> trackings;
            if (startDate.compareTo("beginning") != 0)
                 trackings = (ArrayList<Tracking>) trackingRepository.findByDtmBetweenAndDevice(start,end,device);
            else
                trackings = (ArrayList<Tracking>)trackingRepository.findByDtmLessThanEqualAndDevice(end, device);
            return new ResponseEntity(trackings,HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Could not parse data. Bad request");
            return new ResponseEntity<String>("Make sure that you had sent the correct JSON \n" + formatApiInnerDate, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getLastTelemetry")
	public ResponseEntity getLastTelemetry(@RequestParam String device) {
            if(deviceRepository.existsByName(device)){
                Device d = deviceRepository.findByName(device).get(0);
                List<Telemetria> ts = telemetriaRepository.findByDeviceOrderByDtmDesc(d);
                return new ResponseEntity(ts.get(0),HttpStatus.OK);
            }
            else{
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        }

    @GetMapping("/getLastTracking")
    public ResponseEntity getLastTracking(@RequestParam String device) {
        if(deviceRepository.existsByName(device)){
            Device d = deviceRepository.findByName(device).get(0);
            List<Tracking> ts = trackingRepository.findByDeviceOrderByDtmDesc(d);
            return new ResponseEntity(ts.get(0),HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }


/*
--------------------Axiliar Functions------------------------------------
 */
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
    private String findFamilyMac(JSONObject wifiList){
        if(wifiList.has("00:00:00:00:00:00")) {
            List<WifiScan> wifiScans = wifiScanRepository.findAll();
            for(int i = wifiScans.size() -1; i>=0; i--){
                WifiScan wifiScan = wifiScans.get(i);
                String mac = wifiScan.getMAC();
                if (mac.compareTo("00:00:00:00:00:00") != 0) {
                    if (macRepository.existsByMac(mac)) {
                        return macRepository.findByMac(mac).get(0).getFamily();
                    } else return "";
                }
            }
            return "";
        }
        Iterator<String> iterator = wifiList.keys();
        while(iterator.hasNext()) {
            String mac = (String) iterator.next();
            if(macRepository.existsByMac(mac)){
                return macRepository.findByMac(mac).get(0).getFamily();
            }
        }
        return "";
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
    private void postWifiScans(JSONObject wifiList, RawSensorData rawSensorData){
        Iterator<String> iterator = wifiList.keys();
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            int rssi = wifiList.getInt(key);
            WifiScan wifiScan = new WifiScan(rssi,key);
            postWifiScan(wifiScan,rawSensorData);
        }
    }
    private JSONObject getWifiMACs(JSONArray wifi){
        if(wifi.isEmpty()){
            List<RawSensorData> rawSensorDataList= rawDataRepository.findAll();
            RawSensorData rawSensorData = rawSensorDataList.get(rawSensorDataList.size() - 2);
            //List<WifiScan> wifiScans = wifiScanRepository.findByRawSensorDataOrderById(rawSensorData);
            //wifiScans = wifiScans.subList(0,2);
            Set<WifiScan> wifiScans = rawSensorData.getWifiScans();
            Iterator<WifiScan> iterator = wifiScans.iterator();
            JSONObject json = new JSONObject();
            while(iterator.hasNext()){
                WifiScan wifiScan = iterator.next();
                json.put(wifiScan.getMAC(),wifiScan.getRSSI());
            }
            return json;
        }
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

    private void postAlert(Alerta alert, Device device){
        device.getAlertas().add(alert);
        alert.setDevice(device);
        alertaRepository.save(alert);
        deviceRepository.save(device);
    }
    private void postTelemtry(Telemetria telemetry, Device device){
        device.getTelemetrias().add(telemetry);
        telemetry.setDevice(device);
        telemetriaRepository.save(telemetry);
        deviceRepository.save(device);
    }
    private void postRawSensorDara(RawSensorData rawSensorData, Device device){
        device.getRawSensorDatas().add(rawSensorData);
        rawSensorData.setDevice(device);
        rawDataRepository.save(rawSensorData);
        deviceRepository.save(device);

    }
    private void postWifiScan(WifiScan wifiScan,RawSensorData rawSensorData){
        rawSensorData.getWifiScans().add(wifiScan);
        wifiScan.setRawSensorData(rawSensorData);
        wifiScanRepository.save(wifiScan);
        rawDataRepository.save(rawSensorData);
    }
    private void postMessageGuess(MessageGuess messageGuess){
        messageGuessRepository.save(messageGuess);
    }
    private void postMessage(Message message, MessageGuess messageGuess){
        messageGuess.getMessages().add(message);
        message.setMessageGuess(messageGuess);
        messageRepository.save(message);
        messageGuessRepository.save(messageGuess);
    }
    private void postGoApiResponse(GoApiResponse goApiResponse, Message message, Device device){
        device.getGoApiResponses().add(goApiResponse);
        message.setGoApiResponse(goApiResponse);
        goApiResponse.setDevice(device);
        goApiResponse.setMessage(message);
        goApiResponseRepository.save(goApiResponse);
        deviceRepository.save(device);
        messageRepository.save(message);
    }
    private void postTracking(Tracking tracking, Device device, long idLocationGroup){
        LocationGroup locationGroup = locationGroupRepository.findById(idLocationGroup).get();
        locationGroup.getTrackings().add(tracking);
        device.getTrackings().add(tracking);
        tracking.setDevice(device);
        tracking.setLocationGroup(locationGroup);
        trackingRepository.save(tracking);
        deviceRepository.save(device);
        locationGroupRepository.save(locationGroup);
    }
}
