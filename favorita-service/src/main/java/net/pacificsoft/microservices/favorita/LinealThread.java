package net.pacificsoft.microservices.favorita;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LinealThread extends Thread {
    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    private Ruta ruta;
    private Logger logger;


    public LinealThread(Ruta ruta, AlertaRepository alertaRepository, TrackingRepository trackingRepository){
        this.ruta = ruta;
        this.alertaRepository = alertaRepository;
        this.trackingRepository = trackingRepository;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void run() {
        Device device = ruta.getDevice();
        Date startDate = ruta.getStart_date();
        Date endDate = ruta.getEnd_date();
        while (ruta.getEnd_date().compareTo(new Date()) > 0){
            ArrayList<String> priorityQueue = new ArrayList<>();

            priorityQueue.add("?");
            priorityQueue.add("recepcion carnes");
            priorityQueue.add("carga furgon");

            LinealizeService linealizeService = new LinealizeService(priorityQueue,true);
            linealizeService.setLogger(logger);
            linealizeService.setAlertaRepository(alertaRepository);
            linealizeService.setRuta(ruta);
            List<Tracking> trackingList = trackingRepository.findByDtmBetweenAndDeviceOrderByDtm(startDate, endDate, device);
            for (Tracking t : trackingList){
                linealizeService.addTrack(t);
            }
            List<Tracking> q =linealizeService.getTrackingList();
            for (Tracking t : q){
                this.logger.info(t.getLocation());
            }
        }

    }
}
