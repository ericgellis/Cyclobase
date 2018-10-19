package com.mobithink.velo.carbon;

import android.app.Application;

import com.mobithink.velo.carbon.managers.CarbonApplicationManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by jpaput on 06/02/2017.
 */

public class CarbonApplication extends Application {

    // Singleton instance
    private static CarbonApplication mInstance = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        CarbonApplicationManager.getInstance().init();

    }

    public static CarbonApplication getInstance() {
        return mInstance ;
    }

    @Override
    public void onTerminate() {
        CarbonApplicationManager.getInstance().clearApp();
        super.onTerminate();
    }
}
