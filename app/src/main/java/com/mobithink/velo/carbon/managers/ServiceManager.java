package com.mobithink.velo.carbon.managers;

/**
 * Created by jpaput on 06/02/2017.
 */

public class ServiceManager {

    private static ServiceManager mInstance;

    public static ServiceManager getInstance() {
        if (mInstance == null)
        {
            mInstance = new ServiceManager();
        }
        return mInstance;
    }
}
