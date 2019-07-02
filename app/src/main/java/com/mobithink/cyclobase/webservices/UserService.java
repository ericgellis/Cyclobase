package com.mobithink.cyclobase.webservices;

import com.mobithink.cyclobase.database.model.TripDTO;
import com.mobithink.cyclobase.starter.model.SignInPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/users/auth")
    Call<Void> signIn(@Body SignInPayload tripDTO);
}
