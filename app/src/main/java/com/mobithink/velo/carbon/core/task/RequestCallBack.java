package com.mobithink.velo.carbon.core.task;

import com.mobithink.velo.carbon.core.model.ErrorMessage;

public abstract class RequestCallBack<Result> {

    public abstract void onSuccess(Result result);

    public void onError(ErrorMessage errorMessage) { }


}
