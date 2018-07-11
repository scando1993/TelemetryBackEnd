package net.pacificsoft.microservices.telemetry.sms;

import net.pacificsoft.microservices.telemetry.sms.events.EventHolderBean;
import net.pacificsoft.microservices.telemetry.sms.watcher.FileWatcher;
import net.pacificsoft.microservices.telemetry.sms.watcher.databaseConMongo;
import net.pacificsoft.microservices.telemetry.sms.watcher.databaseConMysql;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class TelemetrySMSApplicationListener  implements ApplicationListener<ContextRefreshedEvent> {
    private EventHolderBean eventHolderBean;
    private static Logger logger = LogManager.getLogger(TelemetrySMSApplicationListener.class);

    @Autowired
    public void setEventHolderBean(EventHolderBean eventHolderBean) {
        this.eventHolderBean = eventHolderBean;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("Event Fired");
        eventHolderBean.setEventFired(true);
        File archivo = new File("c:/Users/carlo/Desktop/prueba.txt ");
        String path= "c:/Users/carlo/Desktop/prueba.txt";






        TimerTask task = new FileWatcher( archivo) {
            protected void onChange( File file ) throws IOException {
                //databaseConMysql.realizarCargaDB(path);
                databaseConMongo.realizarCargaDB(path);
                System.out.println( "File "+ file.getName() +" have change !" );
            }
        };

        Timer timer = new Timer();
        // repeat the check every second
        timer.schedule( task , new Date(), 1000 );
    }
}
