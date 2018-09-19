package com.mytaxi.android_demo.utils.idle_resources;

public class LoginIdleManager {
    private static final String RESOURCE = "GLOBAL";

    private static LoginIdlingResource idlingResource = new LoginIdlingResource();

    public static void setIdle(boolean idle) {
        idlingResource.setIdle(idle);
    }

    public static LoginIdlingResource getIdlingResource() {
        return idlingResource;
    }
}
