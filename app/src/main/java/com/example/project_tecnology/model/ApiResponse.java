package com.example.project_tecnology.model;

import com.example.project_tecnology.model.login.LoginData;

public class ApiResponse {
    private String message;
    private LoginData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}

