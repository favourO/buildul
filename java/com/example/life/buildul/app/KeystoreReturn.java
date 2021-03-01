package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class KeystoreReturn {

    private String keyString;
    private String totalprice;

    public KeystoreReturn() {

    }

    public KeystoreReturn(String keyString, String totalprice){
         this.keyString = keyString;
         this.totalprice = totalprice;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getTotalprice() {
        return totalprice;
    }
}

