package com.mobithink.cyclobase.starter.model;

public class SignInPayload {

    public SignInPayload(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String userName;

    public String password;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
