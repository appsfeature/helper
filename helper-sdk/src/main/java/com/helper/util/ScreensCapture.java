package com.helper.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.helper.task.TaskRunner;

import java.util.concurrent.Callable;

public class ScreensCapture {

    public static void takeScreenShot(final String fileName, final View view, TaskRunner.Callback<Uri> callback) {
        screenShot(fileName, view, true, callback);
    }

    /**
     * @param view: Pass childView of ScrollView
     */
    public static void takeScreenShotScrollView(final String fileName, final View view, TaskRunner.Callback<Uri> callback) {
        screenShot(fileName, view, false, callback);
    }

    private static void screenShot(final String fileName, final View view, boolean isGetFromCache, TaskRunner.Callback<Uri> callback) {
        TaskRunner.getInstance().executeAsync(new Callable<Uri>() {
            @Override
            public Uri call() throws Exception {
                String path = null;
                try {
                    view.setDrawingCacheEnabled(true);
                    view.buildDrawingCache(true);
                    Bitmap bitmap;
                    if(isGetFromCache) {
                        bitmap = Bitmap.createBitmap(view.getDrawingCache());
                    }else {
                        bitmap = getBitmapFromView(view, view.getMeasuredWidth(), view.getMeasuredHeight());
                    }
                    view.setDrawingCacheEnabled(false);
                    path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(), bitmap, getFileName(fileName), null);
                    if(path == null){
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return Uri.parse(path);
            }
        }, callback);
    }

    private static String getFileName(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            fileName = fileName.replaceAll(" ", "_") + "_" + BaseUtil.getTimeStamp();
        }else {
            fileName = BaseUtil.getTimeStamp();
        }
        return fileName;
    }

    public static Bitmap getBitmapFromView(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

}
