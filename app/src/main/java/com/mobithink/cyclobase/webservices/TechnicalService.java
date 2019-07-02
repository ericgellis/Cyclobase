package com.mobithink.cyclobase.webservices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by jpaput on 10/02/2017.
 */

public interface TechnicalService {

    @POST("/technical/wakeup")
    Call<Void> checkStatus();
}
