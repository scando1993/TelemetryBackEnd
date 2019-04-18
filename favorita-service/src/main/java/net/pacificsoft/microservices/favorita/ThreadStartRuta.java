package net.pacificsoft.microservices.favorita;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.TelemetriaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ThreadStartRuta extends Thread{
    
    @Autowired
    private RutaRepository repository;
    
    @Autowired
    private AlertaRepository alertaRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private TrackingRepository trackingRepository;
    
    @Autowired
    private TelemetriaRepository telemetriaRepository;
    
    @Autowired
    private RawSensorDataRepository rawSensorDataRepository;

    private Logger logger;
    public ThreadStartRuta(RutaRepository repository, AlertaRepository alertaRepository, DeviceRepository deviceRepository,
                           TrackingRepository trackingRepository, TelemetriaRepository telemetriaRepository, RawSensorDataRepository rawDataRepository) {
        this.repository = repository;
        this.alertaRepository = alertaRepository;
        this.deviceRepository = deviceRepository;
        this.trackingRepository = trackingRepository;
        this.telemetriaRepository = telemetriaRepository;
        this.rawSensorDataRepository = rawDataRepository;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    List<Long> ids = new ArrayList();
    @Override
	public void run() {
            while(true){
            List<Ruta> rutas = repository.findAll();
            for (Ruta r: rutas){
                Date date = new Date();
                if(date.compareTo(r.getStart_date())>=0 &&
                   (!ids.contains(r.getId())) && !(r.getStatus().equals("Finalizado"))){
                    ids.add(r.getId());
                    r.setStatus("Activo");
                    repository.save(r);
                    String typeAlert = "inicio_ruta";
                    if(r.getDevice()!=null && r.getProducto()!=null ){
                        String mensaje = "Inicio ruta "+r.getId()+". Device: " + r.getDevice().getName() + 
                                    " y producto " + r.getProducto().getName();
                        ThreadStateRuta ts = new ThreadStateRuta(r, repository, deviceRepository, alertaRepository,
                                                        trackingRepository, telemetriaRepository, rawSensorDataRepository);
                        saveRuta(r, typeAlert, mensaje);
                        ts.setLogger(logger);
                        LinealThread linealThread = new LinealThread(r,alertaRepository, trackingRepository);
                        //linealThread.setLogger(logger);
                        linealThread.start();
                        ts.start();
                    }
                }
            }
        }
        }
        
    public void saveRuta(Ruta ruta, String typeAlert, String mensaje){
        Alerta alert = new Alerta(typeAlert, mensaje, new Date());
                alert.setDevice(ruta.getDevice());
                alert.setRuta(ruta);
                ruta.getDevice().getAlertas().add(alert);
                ruta.getAlertas().add(alert);
                alertaRepository.save(alert);
                deviceRepository.save(ruta.getDevice());
                repository.save(ruta);
    }
    public void startLinealizeService(Device device, Date start, Date end){
        ArrayList<String> priorityQueue = new ArrayList<>();
        priorityQueue.add("?");
        priorityQueue.add("recepcion carnes");
        priorityQueue.add("carga furgon");

        LinealizeService linealizeService = new LinealizeService(priorityQueue,true);
        linealizeService.setLogger(logger);
        linealizeService.setAlertaRepository(alertaRepository);
        List<Tracking> trackingList = trackingRepository.findByDtmBetweenAndDeviceOrderByDtm(start, end, device);
        for (Tracking t : trackingList){
            linealizeService.addTrack(t);

        }
        List<Tracking> q =linealizeService.getTrackingList();
        for (Tracking t : q){
            logger.info(t.getLocation());
        }
    }
}
