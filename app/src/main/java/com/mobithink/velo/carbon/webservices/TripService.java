package com.mobithink.velo.carbon.webservices;

import com.mobithink.velo.carbon.database.model.TripDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TripService {

    @POST("/mobithink/trip/create")
    Call<Void> register(@Body TripDTO tripDTO);
}
