package com.helper.stats;

import com.google.gson.reflect.TypeToken;
import com.helper.util.GsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatsJsonCreator {

    public static String toJson(StatisticsModel statisticsModels) {
        return GsonParser.toJson(statisticsModels, new TypeToken<StatisticsModel>() {});
    }
    public static StatisticsModel fromJson(String jsonValue) {
        return GsonParser.fromJson(jsonValue, new TypeToken<StatisticsModel>() {});
    }
//    public static String toJson(StatisticsModel statisticsModels) {
//        try {
//            // In this case we need a json array to hold the java list
//            JSONArray jsonArr = new JSONArray();
//            for (StatisticsModel item : statisticsModels) {
//
//                JSONObject pnObj = new JSONObject();
//                pnObj.put("id", item.getId());
//                pnObj.put("title", item.getTitle());
//                pnObj.put("level", item.getLevel());
//                jsonArr.put(pnObj);
//            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("levels", jsonArr);
//            return jsonObject.toString();
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }

}

