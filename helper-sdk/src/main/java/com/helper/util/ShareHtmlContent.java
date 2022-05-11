package com.helper.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.helper.R;
import com.helper.callback.Response;

import java.io.File;


public class ShareHtmlContent {
    private final Context context;
    private final Handler handler;
    private static final String colorWhite = "#fff", colorBlack = "#000";
    private final Response.Progress callback;
    private final boolean isDayMode;
    private final String textColor, bgColor;
    private long delayTime = 400;
    private boolean isDeletePreviousFile = false;
    private boolean isAddDownloadLink = true;
    private String extraText;
    private String downloadMessage;
    private Callback mResponseCallback;

    public interface Callback{
        void onPDFGenerated(Context context, File file);
    }

    public ShareHtmlContent(Context context, Response.Progress callback) {
        this.context = context;
        this.callback = callback;
        this.isDayMode = !DayNightPreference.isNightModeEnabled(context);
        this.handler = new Handler(Looper.getMainLooper());
        this.textColor = isDayMode ? colorBlack : colorWhite;
        this.bgColor = isDayMode ? colorWhite : colorBlack;
    }

    public static ShareHtmlContent getInstance(Context context, Response.Progress callback) {
        return new ShareHtmlContent(context, callback);
    }

    public static String getFileName(String prefix) {
        return prefix + ".pdf";
    }


    public void share(final String fileName, String desc) {
        share(fileName, null, desc);
    }

    public void share(final String fileName, WebView desc) {
        share(fileName, desc, null);
    }

    private void share(final String fileName, WebView webView, String desc) {
        if (callback != null) {
            callback.onStartProgressBar();
        }
        if(webView == null) {
            WebView newWebView = new WebView(context);
            loadWebView(newWebView, desc, new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    // do your stuff here
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shareWithPermission(fileName, newWebView);
                        }
                    }, delayTime);
                }
            });
        }else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shareWithPermission(fileName, webView);
                }
            }, delayTime);
        }
    }

    private void shareWithPermission(final String fileName, final WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                saveWebPageToPDF(fileName, webView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Share not supported in this android version.", Toast.LENGTH_SHORT).show();
            shareApp("");
        }
    }

    private boolean isRunning = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void saveWebPageToPDF(final String fileName, final WebView webView) throws Exception{
        if (!isRunning) {
            isRunning = true;
            final File path = FileUtils.getFileStoreDirectory(context);
            final String jobName = webView.getContext().getString(R.string.app_name);

            if(isDeletePreviousFile) {
                File myFile = new File(path, fileName);
                if (myFile.exists()) {
                    myFile.delete();
                }
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PrintAttributes attributes = new PrintAttributes.Builder()
                            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                            .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                            .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
                    PdfPrint pdfPrint = new PdfPrint(attributes);
                    pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, fileName, new PdfPrint.PDFSaveInterface() {
                        @Override
                        public void onSaveFinished(boolean isSuccess) {
                            isRunning = false;
                            if(mResponseCallback != null){
                                mResponseCallback.onPDFGenerated(context, FileUtils.getFile(context, fileName));
                            }else {
                                share(context, FileUtils.getFile(context, fileName));
                            }
                        }
                    });
                }
            }, 100);
        }
    }

    public void share(Context context, File file) {
        try {
            if (callback != null) {
                callback.onStopProgressBar();
            }
            String downloadUrl = "http://play.google.com/store/apps/details?id=" + context.getPackageName();
            String downloadMessage = "\nChick here to open : \n" + downloadUrl;
            Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + context.getString(R.string.file_provider), file);
            intentShare(context, fileUri, downloadMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareApp(String s) {
        String downloadUrl = "http://play.google.com/store/apps/details?id=" + context.getPackageName();
        String downloadMessage = s + "Download " + context.getString(R.string.app_name) + " app. \n" + downloadUrl;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String mExtraText = getExtraText(downloadMessage);
        if(!TextUtils.isEmpty(mExtraText)) {
            sendIntent.putExtra(Intent.EXTRA_TEXT, mExtraText);
        }
        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void intentShare(Context context, Uri uri, String downloadMessage) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        String mExtraText = getExtraText(downloadMessage);
        if(!TextUtils.isEmpty(mExtraText)) {
            intent.putExtra(Intent.EXTRA_TEXT, mExtraText);
        }
//        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("application/pdf");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getExtraText(String mDownloadMessage) {
        String mExtraText = null;
        try {
            mExtraText = extraText;
            if(downloadMessage != null){
                mExtraText = mExtraText == null ? downloadMessage : mExtraText  + downloadMessage;
            }else if(isAddDownloadLink) {
                mExtraText = mExtraText == null ? mDownloadMessage : mExtraText  + mDownloadMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mExtraText;
    }

    /**
     * @implNote
     *             shareHtmlContent.loadWebView(webView, htmlContent, new WebViewClient(){
     *                 @Override
     *                 public void onPageFinished(WebView view, String url) {
     *                     super.onPageFinished(view, url);
     *                 }
     *             });
     */
    public void loadWebView(WebView webView, String data, WebViewClient listener) {
        loadWebView(webView, data, false, listener);
    }
    public void loadWebView(WebView webView, String data, boolean isEnableZoom, WebViewClient listener) {
        try {
            setDataWebView(webView, data, isEnableZoom, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setDataWebView(WebView webView, String data, boolean isEnableZoom, WebViewClient listener) {
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.getSettings().setLoadWithOverviewMode(true);
        if(isEnableZoom){
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(true);
        }
        webView.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setWebViewClient(listener);
        if (data.startsWith("http://") || data.startsWith("https://") || data.startsWith("www.")) {
            webView.loadUrl(data);
        } else {
            String s = htmlData(data, textColor, bgColor);
            webView.loadDataWithBaseURL("file:///android_asset/", s, "text/html", "UTF-8", null);
        }
    }

    public String htmlData(String myContent, String textColor, String bgColor) {
        return "<!DOCTYPE html>" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<html><head>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">"
                + "<style type=\"text/css\">"
                + "@font-face { font-family: 'roboto_regular'; src: url('rr.ttf'); } "
                + "body{color: " + textColor + "; font-family:roboto_regular; background-color: " + bgColor + ";}img{display: inline;height: auto;max-width: 100%;}"
                + "</style>"
                + "<head><body>" + myContent + "</body></html>";

    }

    private boolean isFirstRequest = true;

    public void resetZoom(WebView webView) {
        if(isFirstRequest) {
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
            isFirstRequest = false;
        }else {
            webView.setInitialScale(50);
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
            webView.getSettings().setUseWideViewPort(true);
        }
    }

    public ShareHtmlContent setDelayTime(long delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public ShareHtmlContent setDeletePreviousFile(boolean deletePreviousFile) {
        isDeletePreviousFile = deletePreviousFile;
        return this;
    }

    public ShareHtmlContent setAddDownloadLink(boolean addDownloadLink) {
        isAddDownloadLink = addDownloadLink;
        return this;
    }

    public ShareHtmlContent setExtraText(String extraText) {
        this.extraText = extraText;
        return this;
    }

    public ShareHtmlContent setDownloadMessage(String downloadMessage) {
        this.downloadMessage = downloadMessage;
        return this;
    }

    public ShareHtmlContent setResponseCallback(Callback responseCallback) {
        this.mResponseCallback = responseCallback;
        return this;
    }
}
