package com.mobithink.cyclobase.webservices;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jpaput on 10/02/2017.
 */

public interface TechnicalService {

    @GET("/mobithink/velo/server/wakeup")
    Call<Void> checkStatus();
}
