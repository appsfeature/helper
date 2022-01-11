package com.helper.util;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.helper.R;

import java.util.Map;

public class UriBuilder {

    public static Uri.Builder getDynamicUri(Context context) {
        return getUriBuilder(context.getString(R.string.url_public_domain_host_manifest));
    }

    public static Uri.Builder getUriBuilder(String authority) {
        return new Uri.Builder()
                .scheme("https")
                .authority(authority);
    }

    @Nullable
    public static Uri getUri(String url, Map<String, String> params) {
        try {
            Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
            for (Map.Entry<String,String> entry : params.entrySet()) {
              String key = entry.getKey();
              String value = entry.getValue();
              uriBuilder.appendQueryParameter(key, value);
            }
            return uriBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getUrlParamValue(String url, String key) {
        try {
            if(url.contains(key)) {
                Uri uri = Uri.parse(url);
                return uri.getQueryParameter(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getUrlParamValue(Uri uri, String key) {
        try {
            if(uri.toString().contains(key)) {
                return uri.getQueryParameter(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isContainsParam(Uri uri, String key) {
        return uri.toString().contains(key);
    }
}
