package com.mytaxi.android_demo.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserCredentials {

    public static Map<String, String> getUderCreds(){
        Map<String,String> userCreds = new HashMap<>();
        String inline = "";
        try{
            URL url = new URL("https://randomuser.me/api/?seed=a1f30d446f820665");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int resCode = conn.getResponseCode();
            if(resCode != 200)
                throw new RuntimeException("HttpResponseCode: " +resCode);
            else
                {
                    Scanner sc = new Scanner(url.openStream());
                    while(sc.hasNext())
                    {
                        inline+=sc.nextLine();
                    }
                }

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(inline);
            JsonArray results = jsonObject.getAsJsonArray("results");

            for(int i = 0; i<results.size(); i++){
                JsonObject des = (JsonObject) results.get(i);
                JsonObject loginDetails = (JsonObject) des.get("login");
                userCreds.put("username", loginDetails.get("username").toString().replaceAll("^\"|\"$", ""));
                userCreds.put("password", loginDetails.get("password").toString().replaceAll("^\"|\"$", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userCreds;
    }
}
