package com.mytaxi.android_demo.utils.idle_resources;

import android.support.test.espresso.IdlingResource;

public class LoginIdlingResource implements IdlingResource {

    private boolean isIdle = true;
    private ResourceCallback callback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;

        if(callback != null && idle){
            callback.onTransitionToIdle();
        }
    }
}
