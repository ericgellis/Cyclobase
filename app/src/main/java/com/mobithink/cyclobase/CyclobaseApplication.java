package com.mobithink.cyclobase;

import android.app.Application;

import com.mobithink.cyclobase.managers.CarbonApplicationManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by jpaput on 06/02/2017.
 */

public class CyclobaseApplication extends Application {

    // Singleton instance
    private static CyclobaseApplication mInstance = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        CarbonApplicationManager.getInstance().init();

    }

    public static CyclobaseApplication getInstance() {
        return mInstance ;
    }

    @Override
    public void onTerminate() {
        CarbonApplicationManager.getInstance().clearApp();
        super.onTerminate();
    }
}
