package com.yoryo.assignment.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author Sagar
 *
 */
public final class JsonConverter {
    
    public static final String createJson(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
    
    public final static Object fromJson(String json, Class<?> type) {
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, type);
        return obj;
    }
    
    public final static Object getListFromJson(String json, Type listType) {
        Gson gson = new Gson();
        //Type listType = new TypeToken<List<className>>() {}.getType();
        Object obj = gson.fromJson(json, listType);
        return obj;
    }
    
    public final static Object parseJsonWithDateFormat(String json, Class<?> type, String dateFormat) {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
        Object obj = gson.fromJson(json, type);
        return obj;
    }
}
