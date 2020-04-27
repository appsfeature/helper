package com.helper.network;

import java.util.HashMap;
import java.util.Map;

public class ParamBuilder {

    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public ParamBuilder() {
        params = new HashMap<>();
    }

    public void put(String key, String value) {
        params.put(key, value);
    }

    public void put(String key, Object value) {
        params.put(key, value + "");
    }

    public void put(String key, int value) {
        params.put(key, value + "");
    }

    public void put(String key, float value) {
        params.put(key, value + "");
    }

    public void put(String key, double value) {
        params.put(key, value + "");
    }

    public void put(String key, long value) {
        params.put(key, value + "");
    }


}
