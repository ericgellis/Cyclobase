package com.mobithink.cyclobase.starter.task;

import com.mobithink.cyclobase.core.exception.BackendException;
import com.mobithink.cyclobase.core.model.DefaultResponse;
import com.mobithink.cyclobase.core.task.AbstractTask;

import java.io.IOException;

public class SignInTask extends AbstractTask<DefaultResponse> {


    public SignInTask(String email, String password) {
    }

    @Override
    protected DefaultResponse doCall() throws IOException, BackendException, InterruptedException {
        return null;
    }
}
