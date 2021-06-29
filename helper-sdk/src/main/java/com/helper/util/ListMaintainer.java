package com.helper.util;


import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ListMaintainer {

    private static final String KEY_DEFAULT = "default";

    /**
     * @param item : list item
     */
    public static <T> void saveData(Context context, T item) {
        saveData(context, KEY_DEFAULT, item);
    }

    /**
     * @param key  : unique key for save data
     * @param item : list item
     */
    public static <T> void saveData(Context context, String key, T item) {
        String previousData = updateList(context, key, item);
        if (!TextUtils.isEmpty(previousData)) {
            BasePrefUtil.setRecentFeatureData(context, key, previousData);
        }
    }

    /**
     * @param listSize : list size
     * @param item     : list item
     * @param title    : for unique check
     */
    public static <T> void saveData(Context context, int listSize, T item, String title) {
        saveData(context, KEY_DEFAULT, listSize, item, title);
    }

    /**
     * @param key      : unique key for save data
     * @param listSize : list size
     * @param item     : list item
     * @param title    : for unique check
     */
    public static <T> void saveData(Context context, String key, int listSize, T item, String title) {
        String previousData = updateRecentList(context, key, listSize, item, title);
        if (!TextUtils.isEmpty(previousData)) {
            BasePrefUtil.setRecentFeatureData(context, key, previousData);
        }
    }

    /**
     * @param typeCast : new TypeToken<List<ModelName>>() {} or  new TypeToken<ArrayList<ModelName>>() {}
     */
    @Nullable
    public static <T> T getData(Context context, TypeToken<T> typeCast) {
        return getData(context, KEY_DEFAULT, typeCast);
    }

    @Nullable
    public static <T> T getData(Context context, String key, TypeToken<T> typeCast) {
        return GsonParser.fromJson(BasePrefUtil.getRecentFeatureData(context, key), typeCast);
    }

    @MainThread
    public static String getData(Context context) {
        return getData(context, KEY_DEFAULT);
    }

    @MainThread
    public static String getData(Context context, String key) {
        return BasePrefUtil.getRecentFeatureData(context, key);
    }

    @MainThread
    public static void clear(Context context) {
        clear(context, KEY_DEFAULT);
    }

    @MainThread
    public static void clear(Context context, String key) {
        BasePrefUtil.setRecentFeatureData(context, key, "");
    }


    private static <T> String updateList(Context context, String key, T item) {
        List<T> value = GsonParser.fromJson((BasePrefUtil.getRecentFeatureData(context, key)), new TypeToken<List<T>>() {
        });
        if (value == null) {
            value = new ArrayList<>();
        }
        boolean isItemExist = false;
        for (T listItem : value) {
            if (listItem == item) {
                isItemExist = true;
                break;
            }
        }
        if (!isItemExist) {
            value.add(item);
        }
        if (value.size() == 0) {
            return "";
        } else {
            return GsonParser.toJson(value, new TypeToken<List<T>>() {
            });
        }
    }

    private static <T> String updateRecentList(Context context, String key, int listSize, T item, String title) {
        List<T> value = GsonParser.fromJson((BasePrefUtil.getRecentFeatureData(context, key)), new TypeToken<List<T>>() {
        });
        if (value == null) {
            value = new ArrayList<>();
        }
        if (title != null)
            for (T mItem : value) {
                if (mItem.toString().contains(title)) {
                    value.remove(mItem);
                    break;
                }
            }
        value.add(0, item);
        if (value.size() == 0) {
            return "";
        } else {
            if (value.size() > listSize) {
                return GsonParser.toJson(value.subList(0, listSize), new TypeToken<List<T>>() {
                });
            } else {
                return GsonParser.toJson(value, new TypeToken<List<T>>() {
                });
            }
        }
    }

    /**
     * @param key  : unique key for save data
     * @param list : input List or ArrayList
     */
    public static <T> void saveList(Context context, String key, List<T> list) {
        if (context != null && list != null && list.size() > 0) {
            String previousData = GsonParser.toJson(list, new TypeToken<List<T>>() {
            });
            if (!TextUtils.isEmpty(previousData)) {
                BasePrefUtil.setRecentFeatureData(context, key, previousData);
            }
        }
    }

    /**
     * @param key  : unique key for save data
     * @param hashMap : input HashMap
     */
    public static <T> void saveList(Context context, String key, T hashMap) {
        if (context != null && hashMap != null) {
            String previousData = GsonParser.toJson(hashMap, new TypeToken<T>() {
            });
            if (!TextUtils.isEmpty(previousData)) {
                BasePrefUtil.setRecentFeatureData(context, key, previousData);
            }
        }
    }

    /**
     * @param typeCast : new TypeToken<List<ModelName>>() {}
     *                 or  new TypeToken<ArrayList<ModelName>>() {}
     *                 or new TypeToken<HashMap<Integer, PropertyModel>>() {}
     */
    @Nullable
    public static <T> T getList(Context context, String key, TypeToken<T> typeCast) {
        return GsonParser.fromJson(BasePrefUtil.getRecentFeatureData(context, key), typeCast);
    }

}
