package com.mobithink.cyclobase.gopro;


import android.content.Context;
import android.util.Log;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoProController implements Callback<String> {

    private final Context context;
    private final GoProAPI goProAPI;
    private final OnTaskCompleted listener;


    public GoProController(Context c, OnTaskCompleted listener) {
        context=c;
        Retrofit client =  GoProApiClient.getClient();
        goProAPI=client.create(GoProAPI.class);
        this.listener=listener;
    }

    public void startTakePhoto(){
        Call<String> call = goProAPI.takePhoto();
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            listener.onTaskCompleted("photo taken");
        }
        else {
            listener.onTaskCompleted("upps");
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Request request = call.request();
        Log.e(this.getClass().getName(),"error");
    }
}
