package com.mytaxi.android_demo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Driver implements Parcelable {

    private String mName;
    private String mPhone;
    private String mAvatar;
    private String mLocation;

    public Driver(String name, String phone, String avatar, String location) {
        mName = name;
        mPhone = phone;
        mAvatar = avatar;
        mLocation = location;
    }

    private Driver(Parcel parcel) {
        mName = parcel.readString();
        mPhone = parcel.readString();
        mAvatar = parcel.readString();
        mLocation = parcel.readString();
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getLocation() {
        return mLocation;
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel parcel) {
            return new Driver(parcel);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mPhone);
        parcel.writeString(mAvatar);
        parcel.writeString(mLocation);
    }

}
