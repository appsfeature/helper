package com.helper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

import com.helper.R;
import com.helper.callback.Response;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class BaseUtil {

    public static boolean isEmptyOrNull(String s) {
        return (s == null || TextUtils.isEmpty(s));
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastCentre(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean isConnected(Context context) {
        boolean isConnected = false;

        try {
            if (context != null && context.getSystemService(Context.CONNECTIVITY_SERVICE) != null && context.getSystemService(Context.CONNECTIVITY_SERVICE) instanceof ConnectivityManager) {
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                isConnected = false;
                if (connectivityManager != null) {
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    isConnected = activeNetwork != null && activeNetwork.isConnected();
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return isConnected;
    }

    public static void showNoData(View view, @Response.Visibility int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
            if(visibility == VISIBLE) {
                TextView tvNoData = view.findViewById(R.id.tv_no_data);
                if (view.findViewById(R.id.player_progressbar) != null) {
                    view.findViewById(R.id.player_progressbar).setVisibility(GONE);
                }
                if (tvNoData != null) {
                    tvNoData.setVisibility(VISIBLE);
                    if (!isConnected(view.getContext())) {
                        tvNoData.setText(BaseConstants.NO_INTERNET_CONNECTION);
                    } else {
                        tvNoData.setText(BaseConstants.NO_DATA);
                    }
                }
            }
        }
    }

    public static String getColorValue(Context context, int colorResource) {
        try {
            return "#" + Integer.toHexString(ContextCompat.getColor(context, colorResource) & 0x00ffffff);
        } catch (Exception e) {
            e.printStackTrace();
            return "#ffffff";
        }
    }

    public static boolean isValidUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            return (url.startsWith("file://") || url.startsWith("http://") || url.startsWith("https://"));
        }else {
            return false;
        }
    }

    public static boolean isNightMode(Context context) {
        return DayNightPreference.isNightModeEnabled(context);
    }

    private static void setNightMode(Context context, boolean isNightModeEnabled) {
        DayNightPreference.setNightMode(context, isNightModeEnabled);
    }
}
