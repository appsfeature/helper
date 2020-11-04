package com.helper.stats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class StatsJsonCreator {

    public static String toJson(ArrayList<StatisticsModel> statisticsModels) {
        try {
            // In this case we need a json array to hold the java list
            JSONArray jsonArr = new JSONArray();
            for (StatisticsModel item : statisticsModels) {

                JSONObject pnObj = new JSONObject();
                pnObj.put("id", item.getId());
                pnObj.put("title", item.getTitle());
                pnObj.put("level", item.getLevel());
                jsonArr.put(pnObj);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("levels", jsonArr);
            return jsonObject.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}

