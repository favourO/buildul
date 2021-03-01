package com.example.life.buildul.app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String Uid;
    private String contactName;
    private String mobile;
    private String Email;
    private String street;
    private String country;
    private String city;
    private String zip;
    private String State;
    private String payStackTransactionDetails;
    private String quantity;
    private String Name;
    private String totalPrice;
    private String imageUrl;

    public Order(){

    }

    public Order(String Uid, String contactName, String mobile, String Email, String street, String country, String city, String zip, String State, String payStackTransactionDetails, String quantity, String Name, String totalPrice, String imageUrl) {
        this.Uid = Uid;
        this.contactName = contactName;
        this.mobile = mobile;
        this.Email = Email;
        this.street = street;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.State = State;
        this.payStackTransactionDetails = payStackTransactionDetails;
        this.quantity = quantity;
        this.Name = Name;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
    }

    /*public void setUid(String uid) {
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setState(String state) {
        State = state;
    }

    public String getState() {
        return State;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }*/

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Uid", Uid);
        result.put("contactName", contactName);
        result.put("mobile", mobile);
        result.put("Email", Email);
        result.put("street", street);
        result.put("country", country);
        result.put("city", city);
        result.put("zip", zip);
        result.put("State", State);
        result.put("payStackTransactionDetails", payStackTransactionDetails);
        result.put("quantity", quantity);
        result.put("Name", Name);
        result.put("totalPrice", totalPrice);
        result.put("imageUrl", imageUrl);

        return result;
    }
}
