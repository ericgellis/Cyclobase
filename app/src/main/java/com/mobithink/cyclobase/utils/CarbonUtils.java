package com.mobithink.cyclobase.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import com.mobithink.cyclobase.CyclobaseApplication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jpaput on 09/02/2017.
 */

public class CarbonUtils {

    public static int dpToPx(float valueInDp) {
        DisplayMetrics metrics = CyclobaseApplication.getInstance().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private int pxToDp(int px){
        return Math.round(px/(CyclobaseApplication.getInstance().getResources().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
    }


    static void callHTTP(final Context context){
        final OkHttpClient client = new OkHttpClient();

        final Request startpreview = new Request.Builder()
                .url(HttpUrl.get(URI.create("http://10.5.5.9/gp/gpControl/execute?p1=gpStream&a1=proto_v2&c1=restart")))
                .build();

        client.newCall(startpreview).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context,"Camera not connected!", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(context,"Camera not connected!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    static void sendcommand(final String StrUrl)
    {
        //  log.setText(StrUrl);
        (new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(StrUrl);
                    java.net.URLConnection con = url.openConnection();
                    con.connect();
                    java.io.BufferedReader in =new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        })).start();
    }

}
