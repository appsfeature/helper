package com.helper.util;

import static android.content.ContentValues.TAG;
import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.helper.R;
import com.helper.activity.BrowserActivity;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class SocialUtil {


    public static void shareImage(Context context, String imagePath) {
        shareImage(context, Uri.fromFile(new File(imagePath)));
    }

    public static void shareImage(Context context, Uri imageUri) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        context.startActivity(Intent.createChooser(shareIntent, "Share image using"));
    }

    public static void sharePdf(Context context, File file) {
        try {
            Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + context.getString(R.string.file_provider), file);
            sharePdf(context, fileUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sharePdf(Context context, Uri uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("application/pdf");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareImageIntent(Context context, String imagePath) {

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, FileUtils.getUriFromFile(context, new File(imagePath)));
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, "Share image using"));

        //return shareIntent;
    }

    public static void openAppInPlayStore(Context context, String appId) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appId))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    /**
     * @deprecated Use {@link SocialUtil#openLinkInBrowserApp(Context context, String title, String webUrl)} directly.
     */
    @Deprecated
    public static void openLinkInAppBrowser(Context context, String title, String webUrl) {
        openLinkInBrowserApp(context, title, webUrl);
    }

    public static void openLinkInBrowserApp(Context context, String title, String webUrl) {
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

    public static void openLinkInBrowserChrome(Context context, String webUrl) {
        try {
            if(context == null || TextUtils.isEmpty(webUrl)){
                return;
            }
            int colorInt = ContextCompat.getColor(context, R.color.colorPrimary); //red
            CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(colorInt)
                    .build();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setDefaultColorSchemeParams(defaultColors);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(webUrl));
        } catch (Exception e) {
            e.printStackTrace();
            openLinkInBrowser(context, webUrl);
        }
    }

    public static void openLinkInBrowser(Context context, String webUrl) {
        try {
            if (context != null && !TextUtils.isEmpty(webUrl)) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            BaseUtil.showToast(context, "No option available for take action.");
        }
    }


    public static String getAndroidAppUrl(Context context) {
        return "http://play.google.com/store/apps/details?id=" + context.getPackageName();
    }

    public static void shareText(Context context, String text) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static void copyTextToClipBoard(Context context, String text) {
        if (!text.equalsIgnoreCase("")) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", text);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
            }
            BaseUtil.showToast(context, "Copied to Clip Board");
        } else {
            LoggerCommon.e(TAG, "copyTextToClipBoard : Nothing to copy");
        }
    }

    public static void shareOnWhatsApp(Context context, String message) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        try {
            Objects.requireNonNull(context).startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            BaseUtil.showToast(context, "Whats App not Install.");
        }
    }

    public static void shareOnEMail(Context context, String mailSubject, String mailText) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.setPackage("com.google.android.gm");
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"sharma.joginder89@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mailText);

        try {
            Objects.requireNonNull(context).startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            BaseUtil.showToast(context, "EMail Client not Install.");
        }
    }

    /**
     * add flag when open activity with context reference
     * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     */
    public static void share(Context context, String message) {
        String appLink = message + "Download " + context.getString(R.string.app_name) + " app. \nLink : http://play.google.com/store/apps/details?id=";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, appLink + context.getPackageName());
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * add flag when open activity with context reference
     * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     */
    public static void rateUs(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * add flag when open activity with context reference
     * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     */
    public static void moreApps(Context context, String developerName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + developerName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void openIntentUrl(Context context, String url) {
        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void openUrlExternal(Activity activity, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
