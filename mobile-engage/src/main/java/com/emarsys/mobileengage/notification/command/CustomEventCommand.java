package com.emarsys.mobileengage.notification.command;

import com.emarsys.core.util.Assert;
import com.emarsys.mobileengage.EventServiceInternal;

import java.util.Map;

public class CustomEventCommand implements Runnable {

    private final EventServiceInternal eventServiceInternal;
    private String eventName;
    private Map<String, String> eventAttributes;

    public CustomEventCommand(EventServiceInternal eventServiceInternal, String eventName, Map<String, String> eventAttributes) {
        Assert.notNull(eventServiceInternal, "EventServiceInternal must not be null!");
        Assert.notNull(eventName, "EventName must not be null!");
        this.eventServiceInternal = eventServiceInternal;
        this.eventName = eventName;
        this.eventAttributes = eventAttributes;
    }

    @Override
    public void run() {
        eventServiceInternal.trackCustomEvent(eventName, eventAttributes, null);
    }

    public String getEventName() {
        return eventName;
    }

    public Map<String, String> getEventAttributes() {
        return eventAttributes;
    }
}
