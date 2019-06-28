package com.mobithink.cyclobase.database.model;

import java.io.Serializable;


public class RollingPointDTO implements Serializable {

    private Long id;
    private Long tripId;
    private Long timeOfRollingPoint;
    private Double gpsLat;
    private Double gpsLong;

    public RollingPointDTO() {
    }

    public RollingPointDTO(Long id, Long tripId, Long timeOfRollingPoint, Double gpsLat, Double gpsLong, int trafficIndex, Double speed) {
        this.id = id;
        this.tripId = tripId;
        this.timeOfRollingPoint = timeOfRollingPoint;
        this.gpsLat = gpsLat;
        this.gpsLong = gpsLong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getTimeOfRollingPoint() {
        return timeOfRollingPoint;
    }

    public void setTimeOfRollingPoint(Long timeOfRollingPoint) {
        this.timeOfRollingPoint = timeOfRollingPoint;
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
}
