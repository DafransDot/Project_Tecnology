package com.example.project_tecnology.model;

import android.service.autofill.UserData;

import com.example.project_tecnology.model.login.LoginData;

public class userData {
    private LoginData loginData;

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }
}
