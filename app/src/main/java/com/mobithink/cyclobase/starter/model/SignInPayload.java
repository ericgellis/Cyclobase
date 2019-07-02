package com.mobithink.cyclobase.starter.model;

public class SignInPayload {

    public SignInPayload(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String login;

    public String password;

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
