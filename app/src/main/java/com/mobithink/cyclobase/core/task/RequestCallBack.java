package com.mobithink.cyclobase.core.task;

import com.mobithink.cyclobase.core.model.ErrorMessage;

public abstract class RequestCallBack<Result> {

    public abstract void onSuccess(Result result);

    public void onError(ErrorMessage errorMessage) { }


}
