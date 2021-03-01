package com.example.life.buildul.app;

public class CartControl {

    private String date;
    private String description;
    private String price;
    private String quantity;
    private String title;
    private String totalPrice;
    private String imageUrl;

    public CartControl(){

    }

    public CartControl(String date, String description, String price, String quantity, String title, String totalPrice, String imageUrl) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

