package com.mobithink.cyclobase.managers;

import com.mobithink.cyclobase.database.model.TripDTO;

/**
 * Created by jpaput on 06/02/2017.
 */

public class CarbonApplicationManager {

    private static CarbonApplicationManager mInstance;

    private long mCurrentTripId = -1;

    private String mCurrentStationDataName = null;

    public static CarbonApplicationManager getInstance() {

        if (mInstance == null)
        {
            mInstance = new CarbonApplicationManager();
        }
        return mInstance;

    }

    public void clearApp() {
        //TODO
    }

    public void init() {
        PreferenceManager.getInstance();
        DatabaseManager.getInstance();
        ServiceManager.getInstance();
    }

    public TripDTO getCurrentTrip(){
        return DatabaseManager.getInstance().getTrip(mCurrentTripId);
    }

    public long getCurrentTripId() {
        return mCurrentTripId;
    }

    public void setCurrentTripId(long tripId) {
        mCurrentTripId = tripId;
    }

    public String getCurrentStationDataName() {
        return mCurrentStationDataName;
    }

    public void setCurrentStationDataName(String stationDataDTOName) {
        mCurrentStationDataName = stationDataDTOName;
    }
}
