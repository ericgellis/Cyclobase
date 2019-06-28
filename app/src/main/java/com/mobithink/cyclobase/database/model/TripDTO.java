package com.mobithink.cyclobase.database.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jpaput on 07/02/2017.
 */

public class TripDTO implements Serializable {

    public Long id;

    public String tripName;

    public Long startTime;

    public Long endTime;

    public List<RollingPointDTO> rollingPoints;

    public List<EventDTO> events;

    private double endGpsLat;

    private double endGpsLong;

    private double startGpsLat;

    private double startGpsLong;

    public TripDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
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

    public List<RollingPointDTO> getRollingPoints() {
        return rollingPoints;
    }

    public void setRollingPoints(List<RollingPointDTO> rollingPoints) {
        this.rollingPoints = rollingPoints;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", tripName='" + tripName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rollingPoints=" + rollingPoints +
                ", events=" + events +
                '}';
    }

    public void setEndLatitude(double latitude) {
        this.endGpsLat = latitude;

    }

    public void setEndLongitude(double longitude) {
        this.endGpsLong = longitude;
    }

    public void setStartLatitude(double latitude) {
        this.startGpsLat = latitude;

    }

    public void setStartLongitude(double longitude) {
        this.startGpsLong = longitude;
    }

    public Double getStartLatitude() {return startGpsLat;}

    public Double getStartLongitude() {return startGpsLong;}

    public Double getEndLatitude() {return endGpsLat;}

    public Double getEndLongitude() {return endGpsLong;}



}
