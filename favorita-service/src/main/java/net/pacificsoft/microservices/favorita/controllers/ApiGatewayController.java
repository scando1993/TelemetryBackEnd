
package net.pacificsoft.microservices.favorita.controllers;


import net.pacificsoft.microservices.favorita.LinealizeService;
import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.models.application.LocalesMac;
import net.pacificsoft.microservices.favorita.repository.*;
import org.json.JSONArray;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import net.pacificsoft.microservices.favorita.SaveMacLocal;
import net.pacificsoft.microservices.favorita.ThreadStartRuta;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.application.LocalesMacRepository;
import net.pacificsoft.microservices.favorita.repository.application.LocalesRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
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
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private LocalesRepository localesRepository;
    @Autowired
    private LocalesMacRepository localesMacRepository;
    @Autowired
    private ConfigurationDeviceRepository configurationDeviceRepository;
    @Autowired
    private DetailConfigurationRepository detailConfigurationRepository;

    //final String uri = "http://104.209.196.204:9090/track";

    final String uri = "http://172.16.10.98:8005/track";
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
            boolean useUnknownDevice = false;
            boolean useDeviceFamily = false;

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
                String dtmS = (String) jDataBody.remove("epochDateTime");
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
                if(!deviceRepository.existsByName("unknown")) {
                    logger.error("Device: unknown is not storaged in DB. Returning 500");
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                logger.warn("Device no found in database, setting relations to device = 'unknown'");
                device = (deviceRepository.findByName("unknown")).get(0);
                useUnknownDevice = true;
                //-------creating alert
                Alerta alerta = new Alerta("Device error","Device: " + deviceName + " not found, continuing ith Device: unknown", new Date());
                postAlert(alerta, device);
            }
            //-----------updating Device Status
            //updateStatus(device, batteryLevel, epochDateTime);
            Status statusDevice = new Status(batteryLevel, epochDateTime, null ,null );
            postStatus(statusDevice, device);
            logger.info("Device status has been storaged");

            //-----------creating rawsSensorData
            RawSensorData rawSensorData = new RawSensorData(epoch,temperature,epochDateTime,rawData);
            postRawSensorDara(rawSensorData, device);
            logger.info("Raw data has been storaged");

            //-------Obtainning MACS
            JSONObject wifiList = getWifiMACs(wifis, device);

            //-----------creating wifiSensor
            postWifiScans(wifiList,rawSensorData);
            logger.info("WifiSensor has been storaged");
/*
            if(!validDevice){
                logger.error("Device not found returning 404");
                Alerta alert = new Alerta("Device error","Device: " + deviceName + " not found, saving data with deviceName: unknown");
                return new ResponseEntity(alert.toJson().toMap(), HttpStatus.NOT_FOUND);
            }
*/
            //-----creatinig Telemetry
            Telemetria telemetry = new Telemetria(epochDateTime,"temperature",temperature);
            postTelemtry(telemetry,device);
            logger.info("Telemetry has been storaged");

            //-------getting Families
            String family = findFamilyMac(wifiList, rawSensorData);
            Set<Family> families = null;
            //family = "favorita";
            if(family.compareTo("") == 0){
                logger.warn("Any MAC is associated with any family. Try with families associated with device");
                Alerta alert = new Alerta("MAC error", "MACs do not have any family, looking for a family asociate with the device: "+deviceName, new Date());
                //postAlert(alert,device);
                if(useUnknownDevice)
                    return new ResponseEntity(alert.toJson().toMap(),HttpStatus.PARTIAL_CONTENT);
                families = device.getGroup().getFamilies();
                useDeviceFamily = true;
                if(families.size() == 0){
                    logger.error("Device is not associated with any family");
                    Alerta alert2 = new Alerta("Device error", "Device does not have any family", new Date());
                    postAlert(alert2,device);
                    return new ResponseEntity("Could not found any family associated with given MACs or device", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            //-------creating request to send Go server
            JSONObject jsonRequesGoServer = new JSONObject();
            JSONObject jData = null;
            //----s
            JSONObject s = new JSONObject();
            jsonRequesGoServer.put("t", epoch);
            jsonRequesGoServer.put("d", deviceName);
            s.put("wifi", wifiList);
            jsonRequesGoServer.put("s", s);
            if(!useDeviceFamily){
                //----f
                jsonRequesGoServer.put("f", family);
                //----ready to be send
                logger.info("Data prepared to send");
                logger.info("sending data to: " + uri);
                logger.info("" + jsonRequesGoServer.toString());
                jData = new JSONObject(restTemplate.postForObject( uri, jsonRequesGoServer.toString(), String.class));
            }
            else{
                Iterator<Family> iterator = families.iterator();
                boolean errorGetData = false;
                while(iterator.hasNext()){
                    Family f = iterator.next();
                    jsonRequesGoServer.put("f",f.getName());
                    jData = new JSONObject(restTemplate.postForObject( uri, jsonRequesGoServer.toString(), String.class));
                    try {
                        Boolean empty = jData.getJSONObject("message").getJSONObject("location_names").isEmpty();
                        if (empty)
                            jsonRequesGoServer.remove("f");
                        else
                            break;
                    }
                    catch (Exception e){errorGetData = true;}
                }
                if(errorGetData){
                    logger.error("Response is empty. Could not obtain a valid prediction, maybe invalid family");
                    Alerta alert = new Alerta("Go Server error", "Response is empty. Could not obtain a valid prediction, maybe invalid family", new Date());
                    postAlert(alert,device);
                    return new ResponseEntity(alert.toJson().toMap(),HttpStatus.PRECONDITION_FAILED);
                }
            }

            logger.info("Successfull Responce");
            boolean conditionGoServer;
            try {
                conditionGoServer = jData.getJSONObject("message").getJSONObject("location_names").isEmpty();
            }
            catch (Exception e){conditionGoServer = true;}
            if(conditionGoServer){
                logger.error(jData.toString());
                logger.error("Response is empty. Could not obtain a valid prediction, maybe invalid family. Setting location to : ?");
                Alerta alert = new Alerta("Go Server error", "Response is empty. Could not obtain a valid prediction, maybe invalid family. Setting location to : ?", new Date());
                postAlert(alert,device);
                Tracking tracking = new Tracking("?", epochDateTime);
                postTracking(tracking,device,defaultTrackingLocationGroup);
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
            logger.info("MessageGuess has been storaged");

            //---------Creating Message
            Message message = new Message();
            postMessage(message, messageGuess);
            logger.info("Message has been storaged");

            //--------Creating goApiResponse
            GoApiResponse goApiResponse = new GoApiResponse(status);
            postGoApiResponse(goApiResponse,message,device);
            logger.info("goApiResponse has been storaged");

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

    /*
    Structure of getTrackingInnerDate json
    {
        "device": String,
        "startDate": "yyyy-MM-ddTHH:MM" or "beginning",
        "endDate": "yyyy-MM-ddTHH:MM" or "now"
    }
     */

    @GetMapping("/getTrackingBetweenDates")
    public ResponseEntity getTrackingBetweenDates(
            @RequestParam String deviceName, @RequestParam String startDate, @RequestParam String endDate) {
        try{
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Device device;
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
                    start = formater.parse(startDate);
                else
                    start = new Date();
                if (endDate.compareTo("now") != 0)
                    end = formater.parse(endDate);
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

    @GetMapping("/getTelemetryBetweenDates")
    public ResponseEntity getTelemetryBetweenDate(
            @RequestParam String deviceName, @RequestParam String startDate, @RequestParam String endDate) {
        try{
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            Device device;
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
                    start = formater.parse(startDate);
                else
                    start = new Date();
                if (endDate.compareTo("now") != 0)
                    end = formater.parse(endDate);
                else
                    end = new Date();
            }
            catch (Exception e){
                logger.error("Bad Date");
                return new ResponseEntity<String>("Make sure that you had sent the Date Format\n" + formatApiInnerDate, HttpStatus.BAD_REQUEST);
            }
            ArrayList<Telemetria> telemetries;
            if (startDate.compareTo("beginning") != 0)
                telemetries = (ArrayList<Telemetria>) telemetriaRepository.findByDtmBetweenAndDevice(start,end,device);
            else
                telemetries = (ArrayList<Telemetria>)telemetriaRepository.findByDtmLessThanEqualAndDevice(end, device);
            return new ResponseEntity(telemetries,HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Could not parse data. Bad request");
            return new ResponseEntity<String>("Make sure that you had sent the correct JSON \n" + formatApiInnerDate, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllFamilies")
        public ResponseEntity getAllFamilies(){
        try{
            JSONObject families = new JSONObject(restTemplate.postForObject(urlFamily,new Family(),JSONObject.class));
            return new ResponseEntity(families, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLastTelemetry")
	public ResponseEntity getLastTelemetry(@RequestParam String device) {
            if(deviceRepository.existsByName(device)){
                Device d = deviceRepository.findByName(device).get(0);
                List<Telemetria> ts = telemetriaRepository.findByDeviceOrderByDtmDesc(d);
                if(ts.isEmpty()){
                    return new ResponseEntity(new ArrayList(), HttpStatus.NOT_FOUND);
                }
                else{
                    return new ResponseEntity(ts.get(0),HttpStatus.OK);
                }
            }
            else{
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        }
        
    @GetMapping("/getLastStatus")
	public ResponseEntity getLastStatus(@RequestParam Long deviceid) {
            if(deviceRepository.existsById(deviceid)){
                Device d = deviceRepository.findById(deviceid).get();
                List<Status> ts = statusRepository.findByDeviceOrderByLastTransmisionDesc(d);
                if(ts.isEmpty()){
                    return new ResponseEntity(new ArrayList(), HttpStatus.NOT_FOUND);
                }
                else{
                    return new ResponseEntity(ts.get(0),HttpStatus.OK);
                }
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
            if(ts.isEmpty()){
               return new ResponseEntity(new ArrayList(), HttpStatus.NOT_FOUND);
            }
            else{
               return new ResponseEntity(ts.get(0),HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getDeviceTrack")
    public ResponseEntity getOrderTrackingsDevice(@RequestParam String device) {
        if(deviceRepository.existsByName(device)){
            Device d = deviceRepository.findByName(device).get(0);
            List<Tracking> ts = trackingRepository.findByDeviceOrderByDtmDesc(d);
            return new ResponseEntity(ts,HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getDeviceTelemetry")
    public ResponseEntity getOrderTelemetrysDevice(@RequestParam String device) {
        if(deviceRepository.existsByName(device)){
            Device d = deviceRepository.findByName(device).get(0);
            List<Telemetria> ts = telemetriaRepository.findByDeviceOrderByDtmDesc(d);
            return new ResponseEntity(ts,HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/getAlerts")
    public ResponseEntity getAlerts(@RequestParam String device) {
        if(deviceRepository.existsByName(device)){
            Device d = deviceRepository.findByName(device).get(0);
            List<Telemetria> ts = telemetriaRepository.findByDeviceOrderByDtmDesc(d);
            return new ResponseEntity(ts,HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAlertasRuta")
        public ResponseEntity getAlertasRuta(@RequestParam Long rutaid){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            if(rutaid > 0){
                Ruta ruta = rutaRepository.findById(rutaid).get();
                List<Alerta> alertas = alertaRepository.findByRutaOrderByDtm(ruta);
                List<Alerta> rAl = new ArrayList();
                List<Map<String, Object>> result = new ArrayList();
                JSONObject jAlerta;
                List<Date> dates = new ArrayList();
                for(Alerta a: alertas){
                    Date d = new Date(a.getDtm().getYear(), a.getDtm().getMonth(), a.getDtm().getDate());
                    rAl  = new ArrayList();
                    jAlerta = new JSONObject();
                    if(!dates.contains(d)){
                        dates.add(d);
                        rAl.add(a);
                        Date comp = a.getDtm();
                        for (Alerta b: alertas){
                            Date m = b.getDtm();
                            if(m.getYear()==comp.getYear() && m.getMonth()==comp.getMonth() &&
                               m.getDate()==comp.getDate() && b.getId()!=a.getId()){
                                rAl.add(b);
                            }
                        }
                        Collections.sort(rAl);
                        jAlerta.put("Dtm", d);
                        jAlerta.put("Alertas", rAl.toArray());
                        result.add(jAlerta.toMap());
                    }
                }
                return new ResponseEntity(result,HttpStatus.OK);
            }
            else if(rutaid==0){
                List<Alerta> alertas = alertaRepository.findByRutaIsNotNullOrderByDtm();
                List<Alerta> rAl = new ArrayList();
                List<Map<String, Object>> result = new ArrayList();
                JSONObject jAlerta;
                List<Date> dates = new ArrayList();
                for(Alerta a: alertas){
                    Date d = new Date(a.getDtm().getYear(), a.getDtm().getMonth(), a.getDtm().getDate());
                    rAl  = new ArrayList();
                    jAlerta = new JSONObject();
                    if(!dates.contains(d)){
                        dates.add(d);
                        rAl.add(a);
                        Date comp = a.getDtm();
                        for (Alerta b: alertas){
                            Date m = b.getDtm();
                            if(m.getYear()==comp.getYear() && m.getMonth()==comp.getMonth() &&
                               m.getDate()==comp.getDate() && b.getId()!=a.getId()){
                                rAl.add(b);
                            }
                        }
                        Collections.sort(rAl);
                        jAlerta.put("Dtm", d);
                        jAlerta.put("Alertas", rAl.toArray());
                        result.add(jAlerta.toMap());
                    }
                }
                return new ResponseEntity(result,HttpStatus.OK);
            }
            else{
                List<Ruta> rutas = rutaRepository.findByStatusLike("Finalizado");
                JSONObject jRuta;
                List<Map<String, Object>> fResult = new ArrayList();
                for(Ruta r: rutas){
                    jRuta = new JSONObject();
                    jRuta.put("id", r.getId());
                    jRuta.put("status", r.getStatus());
                    jRuta.put("start_date", r.getStart_date());
                    jRuta.put("end_date", r.getEnd_date());
                    List<Alerta> alertas = new ArrayList();
                    for(Alerta a: r.getAlertas())
                        alertas.add(a);
                    Collections.sort(alertas);
                    List<Alerta> rAl = new ArrayList();
                    List<Map<String, Object>> result = new ArrayList();
                    JSONObject jAlerta;
                    List<Date> dates = new ArrayList();
                    for(Alerta a: alertas){
                        Date d = new Date(a.getDtm().getYear(), a.getDtm().getMonth(), a.getDtm().getDate());
                        rAl  = new ArrayList();
                        jAlerta = new JSONObject();
                        if(!dates.contains(d)){
                            dates.add(d);
                            rAl.add(a);
                            Date comp = a.getDtm();
                            for (Alerta b: alertas){
                                Date m = b.getDtm();
                                if(m.getYear()==comp.getYear() && m.getMonth()==comp.getMonth() &&
                                   m.getDate()==comp.getDate() && b.getId()!=a.getId()){
                                    rAl.add(b);
                                }
                            }
                            Collections.sort(rAl);
                            jAlerta.put("Dtm", d);
                            jAlerta.put("Alertas", rAl.toArray());
                            result.add(jAlerta.toMap());
                        }
                    }
                    jRuta.put("groupAlertas", result.toArray());
                    fResult.add(jRuta.toMap());
                }
                return new ResponseEntity(fResult,HttpStatus.OK);
            }

    }

    @GetMapping("/startThread")
        public void startThread(){
            //Ruta ruta = rutaRepository.findById(id).get();
            ThreadStartRuta ts = new ThreadStartRuta(rutaRepository, alertaRepository, deviceRepository,
                                                     trackingRepository, telemetriaRepository, rawDataRepository);
            ts.setLogger(logger);
            ts.start();
            //run(ruta);
    }
        
    @GetMapping("/getRutasNotEnd")
        public ResponseEntity getRutaNotEnd(){
            //Ruta ruta = rutaRepository.findById(id).get();
            return new ResponseEntity(rutaRepository.findByStatusNotLike("Finalizado"), HttpStatus.OK);
    }

    @GetMapping("/correctRoute")
    public ResponseEntity startThreadTrack(@RequestParam Long id){
        try {
            Ruta ruta = rutaRepository.findById(id).get();
            //ThreadStartRuta ts = new ThreadStartRuta();
            //ts.start();
            Device device = ruta.getDevice();
            startLinealizeService(device, ruta.getStart_date(), ruta.getEnd_date(), ruta);

            return new ResponseEntity("WOW", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAlertasOrder")
    public ResponseEntity alertasOnOrderByDtm(){
        try {
            Date now = new Date();
            List<Ruta> rutas = rutaRepository.findByStatusNotLike("Finalizado");
            List<Alerta> alertas = new ArrayList();
            List<Alerta> rAl = new ArrayList();
            List<Map<String, Object>> result = new ArrayList();
            JSONObject jAlerta;
            List<Date> dates = new ArrayList();
            for(Ruta r: rutas){
                for(Alerta a: r.getAlertas()){
                    alertas.add(a);
                }
                
            }
            Collections.sort(alertas);
            for(Alerta a: alertas){
                Date d = new Date(a.getDtm().getYear(), a.getDtm().getMonth(), a.getDtm().getDate());
                rAl  = new ArrayList();
                jAlerta = new JSONObject();
                if(!dates.contains(d)){
                    dates.add(d);
                    rAl.add(a);
                    Date comp = a.getDtm();
                    for (Alerta b: alertas){
                        Date m = b.getDtm();
                        if(m.getYear()==comp.getYear() && m.getMonth()==comp.getMonth() &&
                           m.getDate()==comp.getDate() && b.getId()!=a.getId()){
                            rAl.add(b);
                        }
                    }
                    Collections.sort(rAl);
                    jAlerta.put("Dtm", d);
                    jAlerta.put("Alertas", rAl.toArray());
                    result.add(jAlerta.toMap());
                }
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/saveMacLocales")
    public ResponseEntity saveMacLocales(@Valid @RequestBody SaveMacLocal saveMacLocal,
                                         @RequestParam Long localid){
        try {
            SaveMacLocal.postMacLocales(localesRepository, localesMacRepository, saveMacLocal, localid, logger);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getDataTrack")
    public ResponseEntity getDataTrack(@RequestParam Long rutaid){
        try {
            Ruta ruta = rutaRepository.findById(rutaid).get();
            List<Alerta> alertas = alertaRepository.findByRutaAndTypeAlertOrderByDtm(ruta, "cambio_zona");
            List<Map<String, Object>> result = new ArrayList();         
            JSONObject jData;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            logger.warn(""+ruta.getId());
            logger.warn(""+alertas.size());
            //Collections.sort(alertas);
            if(alertas.size() == 1){
                jData = new JSONObject();
                Date d = new Date();
                String dat[] = alertas.get(0).getMensaje().split("\\|");
                logger.warn("if1");
                if(dat.length==3){
                    logger.warn("if2");
                    String lugar = dat[1];
                    String fecha = dat[2];
                    jData.put("start_date", fecha);
                    jData.put("end_date", simpleDateFormat.format(d));
                    if(lugar.equals("?"))
                        lugar = "en ruta";
                    jData.put("lugar", lugar);
                    result.add(jData.toMap());
                }
            }
            else if(alertas.size() >1){
                logger.warn("if3");
                String dat[] = alertas.get(0).getMensaje().split("\\|");
                logger.warn("if3 " + dat.length);
                String fechaInicio = "";
                String fechaFin = "";
                String lugar = "";
                if(dat.length==3){        
                    logger.warn("if4 " + dat.length);
                    lugar = dat[1];
                    fechaInicio = dat[2];
                }
                for(int i = 1; i < alertas.size(); i++){
                    String dati[] = alertas.get(i).getMensaje().split("\\|");
                    logger.warn("for "+ i + " "+dat.length);
                    if(dat.length==3){
                        logger.warn("if5 " + dat.length);
                        jData = new JSONObject();
                        fechaFin = dati[2];
                        jData.put("start_date", fechaInicio);
                        jData.put("end_date", fechaFin);
                        if(lugar.equals("?"))
                            lugar = "en ruta";
                        jData.put("lugar", lugar);
                        if(!(fechaInicio.equals(fechaFin))){
                            result.add(jData.toMap());
                        }                        
                        fechaInicio = dati[2];
                        lugar = dati[1];
                    }
                }
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getMacConfiguration")
    public ResponseEntity getMacConfiguration(@RequestParam Long deviceid) {
        try{
            if(!deviceRepository.existsById(deviceid))
                return new ResponseEntity("Device Not Found", HttpStatus.NOT_FOUND);
            Device device = deviceRepository.findById(deviceid).get();
            if(device.getRutas().isEmpty())
                return new ResponseEntity("Any Route is associated with this device", HttpStatus.BAD_REQUEST);
            Ruta ruta = rutaRepository.findByStatusAndDevice("Activo", device).get(0);
            Set<LocalesMac> localesMacs = ruta.getLocalInicio().getLocalesMacs();
            localesMacs.addAll(ruta.getLocalFin().getLocalesMacs());
            String csv = "";
            Iterator<LocalesMac> iterator = localesMacs.iterator();
            Set<DetailConfiguration> wifiList = new HashSet<DetailConfiguration>();
            ConfigurationDevice configurationDevice;
            if(device.getConfigDevice() == null){
                configurationDevice = new ConfigurationDevice();
                device.setConfigDevice(configurationDevice);
                configurationDevice.setDevice(device);
                //configurationDeviceRepository.save(configurationDevice);
            }
            else configurationDevice = device.getConfigDevice();
            while(iterator.hasNext()){
                LocalesMac localesMac = iterator.next();
                String line = String.format("%s,%s,%s\n",localesMac.getSsid(),localesMac.getMac(), localesMac.getPassword());
                csv = csv + line;
                DetailConfiguration detailConfiguration = new DetailConfiguration(localesMac.getSsid(),localesMac.getMac(), localesMac.getPassword());
                detailConfiguration.setConfigDevice(configurationDevice);
                detailConfigurationRepository.save(detailConfiguration);
                wifiList.add(detailConfiguration);
            }

           // configurationDevice.setDetailConfigurations(wifiList);
            configurationDeviceRepository.save(configurationDevice);
            //device.getConfigDevice().setDetailConfigurations(wifiList);
            //ConfigurationDevice configurationDevice = configurationDeviceRepository.findByDevice(device).get(0);
            //configurationDevice.setDetailConfigurations(wifiList);
            deviceRepository.save(device);
            return new ResponseEntity(csv, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


/*
--------------------Auxiliar Functions------------------------------------
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
    private String findFamilyMac(JSONObject wifiList, RawSensorData rawSensorData){
        //this condition happends when any WIFI MAC is close
        if(wifiList.has("00:00:00:00:00:00") || wifiList.has("ff:ff:ff:ff:ff:ff")) {
            RawSensorData rawSensorData1;
            try{
                rawSensorData1 = rawDataRepository.findById(rawSensorData.getId() -1).get();
            }
            catch (Exception e){return "";}
            //List<WifiScan> wifiScans = wifiScanRepository.findAll();
            List<WifiScan> wifiScans = wifiScanRepository.findByRawSensorData(rawSensorData1);
            //List<WifiScan> wifiScans = (List)rawSensorData.getWifiScans();
            for(int i = wifiScans.size() -1; i>=0; i--){
                WifiScan wifiScan = wifiScans.get(i);
                String mac = wifiScan.getMac();
                if (mac.compareTo("00:00:00:00:00:00") != 0 || mac.compareTo("ff:ff:ff:ff:ff:ff") != 0) {
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
            String key = iterator.next();
            int rssi = wifiList.getInt(key);
            WifiScan wifiScan = new WifiScan(rssi,key);
            postWifiScan(wifiScan,rawSensorData);
        }
    }
    private JSONObject getWifiMACs(JSONArray wifi, Device device){
        boolean d;
        try{
            d = wifi.getJSONObject(0).getString("MAC").compareTo("ff:ff:ff:ff:ff:ff") == 0;
        }
        catch(Exception e){
            d = true;
        }
        //this conition happends when the location has not changed, so we send the last MACs different from ff:ff:ff:ff:ff:ff
        if(wifi.isEmpty() || d){
            JSONObject json = new JSONObject();
            try {
                List<RawSensorData> rawSensorDataList = rawDataRepository.findByDevice(device);
                RawSensorData rawSensorData = rawSensorDataList.get(rawSensorDataList.size() - 2);
                Set<WifiScan> wifiScans = rawSensorData.getWifiScans();
                Iterator<WifiScan> iterator = wifiScans.iterator();
                while (iterator.hasNext()) {
                    WifiScan wifiScan = iterator.next();
                    json.put(wifiScan.getMac(), wifiScan.getRssi());
                }
            }
            catch (Exception e){json.put("00:00:00:00:00:00", 0);}
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
        //locationGroup.getTrackings().add(tracking);
        device.getTrackings().add(tracking);
        tracking.setDevice(device);
        //tracking.setLocationGroup(locationGroup);
        trackingRepository.save(tracking);
        deviceRepository.save(device);
        locationGroupRepository.save(locationGroup);
    }
    /*
    private void updateStatus(Device device, int battery, Date lastTransmition) {
        Status status = device.getStatus();
        status.setBatery(battery);
        status.setLast_transmision(lastTransmition);
        statusRepository.save(status);
    }*/
    private void postStatus(Status status, Device device){
        device.getStatuses().add(status);
        status.setDevice(device);
        statusRepository.save(status);
        deviceRepository.save(device);
    }


    public void startLinealizeService(Device device, Date start, Date end, Ruta ruta){
        ArrayList<String> priorityQueue = new ArrayList<>();
        priorityQueue.add("?");
        priorityQueue.add("recepcion carnes");
            priorityQueue.add("carga furgon");

        LinealizeService linealizeService = new LinealizeService(priorityQueue,true);
        linealizeService.setLogger(logger);
        linealizeService.setAlertaRepository(alertaRepository);
        linealizeService.setRuta(ruta);
        List<Tracking> trackingList = trackingRepository.findByDtmBetweenAndDeviceOrderByDtm(start, end, device);
        for (Tracking t : trackingList){
            linealizeService.addTrack(t);

        }
        List<Tracking> q =linealizeService.getTrackingList();
        for (Tracking t : q){
            //logger.info(t.getId() + " " + t.getLocation() +"  " + t.getDtm());
            System.out.println(t.getId() + " " + t.getLocation() +"  " + t.getDtm());
        }
    }


}
