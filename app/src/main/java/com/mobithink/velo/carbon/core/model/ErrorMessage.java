package com.mobithink.velo.carbon.core.model;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {

    @SerializedName("type")
    public ErrorType type;

    public ErrorMessage(ErrorType type) {
        this.type = type;
    }
}
