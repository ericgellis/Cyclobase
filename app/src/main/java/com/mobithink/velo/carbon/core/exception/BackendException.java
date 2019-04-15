package com.mobithink.velo.carbon.core.exception;

public class BackendException extends Exception {


    private String errorMessage;
    private int code;

    public BackendException(int code, String error) {
        super(error);
        this.errorMessage = error;
        this.code = code;
    }

    public int getBackendErrorCode() {
        return code;
    }


    public String getBackendErrorMessage() {
        return errorMessage;
    }
}