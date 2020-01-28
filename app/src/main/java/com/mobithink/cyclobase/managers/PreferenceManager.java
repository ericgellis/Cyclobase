package com.mobithink.cyclobase.managers;

import android.content.SharedPreferences;

import com.mobithink.cyclobase.CyclobaseApplication;

/**
 * Created by jpaput on 06/02/2017.
 */

public class PreferenceManager {

    public static final String PREFERENCE_GENERAL = "PREFERENCE_GENERAL";

    public static final String STATION_RADIUS = "STATION_RADIUS";
    public static final String TIME_INTERVAL = "TIME_INTERVAL";
    public static final String COST_PRODUCTION = "COST_PRODUCTION";

    public static final String USER_NAME = "USER_NAME";


    private static PreferenceManager mInstance;

    private SharedPreferences mGeneralPreference;

    private PreferenceManager() {
        mGeneralPreference = CyclobaseApplication.getInstance().getSharedPreferences(PREFERENCE_GENERAL, 0);
    }

    public static PreferenceManager getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new PreferenceManager();
        }
        return mInstance;
    }

    public void setDataString(String key, String value){
        SharedPreferences.Editor editor = mGeneralPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getDataString(String key){
        return mGeneralPreference.getString(key, null);
    }

    public void setStationRadius(int value){
        SharedPreferences.Editor editor = mGeneralPreference.edit();
        editor.putInt(STATION_RADIUS, value);
        editor.apply();
    }

    public int getStationRadius(){
        return mGeneralPreference.getInt(STATION_RADIUS, 50);
    }


    public void setTimeFrequency(int value){
        SharedPreferences.Editor editor = mGeneralPreference.edit();
        editor.putInt(TIME_INTERVAL, value);
        editor.apply();
    }

    public int getTimeFrequency(){
        return mGeneralPreference.getInt(TIME_INTERVAL, 5);
    }

    public void setCostOfProductionByMinute(int value){
        SharedPreferences.Editor editor = mGeneralPreference.edit();
        editor.putInt(COST_PRODUCTION, value);
        editor.apply();
    }

    public int getCostOfProductionByMinute(){
        return mGeneralPreference.getInt(COST_PRODUCTION, 1);
    }

    public void setCostOfProductionByKilometer(int value){
        SharedPreferences.Editor editor = mGeneralPreference.edit();
        editor.putInt(COST_PRODUCTION, value);
        editor.apply();
    }

    public int getCostOfProductionByKilometer(){
        return mGeneralPreference.getInt(COST_PRODUCTION, 1);
    }

    public void setUserName(String userName){
        SharedPreferences.Editor editor = mGeneralPreference.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public String getUserName(){
        return mGeneralPreference.getString(USER_NAME, "");
    }


    public void clear(){
        mGeneralPreference.edit().clear().commit();
    }
}
