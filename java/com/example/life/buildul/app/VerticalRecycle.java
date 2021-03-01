package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class VerticalRecycle {
    public String title;
    public String image;
    public String price;
    public String description;

    public VerticalRecycle() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }
    public VerticalRecycle(String title, String image, String price, String description) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setProductdescription(String description) {
        this.description = description;
    }

    public String getProductdescription() {
        return description;
    }

    /*@Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("image", image);
        result.put("price", price);

        return result;
    }*/
}
