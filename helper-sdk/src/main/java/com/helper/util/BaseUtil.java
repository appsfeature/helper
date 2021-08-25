package com.helper.util;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.helper.R;
import com.helper.activity.BrowserActivity;
import com.helper.callback.Response;
import com.helper.widget.PopupProgress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BaseUtil {

    public static SpannableString spannableText(String sentence, int textColor, String... coloredTest) {
        return spannableText(sentence, textColor, 0, coloredTest);
    }
    public static SpannableString spannableText(String sentence, int textColor, int textSizeInPx, String... coloredTest) {
        return StyleUtil.spannableText(sentence, textColor, textSizeInPx, false, coloredTest);
    }

    public static void showToast(Context context, String message) {
        showToastCentre(context, message);
    }

    public static void showToastCentre(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getFormattedViews(int number) {
        return convertNumberUSFormat(number);
    }

    //US format
    public static String convertNumberUSFormat(int number){
        try {
            String[] suffix = new String[]{"K","M","B","T"};
            int size = (number != 0) ? (int) Math.log10(number) : 0;
            if (size >= 3){
                while (size % 3 != 0) {
                    size = size - 1;
                }
            }
            double notation = Math.pow(10, size);
            return (size >= 3) ? + (Math.round((number / notation) * 100) / 100.0d)+suffix[(size/3) - 1] : + number + "";
        } catch (Exception e) {
            e.printStackTrace();
            return number + "";
        }
    }

    // indian format
    public static String convertNumberINFormat(int n) {
        try {
            String[] c = new String[]{"K", "L", "Cr"};
            int size = String.valueOf(n).length();
            if (size>=4 && size<6) {
                int value = (int) Math.pow(10, 1);
                double d = (double) Math.round(n/1000.0 * value) / value;
                return (double) Math.round(n/1000.0 * value) / value+" "+c[0];
            } else if(size>5 && size<8) {
                int value = (int) Math.pow(10, 1);
                return (double) Math.round(n/100000.0 * value) / value+" "+c[1];
            } else if(size>=8) {
                int value = (int) Math.pow(10, 1);
                return (double) Math.round(n/10000000.0 * value) / value+" "+c[2];
            } else {
                return n+"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return n + "";
        }
    }

    public static boolean isConnected(Context context) {
        boolean isConnected = false;
        try {
            if (context != null && context.getSystemService(Context.CONNECTIVITY_SERVICE) != null && context.getSystemService(Context.CONNECTIVITY_SERVICE) instanceof ConnectivityManager) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network network = connectivityManager.getActiveNetwork();
                    if (network != null) {
                        NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(network);
                        isConnected = nc != null && (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
                    }
                } else {
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    isConnected = activeNetwork != null && activeNetwork.isConnected();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void showNoDataProgress(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
            if (view.findViewById(com.helper.R.id.player_progressbar) != null) {
                view.findViewById(com.helper.R.id.player_progressbar).setVisibility(VISIBLE);
            }
            TextView tvNoData = view.findViewById(com.helper.R.id.tv_no_data);
            if (tvNoData != null) {
                tvNoData.setVisibility(GONE);
            }
        }
    }

    public static String getColorValue(Context context, int colorResource) {
        return getColorValue(context, "", colorResource);
    }
    public static String getColorValue(Context context, String transparentLevel, int colorResource) {
        try {
            String colorValue = Integer.toHexString(ContextCompat.getColor(context, colorResource) & 0x00ffffff);
            if(colorValue.length() < 6){
                switch (colorValue.length()){
                    case 5:
                        colorValue = "0" + colorValue;
                        break;
                    case 4:
                        colorValue = "00" + colorValue;
                        break;
                    case 3:
                        colorValue = "000" + colorValue;
                        break;
                }
            }
            return "#" + transparentLevel + colorValue;
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

    public static void setNightMode(Context context, boolean isNightModeEnabled) {
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
//        if ( view != null && activity != null && AdsSDK.getInstance() != null ){
//            AdsSDK.getInstance().setAdoptiveBannerAdsOnView(view , activity);
//        }
    }

    public static void openLinkInAppBrowser(Context context, String title, String webUrl) {
        try {
            Intent intent = new Intent(context, BrowserActivity.class);
            intent.putExtra(BaseConstants.WEB_VIEW_URL, webUrl);
            intent.putExtra(BaseConstants.TITLE, title);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            BaseUtil.showToast(context, "No option available for take action.");
        }
    }


    public static void showProgressDialog(boolean isShow, Context context) {
        showProgressDialog(context, isShow, context.getString(R.string.helper_loading));
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
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

    public static boolean isEmptyOrNull(String s) {
        return (s == null || TextUtils.isEmpty(s));
    }



    public static String timeTaken(long time) {
        return String.format(Locale.US, "%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
    }

    public static String getTimeSpanString(String serverDateFormat){
        try {
            Date mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(serverDateFormat);
            if(mDate != null) {
                long timeInMilliseconds = mDate.getTime();
                return DateUtils.getRelativeTimeSpanString(timeInMilliseconds, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return serverDateFormat;
    }

    /**
     * @param mileSecond enter time in millis
     * @return Returns a string describing 'time' as a time relative to 'now'.
     * Time spans in the past are formatted like "42 minutes ago". Time spans in the future are formatted like "In 42 minutes".
     * i.e: 5 days ago, or 5 minutes ago.
     */
    public static CharSequence convertTimeStamp(String mileSecond){
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_TIME;
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSecond), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, flags);
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("_yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static void showKeyboard(View view) {
        try {
            if (view != null && view.requestFocus()) {
                InputMethodManager imm = (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void hideKeyboard(Activity activity) {
        try {
            if(activity != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View f = activity.getCurrentFocus();
                if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
                    imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
                else
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
