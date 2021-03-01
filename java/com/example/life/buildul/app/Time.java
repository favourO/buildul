package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Time {

    private Map<String, String> timeStamp;

    public Time(){

    }

    public Time(Map timeStamp){
        this.timeStamp = timeStamp;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeStamp", timeStamp);

        return result;
    }
}
