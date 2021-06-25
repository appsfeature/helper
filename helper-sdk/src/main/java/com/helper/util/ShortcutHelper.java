package com.helper.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

public class ShortcutHelper {

    public static final String EXTRA_SHORTCUT_DATA = "extra_data";

    public static Intent getShortcutClass(Activity activity, Class<?> activityToOpen) {
        return new Intent(Intent.ACTION_MAIN, Uri.EMPTY, activity, activityToOpen);
    }

    /**
     * @param activityToOpen = YourActivity.class
     */
    public static void createShortcut(@NonNull Activity activity, @NonNull Class<?> activityToOpen, String extraData, @NonNull String title, @DrawableRes int icon) {
        Intent shortcutIntent = getShortcutClass(activity, activityToOpen);
        shortcutIntent.putExtra(EXTRA_SHORTCUT_DATA, extraData);
        createShortcut(activity, shortcutIntent, title, icon);
    }

    /**
     * @param shortcutIntent = ShortcutHelper.getShortcutClass(activity, YourActivity.class);
     */
    public static void createShortcut(@NonNull Activity activity, Intent shortcutIntent, @NonNull String title, @DrawableRes int icon) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent(ACTION_SHORTCUT);
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
            intent.putExtra("duplicate", false);
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(activity, icon));
            activity.sendBroadcast(intent);
            Toast.makeText(activity, Message.SHORTCUT_SUCCESS, Toast.LENGTH_SHORT).show();
        } else {
            ShortcutManager shortcutManager = activity.getSystemService(ShortcutManager.class);
            assert shortcutManager != null;
            if (shortcutManager.isRequestPinShortcutSupported()) {
                ShortcutInfo pinShortcutInfo =
                        new ShortcutInfo.Builder(activity, title)
                                .setIntent(shortcutIntent)
                                .setIcon(Icon.createWithResource(activity, icon))
                                .setShortLabel(title)
                                .build();
                shortcutManager.requestPinShortcut(pinShortcutInfo, null);
            } else {
                Toast.makeText(activity, Message.SHORTCUT_FAILURE, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void createDynamicShortcut(@NonNull Activity activity, @NonNull Class<?> activityToOpen, Bundle bundle, @NonNull String title, @DrawableRes int icon) {
        Intent shortcutIntent = getShortcutClass(activity, activityToOpen);
//        shortcutIntent.setData(Uri.EMPTY);
        shortcutIntent.putExtra(EXTRA_SHORTCUT_DATA, bundle); ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(activity, "id" + title)
                .setShortLabel(title)
                .setIcon(IconCompat.createWithResource(activity, icon))
                .setIntent(shortcutIntent)
                .build();
        ShortcutManagerCompat.pushDynamicShortcut(activity, shortcut);
    }


    private static final String ACTION_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    interface Message{
        String SHORTCUT_SUCCESS = "Shortcut added on your home screen.";
        String SHORTCUT_FAILURE = "Launcher does not support shortcut icon";
    }
}
