package com.mytaxi.android_demo.models;

public class Driver {
    private String mName;
    private String mPhone;

    public Driver(String name, String phone) {
        this.mName = name;
        this.mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }
}
