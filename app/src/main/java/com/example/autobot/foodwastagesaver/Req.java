package com.example.autobot.foodwastagesaver;

/**
 * Created by NirZhar on 10/4/2017.
 */

public class Req {
    private String name;
    private String Email;
    private String address;
    private String msg;

    public Req(String name, String email, String address, String msg) {
        this.name = name;
        Email = email;
        this.address = address;
        this.msg = msg;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Req() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
