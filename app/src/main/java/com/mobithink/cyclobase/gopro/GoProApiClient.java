package com.mobithink.cyclobase.gopro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoProApiClient {

    private static final String BASE_URL = "http://10.5.5.9/gp/gpControl/";

    public static Retrofit getClient(){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
