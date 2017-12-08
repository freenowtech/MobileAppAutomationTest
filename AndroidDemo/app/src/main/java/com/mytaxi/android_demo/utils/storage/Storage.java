package com.mytaxi.android_demo.utils.storage;

import com.mytaxi.android_demo.models.User;

public interface Storage {

    User loadUser();

    void saveUser(User user);

    void resetUser();

}
