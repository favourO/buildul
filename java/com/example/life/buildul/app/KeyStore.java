package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class KeyStore {
    private String keyString;
    private String totalprice;
    //private String time;

    public KeyStore() {

    }

    public KeyStore(String keyString, String totalprice){
        this.keyString = keyString;
        this.totalprice = totalprice;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("keyString", keyString);
        result.put("totalprice", totalprice);

        return result;
    }
}
