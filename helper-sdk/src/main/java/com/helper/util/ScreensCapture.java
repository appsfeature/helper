package com.helper.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.helper.task.TaskRunner;

import java.util.concurrent.Callable;

public class ScreensCapture {

    public static void takeScreenShot(final String fileName, final View view, TaskRunner.Callback<Uri> callback) {
        TaskRunner.getInstance().executeAsync(new Callable<Uri>() {
            @Override
            public Uri call() throws Exception {
                String path = null;
                try {
                    view.setDrawingCacheEnabled(true);
                    view.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
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

}
