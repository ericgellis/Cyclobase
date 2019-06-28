package com.mobithink.cyclobase.core.task;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.mobithink.cyclobase.core.exception.BackendException;
import com.mobithink.cyclobase.core.model.Data;
import com.mobithink.cyclobase.core.model.ErrorMessage;
import com.mobithink.cyclobase.core.model.ErrorType;

import java.io.IOException;


import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public abstract class AbstractTask<R extends Data> {

    private final Single<R> single;

    @SuppressWarnings("unchecked")
    public AbstractTask() {

        single = Single.create(new SingleOnSubscribe<R>() {
            @Override
            public void subscribe(SingleEmitter<R> singleEmitter) throws Exception {
                try {
                    R result = AbstractTask.this.doCall();
                    singleEmitter.onSuccess(result);
                } catch (BackendException backendException) {
                    singleEmitter.onError(backendException);
                    //singleEmitter.onBackendError(bc.getBackendErrorCode(), bc.getBackendErrorMessage());
                } catch (Exception e) {
                    singleEmitter.onError(e);
                }
            }
        });
    }

    public void execute(RequestCallBack<R> requestCallback) {
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(requestCallback));
    }

    protected abstract R doCall() throws IOException, BackendException, InterruptedException;


    @NonNull
    SingleObserver<R> getSubscriber(final RequestCallBack<R> requestCallback) {
        return new SingleObserver<R>() {

            @Override
            public void onError(Throwable e) {
                Log.e(getClass().getSimpleName(), e.getMessage(), e);

                    if (e instanceof BackendException) {
                        try{
                            requestCallback.onError(
                                    new Gson().fromJson(((BackendException) e).getBackendErrorMessage(), ErrorMessage.class));
                        }catch (Exception exeption){
                            requestCallback.onError(new ErrorMessage(ErrorType.UNEXPECTED_ERROR));
                        }
                    } else {
                        requestCallback.onError(new ErrorMessage(ErrorType.UNEXPECTED_ERROR));
                    }

            }

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onSuccess(R result) {

                requestCallback.onSuccess(result);
            }
        };
    }
}
