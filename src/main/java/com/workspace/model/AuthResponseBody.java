package com.workspace.model;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthResponseBody {
    private String accessToken;
    private User user;

    public AuthResponseBody() {
    }

    public AuthResponseBody(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}