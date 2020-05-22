package com.helper.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

import com.adssdk.AdsSDK;
import com.helper.Helper;
import com.helper.R;
import com.helper.activity.BrowserActivity;
import com.helper.callback.Response;
import com.helper.widget.PopupProgress;

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

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        if (html == null) {
            return new SpannableString("");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
    public static void loadBanner(final RelativeLayout view , Activity activity) {
        if ( view != null && activity != null && AdsSDK.getInstance() != null ){
            AdsSDK.getInstance().setAdoptiveBannerAdsOnView(view , activity);
        }
    }

    public static void openLinkInAppBrowser(Context context, String webUrl) {
        try {
            Intent intent = new Intent(context, BrowserActivity.class);
            intent.putExtra(BaseConstants.WEB_VIEW_URL, webUrl);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            BaseUtil.showToast(context, "No option available for take action.");
        }
    }


    public static void showProgressDialog(boolean isShow, Context context) {
        showProgressDialog(context, isShow, context.getString(R.string.loading));
    }
    public static void showProgressDialog(Context context, boolean isShow, String message) {
        if (context != null) {
            if (context instanceof Activity && !((Activity) context).isFinishing()) {
                if (isShow) {
                    BaseUtil.showDialog(message, context);
                } else {
                    BaseUtil.hideDialog();
                }
            }
        }
    }

    private static AlertDialog dialog;

    public static void hideDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
        }
    }

    public static void showDialog(String msg, Context context) {
        showDialog(context, msg, true);
    }

    public static void showDialog(Context context, String msg, boolean isCancelable) {
        if (dialog == null) {
            try {
                dialog = PopupProgress.newInstance(context, msg)
                        .setCancelable(isCancelable)
                        .show();
            } catch (Exception e) {
            }
        }
    }

}
