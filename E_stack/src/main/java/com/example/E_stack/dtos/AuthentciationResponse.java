package com.example.E_stack.dtos;

public class AuthentciationResponse {
    private String jwtToken;

    public AuthentciationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
