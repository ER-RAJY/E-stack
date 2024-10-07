package com.example.E_stack.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthentciationResponse {
    private String jwtToken;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("message")
    private String message;
    @JsonProperty("character_id")
    private Long characterId;

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
