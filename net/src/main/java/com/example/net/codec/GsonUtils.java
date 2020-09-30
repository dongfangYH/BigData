package com.example.net.codec;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description GsonUtils
 * @date 2020-02-29 14:52
 **/
public class GsonUtils {

    private static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new GsonExclusionStrategy())
            .create();

    public static  <T> List<T> toList(String json, Class<T> clazz) {
        return gson.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz){
        return gson.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    public static String toJson(JsonObject jsonObject){
        return gson.toJson(jsonObject);
    }

    public static Map<String,Object> getJsonMap(String jsonStr){
        return gson.fromJson(jsonStr, Map.class);
    }

    private static class GsonExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getAnnotation(ExcludeField.class) != null;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(ExcludeField.class) != null;
        }
    }
}
