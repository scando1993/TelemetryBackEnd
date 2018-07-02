package net.pacificsoft.microservices.loka.core;

import net.pacificsoft.microservices.loka.core.events.EventHolderBean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class LokaCoreApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private EventHolderBean eventHolderBean;
    private static Logger logger = LogManager.getLogger(LokaCoreApplicationListener.class);

    @Autowired
    public void setEventHolderBean(EventHolderBean eventHolderBean) {
        this.eventHolderBean = eventHolderBean;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("Event Fired");
        eventHolderBean.setEventFired(true);
    }
}
