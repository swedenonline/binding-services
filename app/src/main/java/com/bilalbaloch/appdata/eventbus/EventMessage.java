package com.bilalbaloch.appdata.eventbus;

/**
 * Created by bilal on 20/08/16.
 */

public class EventMessage {

    private String eventMessage;
    private int eventId;

    public static final int EVENT_CONNECTION_SERVICE_STARTED = 1;
    public static final int EVENT_CONNECTION_SERVICE_DESTROYED = 2;

    public static final int EVENT_SUBSCRIBER_SERVICE_STARTED = 3;
    public static final int EVENT_SUBSCRIBER_SERVICE_DESTROYED = 4;

    public static final int EVENT_CONNECTION_SERVICE_REQUEST_SUCCESS = 5;
    public static final int EVENT_CONNECTION_SERVICE_REQUEST_FAILED = 6;

    public EventMessage(int eventId , String eventMessage) {
        this.eventId = eventId;
        this.eventMessage = eventMessage;
    }

    public String getEventMesssage() {
        return this.eventMessage;
    }

    public int getEventId() {
        return this.eventId;
    }
}
