/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.file;

/**
 * Created by 1466811 on 11/12/2015.
 */
public class FPMEvent {
    public enum EventType {NEW, UPDATE, DELETE}

    private EventType eventType;
    private boolean programmatic;
    private String key;
    private String value;

    public FPMEvent(EventType eventType, boolean programmatic, String key, String value) {
        this.eventType = eventType;
        this.programmatic = programmatic;
        this.key = key;
        this.value = value;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public boolean isProgrammatic() {
        return programmatic;
    }

    public void setProgrammatic(boolean programmatic) {
        this.programmatic = programmatic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FPMEvent{" +
                "eventType=" + eventType +
                ", programmatic=" + programmatic +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
