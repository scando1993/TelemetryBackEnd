package net.pacificsoft.microservices.favorita;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.LocationPriority;
import  net.pacificsoft.microservices.favorita.models.Tracking;

import com.google.common.collect.EvictingQueue;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import org.slf4j.Logger;

import java.util.*;


public class LinealizeService {
    private ArrayList<String> locationPriority;
    private List<Tracking> trackingList;
    private Queue<QueueElement> queue = EvictingQueue.create(2);
    private int index;
    private Map<Integer, Tracking> anomaliesMap = new TreeMap<>();
    private boolean dirrection;

    private Logger logger;
    private AlertaRepository alertaRepository;

    public LinealizeService(ArrayList<String> locationPriority, boolean dirrection) {
        this.locationPriority = locationPriority;
        this.dirrection = dirrection;
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
        int arrayPos = trackingList.size() -1;
        if(trackingList.size() == 0)
            arrayPos = 0;
        QueueElement queueElement = new QueueElement(tracking, arrayPos);
        queue.add(queueElement);
        correctLocations(this.dirrection);

    }
    public void correctLocations(boolean righ) {
        if (queue.size() <= 3)
            return;
        int a = 1;
        if (!righ)
            a = -1;

        boolean flag1 = true;
        int indexChangesCount = 0;
        int initialIndex = this.index;
        int queueCounter = 0;
        while (flag1) {
            int possitivesCount = 0;
            List<QueueElement> negativesTrackings = new ArrayList<>();
            List<Integer> negativesPos = new ArrayList<>();
            for (QueueElement queueElement: this.queue) {
                if (queueElement.getLocation().compareTo(this.locationPriority.get(this.index)) == 0){
                    possitivesCount ++;
                }
                else {
                    negativesTrackings.add(queueElement);
                    negativesPos.add(queueCounter);
                }
                queueCounter ++;
            }
            if (possitivesCount >= this.locationPriority.size()){
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
                        ((QueueElement)arrayQueue[anomalyPosQueue]).setLocation(priorityChange);
                        Tracking trackChange = ((QueueElement)arrayQueue[anomalyPosQueue]).getTracking();
                        anomaliesMap.put(anomalyPosArray,anomaly.getTracking());
                    }

                    for (Object q:arrayQueue)
                        this.queue.add((QueueElement) q);

                    break;
                    //return;
                }
            }
            else {
                if (indexChangesCount == 2) {
                    if (initialIndex + a == locationPriority.size() -1)
                        return;
                    return;
                }
                else if (this.index == locationPriority.size() - 1 & righ)
                    this.index = 0;
                else if (this.index == 0 & !righ )
                    this.index = this.locationPriority.size() - 1;
                else
                    this.index = this.index + a;
            }
        }
        if(initialIndex != this.index && indexChangesCount == 1){
            String initialLocation = this.locationPriority.get(initialIndex);
            String actualLocation = this.locationPriority.get(index);

            Alerta alert = new Alerta("Cambio de zona", "Se cambio de zona a " + actualLocation, new Date());
            try{
                alertaRepository.save(alert);
            }
            catch (Exception e){

            }
        }

    }

    public boolean indexCondition(QueueElement anomaly, int anomalyPosQueue, boolean right){
        String anomalyLocation = anomaly.getLocation();
        int anomalyPosArray = anomaly.getArrayPosition();
        boolean condition = false;
        int a,b,c,d;
        if(right){
            a = 1; b = 2; c = 3; d = 2;
        }
        else {
            a = 1; b = 2; c = 3; d = 2;
        }
        if(anomalyPosQueue == d){
            if(right){
                condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0
                || anomalyLocation.compareTo(this.locationPriority.get(this.index - b)) == 0
                || anomalyLocation.compareTo(this.locationPriority.get(this.index - c)) == 0;
            }
            else{
                if(this.index - c < locationPriority.size())
                    condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0
                            || anomalyLocation.compareTo(this.locationPriority.get(this.index - b)) == 0
                            || anomalyLocation.compareTo(this.locationPriority.get(this.index - c)) == 0;
                else if(this.index - b < locationPriority.size())
                    condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0
                            || anomalyLocation.compareTo(this.locationPriority.get(this.index - b)) == 0;
                else if(this.index - a < locationPriority.size())
                    condition = anomalyLocation.compareTo(this.locationPriority.get(this.index - a)) == 0;
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

}