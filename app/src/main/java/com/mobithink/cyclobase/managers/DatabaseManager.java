package com.mobithink.cyclobase.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobithink.cyclobase.CyclobaseApplication;
import com.mobithink.cyclobase.database.DatabaseOpenHelper;
import com.mobithink.cyclobase.database.model.EventDTO;
import com.mobithink.cyclobase.database.model.RollingPointDTO;
import com.mobithink.cyclobase.database.model.TripDTO;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String TAG = "DatabaseManager";

    private static DatabaseManager mInstance;

    private static DatabaseOpenHelper mDataBase;
    private SQLiteDatabase openedDatabase;

    public static DatabaseManager getInstance() {
        if (mInstance == null) {
            mInstance = new DatabaseManager();
            mDataBase = new DatabaseOpenHelper(CyclobaseApplication.getInstance());
        }
        return mInstance;
    }

    public void open() {
        openedDatabase = mDataBase.getWritableDatabase();
    }

    private SQLiteDatabase getOpenedDatabase() {
        if (openedDatabase == null) {
            open();
        }
        return openedDatabase;
    }

    /*************** TripDTO **************/

    public void startNewTrip() {

        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.KEY_START_DATETIME, getDateTime());

        // insert row
        long tripId = getOpenedDatabase().insert(DatabaseOpenHelper.TABLE_TRIP, null, values);

        Log.i(TAG, "A new Trip have been created : id = " + tripId);

        CarbonApplicationManager.getInstance().setCurrentTripId(tripId);
    }

    public long updateTrip(long tripId, ContentValues values) {

        getOpenedDatabase().update(DatabaseOpenHelper.TABLE_TRIP, values, DatabaseOpenHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(tripId)});

        return tripId;
    }

    TripDTO getTrip(long tripId) {

        String selectQuery = "SELECT  * FROM " + DatabaseOpenHelper.TABLE_TRIP + " WHERE "
                + DatabaseOpenHelper.KEY_ID + " = " + tripId;

        Cursor c = getOpenedDatabase().rawQuery(selectQuery, null);

        if (c == null) {
            return null;
        }

        c.moveToFirst();

        TripDTO tripDTO = new TripDTO();
        tripDTO.setId(c.getLong(c.getColumnIndex(DatabaseOpenHelper.KEY_ID)));
        tripDTO.setTripName((c.getString(c.getColumnIndex(DatabaseOpenHelper.KEY_TRIP_NAME))));
        tripDTO.setStartTime(c.getLong(c.getColumnIndex(DatabaseOpenHelper.KEY_START_DATETIME)));
        tripDTO.setEndTime(c.getLong(c.getColumnIndex(DatabaseOpenHelper.KEY_END_DATETIME)));
        tripDTO.setStartLongitude(c.getDouble(c.getColumnIndex(DatabaseOpenHelper.KEY_LONGITUDE)));
        tripDTO.setStartLatitude(c.getDouble(c.getColumnIndex(DatabaseOpenHelper.KEY_LATITUDE)));
        tripDTO.setEndLongitude(c.getDouble(c.getColumnIndex(DatabaseOpenHelper.KEY_END_LONGITUDE)));
        tripDTO.setEndLatitude(c.getDouble(c.getColumnIndex(DatabaseOpenHelper.KEY_END_LATITUDE)));

        c.close();

        return tripDTO;
    }

    public TripDTO getFullTripDTODataToSend(long tripId) {

        TripDTO tripDto = getTrip(tripId);

        tripDto.rollingPoints = getRollingPointsForTripId(tripId);
        tripDto.events = getEventsForTripId(tripId);

        return tripDto;
    }


    /***************
     * Rolling Point
     *****************/

    public void registerRollingPoint(RollingPointDTO rollingPointDTO) {

        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.KEY_TRIP_ID, rollingPointDTO.getTripId());
        values.put(DatabaseOpenHelper.KEY_LONGITUDE, rollingPointDTO.getGpsLong());
        values.put(DatabaseOpenHelper.KEY_LATITUDE, rollingPointDTO.getGpsLat());
        values.put(DatabaseOpenHelper.KEY_CREATION_DATE, getDateTime());

        // insert row
        long rollingPointId = getOpenedDatabase().insert(DatabaseOpenHelper.TABLE_ROLLING_POINT, null, values);

        Log.i(TAG, "A new rollingPoint have been created : id = " + rollingPointId + " ,with longitude [" + rollingPointDTO.getGpsLong() + "] and lattitude [" + rollingPointDTO.getGpsLat() + "]");
    }

    private List<RollingPointDTO> getRollingPointsForTripId(long tripId) {

        List<RollingPointDTO> rollingPoints = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DatabaseOpenHelper.TABLE_ROLLING_POINT + " WHERE "
                + DatabaseOpenHelper.KEY_TRIP_ID + " = " + tripId;

        Cursor cursor = getOpenedDatabase().rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RollingPointDTO rp = new RollingPointDTO();

                rp.setTripId(tripId);
                rp.setGpsLong(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.KEY_LONGITUDE)));
                rp.setGpsLat(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.KEY_LATITUDE)));
                rp.setTimeOfRollingPoint(cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.KEY_CREATION_DATE)));

                rollingPoints.add(rp);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return rollingPoints;
    }


    /***************************
     * Events
     ****************************/

    private List<EventDTO> getEventsForTripId(long tripId) {

        List<EventDTO> eventDTOList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DatabaseOpenHelper.TABLE_EVENT + " WHERE "
                + DatabaseOpenHelper.KEY_TRIP_ID + " = " + tripId;

        Cursor cursor = getOpenedDatabase().rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                EventDTO ev = new EventDTO();

                ev.setname(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.KEY_EVENT_NAME)));
                ev.setStartTime(cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.KEY_START_DATETIME)));
                ev.setEndTime(cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.KEY_END_DATETIME)));
                ev.setGpsLongStart(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.KEY_LONGITUDE)));
                ev.setGpsLatStart(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.KEY_LATITUDE)));
                ev.setGpsLongEnd(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.KEY_END_LONGITUDE)));
                ev.setGpsLatEnd(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.KEY_END_LATITUDE)));
                ev.setVoiceMemo(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.KEY_VOICE_MEMO)));
                ev.setPicture(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.KEY_PICTURE)));
                ev.setEventType(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.KEY_EVENT_TYPE)));

                eventDTOList.add(ev);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return eventDTOList;
    }

    /*************************** Utils *****************************/
    private Long getDateTime() {
        return System.currentTimeMillis();
    }


    /***************************
     * EVENT
     **************************************/

    public long createNewEvent(long tripId, EventDTO eventDTO) {

        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.KEY_TRIP_ID, tripId);
        values.put(DatabaseOpenHelper.KEY_EVENT_NAME, eventDTO.getname());
        values.put(DatabaseOpenHelper.KEY_START_DATETIME, eventDTO.getStartTime());
        values.put(DatabaseOpenHelper.KEY_LATITUDE, eventDTO.getGpsLatStart());
        values.put(DatabaseOpenHelper.KEY_LONGITUDE, eventDTO.getGpsLongStart());
        values.put(DatabaseOpenHelper.KEY_END_DATETIME, eventDTO.getEndTime());
        values.put(DatabaseOpenHelper.KEY_END_LATITUDE, eventDTO.getGpsLatEnd());
        values.put(DatabaseOpenHelper.KEY_END_LONGITUDE, eventDTO.getGpsLongEnd());
        values.put(DatabaseOpenHelper.KEY_EVENT_TYPE, eventDTO.getEventType());

        long eventId = getOpenedDatabase().insert(DatabaseOpenHelper.TABLE_EVENT, null, values);
        Log.i(TAG, "An event has been created : id = " + eventId + ", for TripId " + tripId + ", with endTime " + eventDTO.getEndTime());

        return eventId;
    }

    public void updateEvent(EventDTO eventDTO) {
        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.KEY_END_DATETIME, eventDTO.getEndTime());
        values.put(DatabaseOpenHelper.KEY_END_LATITUDE, eventDTO.getGpsLatEnd());
        values.put(DatabaseOpenHelper.KEY_END_LONGITUDE, eventDTO.getGpsLongEnd());

        try {
            getOpenedDatabase().update(DatabaseOpenHelper.TABLE_EVENT, values, DatabaseOpenHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(eventDTO.getId())});
            Log.i(TAG, "A event has been updated : id = " + eventDTO.getId() + ", with endTime " + eventDTO.getEndTime());
        } catch (Exception e) {
            throw e;
        }
    }

    /* STATUS Table - column names*/

    public void updateStatus(long tripId, boolean isSent){
        ContentValues values = new ContentValues();
        if (isSent){
            values.put(DatabaseOpenHelper.KEY_STATUS,1);
        }else {
            values.put(DatabaseOpenHelper.KEY_STATUS,1);
        }

        try {
            getOpenedDatabase().update(
                    DatabaseOpenHelper.TABLE_STATUS,
                    values,
                    DatabaseOpenHelper.KEY_TRIP_NAME + " = ?",
                    new String[]{String.valueOf(tripId)});
            Log.i(TAG, "A Status has been updated : for TripId " + tripId + ", to"+ String.valueOf(isSent));
        } catch (Exception e) {
            throw e;
        }
    }
}
