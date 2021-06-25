package com.helper.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.helper.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class FileUtils {
    /**
     * @deprecated As of release 1.5, replaced by {@link #getUriFromFile(Context, File)}
     */
    @Deprecated
    public static Uri getUriForFile(Context context, File file) {
        return getUriFromFile(context, file);
    }

    public static Uri getUriFromFile(Context context, File file) {
        String fileProvider = context.getPackageName() + context.getString(R.string.file_provider);
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

    public static boolean isFileExists(Context context, String fileName) {
        boolean isFileExixts = false;
        try {
            if (!TextUtils.isEmpty(fileName)) {
                File apkStorage = null;
                if (isSDCardPresent()) {
                    apkStorage = getFileStoreDirectory(context);
                }
                if (apkStorage != null && apkStorage.exists()) {
                    File outputFile = new File(apkStorage, fileName);
                    isFileExixts = outputFile.exists();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFileExixts;
    }

    public static void deleteFile(Context context, String fileName) {
        try {
            if (!TextUtils.isEmpty(fileName)) {
                File apkStorage = null;
                if (isSDCardPresent()) {
                    apkStorage = getFileStoreDirectory(context);
                }
                if (apkStorage != null && apkStorage.exists()) {
                    File outputFile = new File(apkStorage, fileName);
                    if (outputFile.exists()) {
                        outputFile.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getStorageFileList(Context context) {
        List<String> result = null;
        try {
            File path = getFileStoreDirectory(context);
            String[] storageFileList = path.list();
            if (storageFileList != null) {
                result = Arrays.asList(storageFileList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
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
//        return Build.VERSION.SDK_INT < Build.VERSION_CODES.R;
        return Build.VERSION.SDK_INT < 29;
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
        return getFileStoreDirectory(context, BasePrefUtil.getDownloadDirectory(context));
    }

    public static File getFileStoreDirectory(Context context, String folderName) {
        if (isSupportLegacyExternalStorage()) {
            final String filePath = Environment.getExternalStorageDirectory() + "/" + folderName;
            return new File(filePath);
        } else {
            return context.getFilesDir();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getFileStoreDirectoryPublic(Context context) {
        if (isSupportLegacyExternalStorage()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        } else {
            return context.getFilesDir();
        }
    }

    public static boolean shouldAskPermissions(Context context) {
        if (isSupportLegacyExternalStorage()) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                return context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
            }
        } else {
            return false;
        }
        return false;
    }

    public static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

}
