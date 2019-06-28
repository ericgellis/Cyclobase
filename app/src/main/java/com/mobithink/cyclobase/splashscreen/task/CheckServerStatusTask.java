package com.mobithink.cyclobase.splashscreen.task;

import com.mobithink.cyclobase.core.exception.BackendException;
import com.mobithink.cyclobase.core.model.DefaultResponse;
import com.mobithink.cyclobase.core.task.AbstractTask;

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
