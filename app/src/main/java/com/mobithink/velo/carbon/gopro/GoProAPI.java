package com.mobithink.velo.carbon.gopro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoProAPI {


    String TRIGGER_PATH ="command/shutter?p=1";

    @GET(TRIGGER_PATH)
    Call<String> takePhoto();

}
