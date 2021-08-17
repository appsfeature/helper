package com.helper.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.helper.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDynamicUrlCreator {

    public static final String PARAM_EXTRA_DATA = "extras";
    public static final String ACTION_TYPE = "action_type";
    private static final int DEEP_LINK_MAX_LENGTH_SUPPORT = 7168;
    protected DynamicUrlResult resultCallBack;
    protected final Context context;
    protected int appMinVersion;

    protected abstract void onBuildDeepLink(@NonNull Uri deepLink, int minVersion, Context context, DynamicUrlCallback callback);

    protected abstract void onDeepLinkIntentFilter(Activity activity);

    public BaseDynamicUrlCreator(Context context) {
        this.context = context;
        this.appMinVersion = context.getResources().getInteger(R.integer.url_min_app_version);
    }

    public BaseDynamicUrlCreator register(DynamicUrlResult callback) {
        this.resultCallBack = callback;
        if (context instanceof Activity) {
            onDeepLinkIntentFilter(((Activity) context));
        }
        return this;
    }

    public void generate(HashMap<String, String> deepLinkParams, DynamicUrlCallback callback) {
        generate(deepLinkParams, null, callback);
    }

    public void generate(String extraData, DynamicUrlCallback callback) {
        HashMap<String, String> deepLinkParams = new HashMap<>();
        deepLinkParams.put(ACTION_TYPE, "1");
        generate(deepLinkParams, extraData, callback);
    }

    public void generate(HashMap<String, String> deepLinkParams, String extraData, DynamicUrlCallback callback) {
        generate(deepLinkParams, extraData, appMinVersion, callback);
    }

    public void generate(HashMap<String, String> deepLinkParams, String extraData, int minVersion, DynamicUrlCallback callback) {
        Uri.Builder builder = getDynamicUri();
        if(BaseUtil.isConnected(context)) {
            if (deepLinkParams != null && context != null && builder != null) {
                String path = context.getString(R.string.url_public_short_host_postfix);
                if (!TextUtils.isEmpty(path)) {
                    builder.appendPath(path);
                }
                for (Map.Entry<String, String> entry : deepLinkParams.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    builder.appendQueryParameter(key, value);
                }
                if (!TextUtils.isEmpty(extraData)) {
                    String encodedExtraData = EncryptData.encode(extraData);
                    if ((builder.toString().length() + encodedExtraData.length()) < DEEP_LINK_MAX_LENGTH_SUPPORT) {
                        builder.appendQueryParameter(PARAM_EXTRA_DATA, encodedExtraData);
                    }
                }
                Uri deepLink = builder.build();
                onBuildDeepLink(deepLink, minVersion, context, callback);
            } else {
                callback.onError(new Exception("Invalid deepLink params"));
            }
        }else {
            callback.onError(new Exception(BaseConstants.NO_INTERNET_CONNECTION));
        }
    }

    public String getDynamicUrl() {
        Uri.Builder dynamicUri = getDynamicUri();
        return dynamicUri != null ? dynamicUri.toString() : null;
    }

    public static boolean isValidIntent(Activity activity) {
        Intent intent = activity.getIntent();
        return intent.getData() != null
                && intent.getData().getAuthority().equals(activity.getString(R.string.url_public_domain_host_manifest));
    }

    public Uri.Builder getDynamicUri() {
        if(!TextUtils.isEmpty(context.getString(R.string.url_public_domain_host_manifest))) {
            return new Uri.Builder()
                    .scheme("https")
                    .authority(context.getString(R.string.url_public_domain_host_manifest));
        }else {
            return null;
        }
    }

    protected interface DynamicUrlCallback {
        void onDynamicUrlGenerate(String url);
        void onError(Exception e);
    }

    public interface DynamicUrlResult {
        void onDynamicUrlResult(Uri url, String extraData);
        void onError(Exception e);
    }

    public String getUrlParamValue(String url, String key) {
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter(key);
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public static <T> String toJson(Object item, TypeToken<T> typeCast) {
        return GsonParser.toJsonAll(item, typeCast);
    }


    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public static <T> T fromJson(String jsonValue, TypeToken<T> typeCast) {
        return GsonParser.fromJsonAll(jsonValue, typeCast);
    }

    protected static class EncryptData {
        static String charsetName = "UTF-8";
        // Sending side
        public static String encode(String value) {
            try {
                if(value == null){
                    return value;
                }
                byte[] data = value.getBytes(charsetName);
                return Base64.encodeToString(data, Base64.URL_SAFE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return value;
            }
        }
        // Receiving side
        public static String decode(String base64value) {
            try {
                if(base64value == null){
                    return base64value;
                }
                byte[] data = Base64.decode(base64value, Base64.URL_SAFE);
                return new String(data, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return base64value;
            }
        }
    }
}