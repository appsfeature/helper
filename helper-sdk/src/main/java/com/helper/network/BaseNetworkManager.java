package com.helper.network;

import com.google.gson.JsonSyntaxException;
import com.helper.model.base.BaseDataModel;
import com.helper.util.BaseConstants;
import com.helper.util.BaseUtil;
import com.helper.util.GsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class BaseNetworkManager {
    public static String keyImagePath = BaseConstants.DEFAULT_KEY_IMAGE_PATH;
    public static String keyPdfPath = BaseConstants.DEFAULT_KEY_PDF_PATH;

    public interface ParserConfigData<T> {
        default void onSuccess(T t) {}
        default void onSuccess(T t, String imagePath, String pdfPath){}
        void onFailure(Exception error);
    }

    public interface ParserConfigDataSimple<T> extends ParserConfigData<T>{
    }

    public static <T> List<T> addBaseUrlOnList(List<T> items, String imagePath, String pdfPath) {
        for (T item : items){
            if(item instanceof BaseDataModel){
                ((BaseDataModel)item).setImagePath(imagePath);
                ((BaseDataModel)item).setPdfPath(pdfPath);
            }
        }
        return items;
    }


    /**
     * @param data : JSON data
     * @param type : new TypeToken<ModelName>() {}.getType()
     * @param callback : response
     * @param <T> : typecast
     */
//    public static <T> void parseConfigData(String data, Type type, ParserConfigDataSimple<T> callback) {
//         parseConfigData(data, null, type, callback);
//    }
    public static <T> void parseConfigData(String data, Type type, ParserConfigData<T> callback) {
         parseConfigData(data, null, type, callback);
    }

    /**
     * @param data : JSON data
     * @param objectKey : parse key from data object
     * @param type : new TypeToken<ModelName>() {}.getType()
     * @param callback : response
     * @param <T> : typecast
     */
    public static <T> void parseConfigData(String data, String objectKey, Type type, ParserConfigData<T> callback) {
        try {
            if (!BaseUtil.isEmptyOrNull(data)) {
                JSONObject object = new JSONObject(data);
                String finalData = data;
                String imagePath = null, pdfPath = null;
                if (!BaseUtil.isEmptyOrNull(object.optString(objectKey))) {
                    imagePath = object.optString(keyImagePath);
                    pdfPath = object.optString(keyPdfPath);
                    finalData = object.optString(objectKey);
                }
                final T t = GsonParser.getGson()
                        .fromJson(finalData, type);
                if (t != null) {
                    if (t instanceof BaseDataModel) {
                        ((BaseDataModel) t).setImagePath(imagePath);
                        ((BaseDataModel) t).setPdfPath(pdfPath);
                    }
                    callback.onSuccess(t, imagePath, pdfPath);
                    callback.onSuccess(t);
                } else {
                    callback.onFailure(new Exception(BaseConstants.NO_DATA));
                }
            } else {
                callback.onFailure(new Exception(BaseConstants.EMPTY_OR_NULL_DATA));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            callback.onFailure(e);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure(e);
        }
    }

    /**
     * @param data : JSON data
     * @param type : new TypeToken<ModelName>() {}.getType()
     * @param callback : response
     * @param <T> : typecast
     */
    public static <T> void parseConfigData(String data, Type type, ParserConfigDataSimple<T> callback) {
        try {
            if (!BaseUtil.isEmptyOrNull(data)) {
                final T t = GsonParser.getGson()
                        .fromJson(data, type);
                if (t != null) {
                    callback.onSuccess(t);
                } else {
                    callback.onFailure(new Exception(BaseConstants.NO_DATA));
                }
            } else {
                callback.onFailure(new Exception(BaseConstants.EMPTY_OR_NULL_DATA));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            callback.onFailure(e);
        }
    }
}
