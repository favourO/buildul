package com.example.life.buildul.models;

public class Model {
    String title, price, Image;
    public Model(String title, String price, String image){
        this.title = title;
        this.price = title;
        this.Image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
