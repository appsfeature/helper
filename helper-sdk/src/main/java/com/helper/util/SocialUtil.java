package com.helper.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static android.content.Context.CLIPBOARD_SERVICE;

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

    public static void openAppInPlayStore(Context context, String appId) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appId))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private static void openLinkInBrowser(Context context, String webUrl) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
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
            BaseUtil.showToast(context,"Whats App not Install.");
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
}
