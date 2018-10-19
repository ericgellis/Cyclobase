package com.mobithink.velo.carbon.database.model;

import java.io.Serializable;

public class EventDTO implements Serializable {

    private Long id;
    private String name;
    private Long startTime;
    private Long endTime;
    private Double gpsLat;
    private Double gpsLong;
    private Double gpsEndLat;
    private Double gpsEndLong;
    private String picture;
    private String voiceMemo;
    private String eventType;
    private Long tripId;

    public EventDTO() {
    }

    public EventDTO(Long id, String name, Long startTime, Long endTime, Double gpsLat, Double gpsLong, Double gpsEndLat, Double gpsEndLong, String picture, String voiceMemo, String eventType) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gpsLat = gpsLat;
        this.gpsLong = gpsLong;
        this.gpsEndLat = gpsEndLat;
        this.gpsEndLong = gpsEndLong;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public Double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(Double gpsLong) {
        this.gpsLong = gpsLong;
    }

    public Double getGpsEndLat() {return gpsEndLat;}

    public void setGpsEndLat(Double gpsEndLat) {this.gpsEndLat = gpsEndLat;}

    public Double getGpsEndLong() {return gpsEndLong;}

    public void setGpsEndLong(Double gpsEndLong) {this.gpsEndLong = gpsEndLong;}

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
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", gpsLat=" + gpsLat +
                ", gpsLong=" + gpsLong +
                ", gpsEndLat=" + gpsEndLat +
                ", gpsEndLong=" + gpsEndLong +
                ", picture='" + picture + '\'' +
                ", voiceMemo='" + voiceMemo + '\'' +
                ", eventType='" + eventType + '\'' +
                ", tripID='" + tripId + '\'' +
                '}';
    }

}
