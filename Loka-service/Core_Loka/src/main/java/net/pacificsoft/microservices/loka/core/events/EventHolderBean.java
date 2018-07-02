package net.pacificsoft.microservices.loka.core.events;

import org.springframework.stereotype.Component;

@Component
public class EventHolderBean {
    private Boolean eventFired = false;

    public Boolean getEventFired() {
        return eventFired;
    }

    public void setEventFired(Boolean eventFired) {
        this.eventFired = eventFired;
    }
}
