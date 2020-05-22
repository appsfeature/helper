package com.helper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.File;


public class FileUtils {

    public static Uri getUriForFile(Context context, File file) {
        String fileProvider = context.getPackageName()+".fileprovider";
        return FileProvider.getUriForFile(context, fileProvider, file);
    }


    private static Bitmap getBitmapFromView(View view) {
        if (view != null) {
            final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

}
