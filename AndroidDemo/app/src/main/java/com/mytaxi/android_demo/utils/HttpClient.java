package com.mytaxi.android_demo.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mytaxi.android_demo.models.Driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.mytaxi.android_demo.misc.Constants.LOG_TAG;
import static com.mytaxi.android_demo.misc.Constants.SOCKET_TIMEOUT;

public class HttpClient {
    private static final OkHttpClient mClient = new OkHttpClient.Builder().readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS).build();
    private static final JsonParser mJsonParser = new JsonParser();
    private static final String RANDOM_USER_URL = "https://randomuser.me/api/";

    public static void fetchDrivers(final DriverCallback driverCallback) {
        int amount = 256;
        String seed = "23f8827e04239990";
        String url = RANDOM_USER_URL + "?results=" + amount + "&seed=" + seed;
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    ArrayList<Driver> drivers = getDrivers(responseBody.string());
                    Log.i(LOG_TAG, "Fetched successfully " + drivers.size() + " drivers.");
                    driverCallback.setDrivers(drivers);
                    driverCallback.run();
                }
            }
        });
    }

    private static ArrayList<Driver> getDrivers(String jsonResponse) {
        JsonObject jsonObject = mJsonParser.parse(jsonResponse).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");
        ArrayList<Driver> drivers = new ArrayList<>();
        for (JsonElement jsonElement :results) {
            JsonObject jsonUser = jsonElement.getAsJsonObject();
            JsonObject name = jsonUser.getAsJsonObject("name");
            String firstName = name.get("first").getAsString();
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            String lastName = name.get("last").getAsString();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            String fullName = firstName + " " + lastName;
            String phone = jsonUser.get("cell").getAsString();
            JsonObject picture = jsonUser.getAsJsonObject("picture");
            String avatar = picture.get("large").getAsString();
            JsonObject location = jsonUser.getAsJsonObject("location");
            String street = location.get("street").getAsString();
            drivers.add(new Driver(fullName, phone, avatar, street));
        }
        return drivers;
    }

    public abstract static class DriverCallback implements Runnable {
        protected ArrayList<Driver> mDrivers;

        void setDrivers(ArrayList<Driver> drivers) {
            mDrivers = drivers;
        }
    }
}
