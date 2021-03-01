package com.example.life.buildul.app;

public class OrderReturn {
    //private String Uid;
    private String imageUrl;
    private String quantity;
    private String Name;
    private String totalPrice;

    public OrderReturn(){

    }

    public OrderReturn(String imageUrl, String quantity, String Name, String totalPrice) {
        //this.Uid = Uid;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.Name = Name;
        this.totalPrice = totalPrice;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

}
