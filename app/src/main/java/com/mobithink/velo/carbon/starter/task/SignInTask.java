package com.mobithink.velo.carbon.starter.task;

import com.mobithink.velo.carbon.core.exception.BackendException;
import com.mobithink.velo.carbon.core.model.DefaultResponse;
import com.mobithink.velo.carbon.core.task.AbstractTask;

import java.io.IOException;

public class SignInTask extends AbstractTask<DefaultResponse> {


    public SignInTask(String email, String password) {
    }

    @Override
    protected DefaultResponse doCall() throws IOException, BackendException, InterruptedException {
        return null;
    }
}
