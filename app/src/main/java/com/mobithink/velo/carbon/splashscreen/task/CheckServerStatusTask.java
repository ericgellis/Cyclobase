package com.mobithink.velo.carbon.splashscreen.task;

import com.mobithink.velo.carbon.core.exception.BackendException;
import com.mobithink.velo.carbon.core.model.DefaultResponse;
import com.mobithink.velo.carbon.core.task.AbstractTask;

import java.io.IOException;

public class CheckServerStatusTask extends AbstractTask<DefaultResponse> {

    public CheckServerStatusTask(){
        super();
    }

    @Override
    protected DefaultResponse doCall() throws IOException, BackendException, InterruptedException {
        //TODO
        return null;
    }
}
