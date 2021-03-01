package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Feedback {

    private String Email;
    private String Name;
    private String Feedback;

    public Feedback(){

    }

    public Feedback(String Email, String Name, String Feedback) {
        this.Email = Email;
        this.Name = Name;
        this.Feedback = Feedback;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Email", Email);
        result.put("Name", Name);
        result.put("Feedback", Feedback);

        return result;
    }
}
