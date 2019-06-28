package com.mobithink.cyclobase.webservices;

import com.mobithink.cyclobase.database.model.TripDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TripService {

    @POST("/trips")
    Call<Void> register(@Body TripDTO tripDTO);
}
