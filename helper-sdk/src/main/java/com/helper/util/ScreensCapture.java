package com.helper.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.helper.task.TaskRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class ScreensCapture {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void takeScreenShotPDF(Activity activity, final String fileName, final View view, TaskRunner.Callback<Uri> callback) {
        TaskRunner.getInstance().executeAsync(new Callable<Uri>() {
            @Override
            public Uri call() throws Exception {
                Uri fileUri = null;
                try {
                    Bitmap bitmap = getBitmapFromView(view, view.getMeasuredWidth(), view.getMeasuredHeight());
                    File filePath = createPdf(activity, bitmap, fileName + ".pdf");
                    fileUri = FileUtils.getUriFromFile(activity, filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return fileUri;
            }
        }, callback);
    }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File createPdf(Activity activity, Bitmap bitmap, String fileName) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 2).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        final File filePath = FileUtils.getFile(activity, fileName);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();
        return filePath;
    }

}
