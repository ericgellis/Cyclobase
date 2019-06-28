package com.mobithink.cyclobase.database.model;

import java.io.Serializable;

public class EventDTO implements Serializable {

    private Long id;
    private String eventName;
    private Long startTime;
    private Long endTime;
    private Double gpsLatStart;
    private Double gpsLongStart;
    private Double gpsLatEnd;
    private Double gpsLongEnd;
    private String picture;
    private String voiceMemo;
    private String eventType;
    private Long tripId;

    public EventDTO() {
    }

    public EventDTO(Long id, String name, Long startTime, Long endTime, Double gpsLatStart, Double gpsLongStart, Double gpsLatEnd, Double gpsEndLong, String picture, String voiceMemo, String eventType) {
        this.id = id;
        this.eventName = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gpsLatStart = gpsLatStart;
        this.gpsLongStart = gpsLongStart;
        this.gpsLatEnd = gpsLatEnd;
        this.gpsLongEnd = gpsEndLong;
        this.picture = picture;
        this.voiceMemo = voiceMemo;
        this.eventType = eventType;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return eventName;
    }

    public void setname(String name) {
        this.eventName = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Double getGpsLatStart() {
        return gpsLatStart;
    }

    public void setGpsLatStart(Double gpsLatStart) {
        this.gpsLatStart = gpsLatStart;
    }

    public Double getGpsLongStart() {
        return gpsLongStart;
    }

    public void setGpsLongStart(Double gpsLongStart) {
        this.gpsLongStart = gpsLongStart;
    }

    public Double getGpsLatEnd() {return gpsLatEnd;}

    public void setGpsLatEnd(Double gpsLatEnd) {this.gpsLatEnd = gpsLatEnd;}

    public Double getGpsLongEnd() {return gpsLongEnd;}

    public void setGpsLongEnd(Double gpsLongEnd) {this.gpsLongEnd = gpsLongEnd;}

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVoiceMemo() {
        return voiceMemo;
    }

    public void setVoiceMemo(String voiceMemo) {
        this.voiceMemo = voiceMemo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", gpsLatStart=" + gpsLatStart +
                ", gpsLongStart=" + gpsLongStart +
                ", gpsLatEnd=" + gpsLatEnd +
                ", gpsLongEnd=" + gpsLongEnd +
                ", picture='" + picture + '\'' +
                ", voiceMemo='" + voiceMemo + '\'' +
                ", eventType='" + eventType + '\'' +
                ", tripID='" + tripId + '\'' +
                '}';
    }

}
