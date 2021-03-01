package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ReturnCart {

    private String uid;
    //private String date;
    private String description;
    private String price;
    private String quantity;
    private String title;
    private String totalPrice;
    private String imageUrl;
    //private Map<String, String> timeStamp;

    public ReturnCart(){

    }

    public ReturnCart(String uid, String description, String price, String quantity, String title, String totalPrice, String imageUrl/*, Map<String, String> timeStamp*/) {
        this.uid = uid;
        //this.date = date;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        //this.timeStamp = timeStamp;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        //result.put("date", date);
        result.put("description", description);
        result.put("price", price);
        result.put("quantity", quantity);
        result.put("title", title);
        result.put("totalPrice", totalPrice);
        result.put("imageUrl", imageUrl);
        //result.put("timeStamp", timeStamp);
        return result;
    }
}

