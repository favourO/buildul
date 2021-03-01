package com.example.life.buildul.app;

public class User {

    private String Address;
    private String City;
    private String Email;
    private String Firstname;
    private String Lastname;
    private String Phone;
    private String Postalcode;
    private String State;

    public User(){

    }

    public User(String Address, String City, String Email, String Firstname, String Lastname, String Phone, String Postalcode, String State) {
        this.Address = Address;
        this.City = City;
        this.Email = Email;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.Phone = Phone;
        this.Postalcode = Postalcode;
        this.State = State;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String Postalcode) {
        this.Postalcode = Postalcode;
    }
}
