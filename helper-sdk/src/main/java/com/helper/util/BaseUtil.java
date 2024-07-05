package com.helper.util;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.helper.R;
import com.helper.callback.NetworkListener;
import com.helper.callback.Response;
import com.helper.widget.AppProgress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Field;

public class BaseUtil {

    public static <T> String printObject(T t) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if(field.getName().equals("CREATOR")){
                        continue;
                    }
                    sb.append(field.getName()).append(": ").append(field.get(t)).append('\n');
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return t.toString();
        }
    }
    
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
        if(context != null && !TextUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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
        showNoData(view, BaseConstants.NO_DATA, visibility);
    }

    public static void showNoData(View view, String message, @Response.Visibility int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
            if(visibility == VISIBLE) {
                View tvNoData = view.findViewById(R.id.tv_no_data);
                if (view.findViewById(R.id.player_progressbar) != null) {
                    view.findViewById(R.id.player_progressbar).setVisibility(GONE);
                }
                if (tvNoData != null) {
                    tvNoData.setVisibility(VISIBLE);
                    if(tvNoData instanceof TextView) {
                        ((TextView)tvNoData).setText(getNoDataMessage(view.getContext(), message));
                    }
                }
                View layoutRetry = view.findViewById(R.id.layout_retry);
                if (layoutRetry != null) layoutRetry.setVisibility(GONE);
            }
        }
    }

    private static String getNoDataMessage(Context context, String message) {
        return !isConnected(context) ? BaseConstants.NO_INTERNET_CONNECTION : message;
    }

    public static void showNoDataProgress(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
            if (view.findViewById(com.helper.R.id.player_progressbar) != null) {
                view.findViewById(com.helper.R.id.player_progressbar).setVisibility(VISIBLE);
            }
            View tvNoData = view.findViewById(com.helper.R.id.tv_no_data);
            if (tvNoData != null) tvNoData.setVisibility(GONE);

            View layoutRetry = view.findViewById(R.id.layout_retry);
            if (layoutRetry != null) layoutRetry.setVisibility(GONE);
        }
    }

    public static void showNoDataRetry(View view, NetworkListener.Retry retryCallback) {
        showNoDataRetry(view, retryCallback, null);
    }

    public static void showNoDataRetry(View view, NetworkListener.Retry retryCallback, Response.Progress progress) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            View tvNoData = view.findViewById(R.id.tv_no_data);
            View layoutRetry = view.findViewById(R.id.layout_retry);
            Button btnRetry = view.findViewById(R.id.btn_retry);
            View pbProgress = view.findViewById(R.id.player_progressbar);

            BaseAnimationUtil.alphaAnimation(layoutRetry, View.VISIBLE);
            BaseAnimationUtil.alphaAnimation(pbProgress, View.GONE);
            tvNoData.setVisibility(View.GONE);
            if (progress != null) progress.onStopProgressBar();
            if (btnRetry != null) btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaseAnimationUtil.alphaAnimation(layoutRetry, View.GONE);
                    BaseAnimationUtil.alphaAnimation(pbProgress, View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    if (progress != null) progress.onStartProgressBar();
                    if (retryCallback != null) {
                        retryCallback.onRetry();
                    }
                }
            });
        }
    }

    public static String getColorValue(Context context, int colorResource) {
        return getColorValue(context, "", colorResource);
    }
    public static String getColorValue(Context context, String transparentLevel, int colorResource) {
        try {
            if(context == null){
                return "#fff";
            }
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
        SocialUtil.openLinkInBrowserApp(context, title, webUrl);
    }

    public static void openLinkInBrowserChrome(Context context, String webUrl) {
        SocialUtil.openLinkInBrowserChrome(context, webUrl);
    }

    public static void openLinkInBrowser(Context context, String webUrl) {
        SocialUtil.openLinkInBrowser(context, webUrl);
    }

    public static void showProgressDialog(boolean isShow, Context context) {
        showProgressDialog(context, isShow, context.getString(R.string.helper_loading));
    }
    public static void showProgressDialog(Context context, boolean isShow, String message) {
        showProgressDialog(context, isShow, message, false);
    }
    public static void showProgressDialog(Context context, boolean isShow, String message, boolean isVertical) {
        if (context != null) {
            if (context instanceof Activity && !((Activity) context).isFinishing()) {
                if (isShow) {
                    BaseUtil.showDialog(context, message, false, isVertical);
                } else {
                    BaseUtil.hideDialog();
                }
            }
        }
    }


    public static void hideDialog() {
         AppProgress.hide();
    }

    public static void showDialog(String msg, Context context) {
        showDialog(context, msg, true);
    }

    public static void showDialog(Context context, String msg, boolean isCancelable) {
        showDialog(context, msg, isCancelable, false);
    }

    public static void showDialog(Context context, String msg, boolean isCancelable, boolean isVertical) {
        AppProgress.show(context, msg, isCancelable, isVertical);
    }

    public static void showPopupDialog(Context context, String message) {
        AppDialog.show(context, message, null, context.getString(R.string.ok), context.getString(R.string.cancel), null);
    }
    public static void showPopupDialog(Context context, String message, Response.Dialog callback) {
        AppDialog.show(context, message, null, context.getString(R.string.ok), context.getString(R.string.cancel), callback);
    }
    public static void showPopupDialog(Context context, String title, String message, Response.Dialog callback) {
        AppDialog.show(context, title, message, context.getString(R.string.ok), context.getString(R.string.cancel), callback);
    }

    public static boolean isEmptyOrNull(String s) {
        return (s == null || TextUtils.isEmpty(s));
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

    public static void hideKeyboard(View editText) {
        try {
            if (editText != null) {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTimeInReadableFormat(String inputDate) {
        return getTimeInReadableFormat(inputDate, "dd-MMM-yy hh:mm:a");
    }

    public static String getTimeInReadableFormat(String inputDate, String outputFormat) {
        try {
            if(TextUtils.isEmpty(inputDate)){
                return inputDate;
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(inputDate);
            if(date != null) {
                return new SimpleDateFormat(outputFormat, Locale.US).format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    public static String getTimeInDaysAgoFormat(String serverDateFormat){
        try {
            Date mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(serverDateFormat);
            if(mDate != null) {
                long timeInMilliseconds = mDate.getTime();
                return getTimeInDaysAgoFormat(timeInMilliseconds).toString();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return serverDateFormat;
    }

    public static String getTimeInDaysAgoFormatFromMileSecond(String mileSecond){
        try {
            return !TextUtils.isEmpty(mileSecond) ? getTimeInDaysAgoFormat(Long.parseLong(mileSecond)).toString() : "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * @param mileSecond enter time in millis
     * @return Returns a string describing 'time' as a time relative to 'now'.
     * Time spans in the past are formatted like "42 minutes ago". Time spans in the future are formatted like "In 42 minutes".
     * i.e: 5 days ago, or 5 minutes ago.
     */
    public static CharSequence getTimeInDaysAgoFormat(long mileSecond){
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_TIME;
        return getTimeInDaysAgoFormat(mileSecond, flags);
    }

    public static CharSequence getTimeInDaysAgoFormat(long mileSecond, int flags){
        return DateUtils.getRelativeTimeSpanString(mileSecond, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, flags);
    }

    public static String getTimeTaken(long timeInMileSecond) {
        return String.format(Locale.US, "%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(timeInMileSecond),
                TimeUnit.MILLISECONDS.toSeconds(timeInMileSecond) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMileSecond))
        );
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("_yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static String getDatabaseTimeStamp() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())).format(new Date());
    }


    /**
     * @deprecated replaced by {@link #getTimeInReadableFormat(String, String)}
     */
    @Deprecated
    public static String convertServerDateTime(String inputDate) {
        return getTimeInReadableFormat(inputDate);
    }

    /**
     * @deprecated replaced by {@link #getTimeInReadableFormat(String, String)}
     */
    @Deprecated
    public static String convertServerDateTime(String inputDate, String outputFormat) {
        return getTimeInReadableFormat(inputDate, outputFormat);
    }
    /**
     * @deprecated replaced by {@link #getTimeInDaysAgoFormatFromMileSecond(String)}
     */
    @Deprecated
    public static CharSequence convertTimeStamp(String mileSecond){
        return getTimeInDaysAgoFormatFromMileSecond(mileSecond);
    }

    /**
     * @deprecated replaced by {@link #getTimeTaken(long)}
     */
    @Deprecated
    public static String timeTaken(long time) {
        return getTimeTaken(time);
    }
    /**
     * @deprecated replaced by {@link #getTimeInDaysAgoFormat(String)}
     */
    @Deprecated
    public static String getTimeSpanString(String serverDateFormat){
        return getTimeInDaysAgoFormat(serverDateFormat);
    }
    /**
     * @deprecated replaced by {@link #getTimeInDaysAgoFormatFromMileSecond(String)}
     */
    @Deprecated
    public static CharSequence getTimeStamp(String timeInMileSecond){
        return getTimeInDaysAgoFormatFromMileSecond(timeInMileSecond);
    }
}
