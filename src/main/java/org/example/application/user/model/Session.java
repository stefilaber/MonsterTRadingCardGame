package org.example.application.user.model;

public class Session {

    private String token;

    public void setToken(String username){
        token = "Basic " + username + "-mtcgToken";
    }
    public String getToken(){
        return token;
    }
}
