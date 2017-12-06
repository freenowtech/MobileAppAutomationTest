package com.mytaxi.android_demo.models;

public class User {

    private String mUsername;
    private String mSalt;
    private String mSHA256;

    public User(String username, String salt, String sha256) {
        mUsername = username;
        mSalt = salt;
        mSHA256 = sha256;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getSalt() {
        return mSalt;
    }

    public String getSHA256() {
        return mSHA256;
    }

    public boolean match(String username, String sha256) {
        return (mUsername.equals(username) && mSHA256.equals(sha256));
    }

}
