package com.example.tenderapp.entities;

public class User {

    private String username;
    private String password;
    private float portafoglio;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public float getPortafoglio() {
        return portafoglio;
    }

    public String getUsername() {
        return username;
    }

    public void setPortafoglio(float portafoglio) {
        this.portafoglio = portafoglio;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
