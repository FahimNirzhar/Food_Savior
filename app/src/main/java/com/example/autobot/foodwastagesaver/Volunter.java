package com.example.autobot.foodwastagesaver;

/**
 * Created by Autobot on 8/3/2017.
 */

public class Volunter {
    private String Name;
    private String Phone;
    private String Address;
    private String KeyValue;
    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Volunter(String name, String phone, String address, String keyValue, String email) {
        Name = name;
        Phone = phone;
        Address = address;
        KeyValue = keyValue;
        Email = email;
    }



    public Volunter()
    {

    }

    public String getKeyValue() {
        return KeyValue;
    }

    public void setKeyValue(String keyValue) {
        KeyValue = keyValue;
    }

    public Volunter(String name, String phone, String address, String keyValue) {
        Name = name;
        Phone = phone;
        Address = address;
        KeyValue = keyValue;
    }

    public Volunter (String Name, String Phone, String Address){
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
