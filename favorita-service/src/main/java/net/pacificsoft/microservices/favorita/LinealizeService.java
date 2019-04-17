package net.pacificsoft.microservices.favorita;
import com.google.gson.Gson;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.LocationPriority;
import  net.pacificsoft.microservices.favorita.models.Tracking;

import com.google.common.collect.EvictingQueue;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import org.slf4j.Logger;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;


public class LinealizeService {
    private ArrayList<String> locationPriority;
    private List<Tracking> trackingList;
    private Queue<QueueElement> queue = EvictingQueue.create(3);
    private int index;
    private Map<Integer, Tracking> anomaliesMap = new TreeMap<>();
    private boolean dirrection;
    private Ruta ruta;

    private Logger logger;
    private AlertaRepository alertaRepository;

    public LinealizeService(ArrayList<String> locationPriority, boolean dirrection) {
        this.locationPriority = locationPriority;
        this.dirrection = dirrection;
        this.trackingList = new ArrayList<>();
        this.index = 0;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public void setAlertaRepository(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public ArrayList<String> getLocationPriority() {
        return locationPriority;
    }

    public void setLocationPriority(ArrayList<String> locationPriority) {
        this.locationPriority = locationPriority;
    }

    public List<Tracking> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(List<Tracking> trackingList) {
        this.trackingList = trackingList;
    }

    public boolean isDirrection() {
        return dirrection;
    }

    public void setDirrection(boolean dirrection) {
        this.dirrection = dirrection;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void addTrack(Tracking tracking){
        this.trackingList.add(tracking);
        int arrayPos = this.trackingList.size() -1;
        if(this.trackingList.size() == 0)
            arrayPos = 0;
        QueueElement queueElement = new QueueElement(tracking, arrayPos);
        this.queue.add(queueElement);
        correctLocations(this.dirrection);

    }
    public void correctLocations(boolean righ) {
        if (this.queue.size() < 3)
            return;
        int a = 1;
        if (!righ)
            a = -1;

        boolean flag1 = true;
        int indexChangesCount = 0;
        int initialIndex = this.index;

        while (flag1) {
            String actualLocation = this.locationPriority.get(this.index);
            int queueCounter = 0;
            int possitivesCount = 0;
            List<QueueElement> negativesTrackings = new ArrayList<>();
            List<Integer> negativesPos = new ArrayList<>();
            for (QueueElement queueElement: this.queue) {
                if (queueElement.getLocation().compareTo(actualLocation) == 0){
                    possitivesCount ++;
                }
                else {
                    negativesTrackings.add(queueElement);
                    negativesPos.add(queueCounter);
                }
                queueCounter ++;
            }
            if (possitivesCount >= negativesTrackings.size()){
                boolean flag2 = true;
                Object[] arrayQueue = this.queue.toArray();
                for (int i = 0; i < negativesTrackings.size(); i++) {
                    QueueElement anomaly = negativesTrackings.get(i);
                    String anomalyLocation = anomaly.getLocation();
                    int anomalyPosArray = anomaly.getArrayPosition();
                    int anomalyPosQueue = negativesPos.get(i);
                    boolean condition = indexCondition(anomaly, anomalyPosQueue, righ);

                    if (this.anomaliesMap.containsKey(anomalyPosArray)){
                        Tracking trackChange = this.anomaliesMap.get(anomalyPosArray);
                        this.trackingList.set(anomalyPosArray, trackChange );
                        ((QueueElement)arrayQueue[anomalyPosQueue]).setTracking(trackChange);
                        //anomaly.setTracking(trackChange);
                    }
                    else if(condition){
                        String priorityChange = this.locationPriority.get(this.index);
                        Tracking trackChange = this.trackingList.get(anomalyPosArray);
                        trackChange.setLocation(priorityChange);
                        this.trackingList.set(anomalyPosArray, trackChange);
                        ((QueueElement)arrayQueue[anomalyPosQueue]).setTracking(trackChange);
                        //anomaly.setTracking(trackChange);
                    }
                    else {
                        String priorityChange = this.locationPriority.get(this.index);
                        //anomaly.setLocation(priorityChange);
                        Tracking tracking = anomaly.getTracking();
                        Tracking trackingChange = new Tracking(priorityChange, tracking.getDtm());
                        trackingChange.setDevice(tracking.getDevice());
                        trackingChange.setId(tracking.getId());

                        //((QueueElement)arrayQueue[anomalyPosQueue]).setLocation(priorityChange);
                        //Tracking trackChange = ((QueueElement)arrayQueue[anomalyPosQueue]).getTracking();
                        //this.anomaliesMap.put(anomalyPosArray,anomaly.getTracking());
                        this.anomaliesMap.put(anomalyPosArray,trackingChange);
                    }

                }
                //return;
                for (Object q:arrayQueue) {
                    this.queue.add((QueueElement) q);
                }
                break;
            }
            else {
                if (indexChangesCount == 2 || this.locationPriority.get(index).compareTo(this.locationPriority.get(initialIndex)) == 0) {
                    if (initialIndex + a == this.locationPriority.size() -1)
                        return;
                    this.index = initialIndex + a;
                    return;
                }
                else if (this.index == this.locationPriority.size() - 1 & righ)
                    this.index = 0;
                else if (this.index == 0 & !righ )
                    this.index = this.locationPriority.size() - 1;
                else
                    this.index = this.index + a;
                this.anomaliesMap = new TreeMap<>();
                indexChangesCount ++;
            }
        }
        if(initialIndex != this.index && indexChangesCount == 1){
            String initialLocation = this.locationPriority.get(initialIndex);
            String actualLocation = this.locationPriority.get(index);
            Date date = ((QueueElement)this.queue.toArray()[1]).getTracking().getDtm();
            //RestTemplate restTemplate = new RestTemplate();
            SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String changeDate = as.format(date);
            String msg = "Ruta "+ ruta.getId()+". Se cambio de zona a " + actualLocation +  " aproximadamente a las: " + changeDate;
            Alerta alert = new Alerta("cambio_zona", msg, date);
            try{
                List<Alerta> alertas = alertaRepository.findByRutaAndDtmAndTypeAlert(this.ruta, date, msg);
                if(alertas.size() == 0){
                    //this.alertaRepository.save(alert);
                    //postAlert(alert, ruta);
                    this.logger.info("Creando alerta");
                }
            }
            catch (Exception e){
                this.logger.warn("No se puede error");
            }
        }

    }

    public boolean indexCondition(QueueElement anomaly, int anomalyPosQueue, boolean right){
        String anomalyLocation = anomaly.getLocation();
        int anomalyPosArray = anomaly.getArrayPosition();
        boolean condition = false;
        int a,b,c,d;
        ArrayList<Integer> restList = new ArrayList<>();
        if(right){
            d = 2;
            restList.add(3);
            restList.add(2);
            restList.add(1);
        }
        else {
            d = 0;
            restList.add(-3);
            restList.add(-2);
            restList.add(-1);
        }
        if(anomalyPosQueue == d){
            if(right){
                for(Integer i: restList){
                    if (this.index - i >= 0){
                        String compareLocation = this.locationPriority.get(this.index - i);
                        condition = condition || anomalyLocation.compareTo(compareLocation) == 0;
                    }
                }
                /*
                Object[] arrayPriority = locationPriority.toArray();
                condition = anomalyLocation.compareTo((String) arrayPriority[(this.index - a)]) == 0;
                //|| anomalyLocation.compareTo(this.locationPriority.get(this.index - b)) == 0
                //|| anomalyLocation.compareTo(this.locationPriority.get(this.index - c)) == 0;
                */
            }
            else{
                for(Integer i: restList){
                    if (this.index - i < locationPriority.size()){
                        String compareLocation = this.locationPriority.get(this.index - i);
                        condition = condition || anomalyLocation.compareTo(compareLocation) == 0;
                    }
                }
                /*
                if(this.index - c < locationPriority.size())
                    condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0
                            || anomalyLocation.compareTo(this.locationPriority.get(this.index - b)) == 0
                            || anomalyLocation.compareTo(this.locationPriority.get(this.index - c)) == 0;
                else if(this.index - b < locationPriority.size())
                    condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0
                            || anomalyLocation.compareTo(this.locationPriority.get(this.index - b)) == 0;
                else if(this.index - a < locationPriority.size())
                    condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0;
                */
            }
        }
        return condition;
    }

    static class QueueElement{
        Tracking tracking;
        int arrayPosition;
        String location;
        public QueueElement(Tracking tracking, int arrayPosition) {
            this.tracking = tracking;
            this.arrayPosition = arrayPosition;
            this.location = tracking.getLocation();
        }

        public Tracking getTracking() {
            return tracking;
        }

        public void setTracking(Tracking tracking) {
            this.tracking = tracking;
            this.location = tracking.getLocation();
        }

        public int getArrayPosition() {
            return arrayPosition;
        }

        public void setArrayPosition(int arrayPosition) {
            this.arrayPosition = arrayPosition;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
            this.tracking.setLocation(location);
        }
    }

    private void postAlert(Alerta alert, Ruta ruta){
        ruta.getAlertas().add(alert);
        alert.setRuta(ruta);
        alert.setDevice(ruta.getDevice());
        alertaRepository.save(alert);
    }

}