package com.pb.hackathon.util;

import com.google.gson.Gson;

public class AppUtils {

    public static String toJSONString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
