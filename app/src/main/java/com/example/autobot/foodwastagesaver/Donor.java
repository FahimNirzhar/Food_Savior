package com.example.autobot.foodwastagesaver;

/**
 * Created by Autobot on 8/3/2017.
 */

public class Donor {
    private String Name;
    private String Phone;
    private String Address;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Email;

    public Donor()
    {

    }

    public Donor(String name, String phone, String address, String email) {
        Name = name;
        Phone = phone;
        Address = address;
        Email = email;
    }

    public Donor (String Name, String Phone, String Address){
        this.Name =Name;
        this.Phone = Phone;
        this.Address =Address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
