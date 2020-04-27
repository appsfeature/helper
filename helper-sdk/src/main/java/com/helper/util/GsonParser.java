//package com.helper.util;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//
//public class GsonParser {
//
//    private static Gson gson = new Gson();
//
//    public static Gson getGson() {
//        if(gson == null){
//            gson = new Gson();
//        }
//        return gson;
//    }
//
//    public static <T> String toJson(Object item, TypeToken<T> typeCast) {
//        String toJson = "";
//        try {
//            Gson gson = new GsonBuilder()
//                    .excludeFieldsWithoutExposeAnnotation()
//                    .serializeNulls()
//                    .create();
//            toJson = gson.toJson(item, typeCast.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return toJson;
//    }
//
//    public static <T> T fromJson(String jsonValue, TypeToken<T> typeCast) {
//        try {
//            Gson gson = new GsonBuilder()
//                    .excludeFieldsWithoutExposeAnnotation()
//                    .serializeNulls()
//                    .create();
//            return gson.fromJson(jsonValue, typeCast.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
