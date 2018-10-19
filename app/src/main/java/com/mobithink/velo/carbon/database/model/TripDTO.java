package com.mobithink.velo.carbon.database.model;

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
}
