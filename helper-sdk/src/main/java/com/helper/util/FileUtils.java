package com.helper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;


public class FileUtils {
    /**
     * @deprecated  As of release 1.5, replaced by {@link #getUriFromFile(Context, File)}
     */
    @Deprecated
    public static Uri getUriForFile(Context context, File file) {
        return getUriFromFile(context, file);
    }

    public static Uri getUriFromFile(Context context, File file) {
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

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        if (!filePath.contains("/")) {
            return filePath;
        }
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    public static String getFileNameFromUrl(String fileUrl) {
        String fileName = null;
        try {
            if (!TextUtils.isEmpty(fileUrl) && BaseUtil.isValidUrl(fileUrl)) {
                if (fileUrl.contains(".")) {
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.lastIndexOf("."));
                } else {
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                }
            }
            if (TextUtils.isEmpty(fileName)) {
                fileName = "Error-" + System.currentTimeMillis();
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error-" + System.currentTimeMillis();
        }
    }

    public static boolean isSupportLegacyExternalStorage() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.R;
    }

    public static File getFile(Context context, String fileName) {
        if (isSupportLegacyExternalStorage()) {
            return new File(getFileStoreDirectory(context), fileName);
        } else {
            return context.getFileStreamPath(fileName);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getFilePublic(Context context, String fileName) {
        if (isSupportLegacyExternalStorage()) {
            return new File(getFileStoreDirectoryPublic(context), fileName);
        } else {
            return context.getFileStreamPath(fileName);
        }
    }

    public static File getFileStoreDirectory(Context context) {
        if(isSupportLegacyExternalStorage()) {
            final String filePath = Environment.getExternalStorageDirectory() + "/" + BasePrefUtil.getDownloadDirectory(context);
            return new File(filePath);
        }else {
            return context.getFilesDir();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getFileStoreDirectoryPublic(Context context) {
        if(isSupportLegacyExternalStorage()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        }else {
            return context.getFilesDir();
        }
    }

}
