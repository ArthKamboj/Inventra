package com.inventra.Dto;

import lombok.Getter;
import lombok.Setter;


public class AuthResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
