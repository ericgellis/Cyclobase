package com.mobithink.cyclobase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseOpenHelper extends SQLiteOpenHelper {

    // Table Names
    public static final String TABLE_TRIP = "trips";
    public static final String TABLE_STATION_TRIP_DATA = "stationTripDatas";
    public static final String TABLE_EVENT = "events";
    public static final String TABLE_STATUS = "status";
    public static final String TABLE_ROLLING_POINT = "rollingPoints";
    // Common column names
    public static final String KEY_ID = "_id";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_END_LONGITUDE = "end_longitude";
    public static final String KEY_END_LATITUDE = "end_latitude";
    public static final String KEY_CREATION_DATE = "creationDate";
    public static final String KEY_TRIP_ID = "trip_id";
    public static final String KEY_START_DATETIME = "startDatetime";
    public static final String KEY_END_DATETIME = "endDatetime";
    public static final String KEY_STEP = "step";
    // STATION Table - column names
    public static final String KEY_STATION_NAME = "stationName";
    // TRIP Table - column names
    public static final String KEY_TRIP_NAME = "tripName";
    // STATION_TRIP_DATA Table - column names
    public static final String KEY_COME_IN = "comeIn";
    public static final String KEY_GO_OUT = "goOut";
    // EVENT Table - column names
    public static final String KEY_EVENT_NAME = "name";
    public static final String KEY_STATION_DATA_NAME = "stationData_name";
    public static final String KEY_VOICE_MEMO = "voice_memo";
    public static final String KEY_PICTURE = "picture";
    public static final String KEY_EVENT_TYPE = "eventType";

    //STATUS Table - column names
    // Cette table permet de savoir si le TRIP a était envoyé a le serveur.

    public static final String KEY_STATUS ="status";

    // Create table TRIP
    private static final String CREATE_TABLE_TRIP =
            "CREATE TABLE " + TABLE_TRIP
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + KEY_TRIP_NAME + " TEXT,"
                    + KEY_LATITUDE + " DOUBLE_PRECISION,"
                    + KEY_LONGITUDE + " DOUBLE_PRECISION,"
                    + KEY_END_LATITUDE + " DOUBLE_PRECISION,"
                    + KEY_END_LONGITUDE + " DOUBLE_PRECISION,"
                    + KEY_START_DATETIME + " DATETIME,"
                    + KEY_END_DATETIME + " DATETIME" + ")";

    // Create table ROLLING_POINT
    private static final String CREATE_TABLE_ROLLING_POINT =
            "CREATE TABLE " + TABLE_ROLLING_POINT
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + KEY_CREATION_DATE + " DATETIME,"
                    + KEY_LATITUDE + " DOUBLE_PRECISION,"
                    + KEY_LONGITUDE + " DOUBLE_PRECISION,"
                    + KEY_TRIP_ID + " INTEGER" + ")";

    // Create table EVENT
    private static final String CREATE_TABLE_EVENT =
            "CREATE TABLE " + TABLE_EVENT
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + KEY_EVENT_NAME + " TEXT,"
                    + KEY_START_DATETIME + " DATETIME,"
                    + KEY_END_DATETIME + " DATETIME,"
                    + KEY_LATITUDE + " DOUBLE_PRECISION,"
                    + KEY_LONGITUDE + " DOUBLE_PRECISION,"
                    + KEY_END_LATITUDE + " DOUBLE_PRECISION,"
                    + KEY_END_LONGITUDE + " DOUBLE_PRECISION,"
                    + KEY_VOICE_MEMO + " TEXT,"
                    + KEY_PICTURE + " TEXT,"
                    + KEY_EVENT_TYPE + " TEXT,"
                    + KEY_TRIP_ID + " INTEGER" + ")";

    // Create table STATUS
    private static final String CREATE_TABLE_STATUS =
            "CREATE TABLE " + TABLE_STATUS
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + KEY_TRIP_NAME + " TEXT,"
                    + KEY_STATUS + " INTEGER"
                    + ")";


    // Database Version
    private static final int DATABASE_VERSION = 2;
    private static final int DATABASE_OLD_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mobithink.db";


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        updateDatabase(db, DATABASE_OLD_VERSION, DATABASE_VERSION);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop Old Table
        switch (oldVersion){
            case 1:
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLLING_POINT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
                break;

            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLLING_POINT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
                break;

            default:
                 db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
                 db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLLING_POINT);
                 db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
                 db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
                 break;
        }
        //Create new
        switch (newVersion){
            case 1:
                db.execSQL(CREATE_TABLE_TRIP);
                db.execSQL(CREATE_TABLE_ROLLING_POINT);
                db.execSQL(CREATE_TABLE_EVENT);
                break;

            case 2:
                db.execSQL(CREATE_TABLE_TRIP);
                db.execSQL(CREATE_TABLE_ROLLING_POINT);
                db.execSQL(CREATE_TABLE_EVENT);
                db.execSQL(CREATE_TABLE_STATUS);
                break;
            default:
                db.execSQL(CREATE_TABLE_TRIP);
                db.execSQL(CREATE_TABLE_ROLLING_POINT);
                db.execSQL(CREATE_TABLE_EVENT);
                db.execSQL(CREATE_TABLE_STATUS);
                break;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);

    }


}
