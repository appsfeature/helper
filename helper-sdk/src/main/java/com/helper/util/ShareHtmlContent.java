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
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.helper.R;
import com.helper.callback.Response;

import java.io.File;


public class ShareHtmlContent {
    private static ShareHtmlContent instance;
    private final Context context;
    private final Handler handler;
    private static final String colorWhite = "#fff", colorBlack = "#000";
    private final Response.Progress callback;

    private ShareHtmlContent(Context context, Response.Progress callback) {
        this.context = context;
        this.callback = callback;
        handler = new Handler(Looper.getMainLooper());
    }

    public static ShareHtmlContent getInstance(Context context, Response.Progress callback) {
        if (instance == null) {
            instance = new ShareHtmlContent(context, callback);
        }
        return instance;
    }

    public static String getFileName(String prefix) {
        return prefix + ".pdf";
    }


    public void share(final String fileName, String desc) {
        if (callback != null) {
            callback.onStartProgressBar();
        }
        final WebView webView = new WebView(context);
        loadWebView(webView, desc, new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                // do your stuff here`
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shareWithPermission(fileName, webView);
                    }
                }, 400);
            }
        });
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
//            final String filePath = Environment.getExternalStorageDirectory() + "/" + Helper.downloadDirectory;
//            final File path = new File(filePath);
            final File path = FileUtils.getFileStoreDirectory(context);
            final String jobName = webView.getContext().getString(R.string.app_name);
//        String deepLink = "Click here to open: <a href=\"" + strUri + "\">" + strUri + "</a>";
            String deepLink = "";

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
                            share(webView.getContext(), FileUtils.getFile(context, fileName));
                        }
                    });
                }
            }, 100);
        }
    }

    private void share(Context context, File file) {
        try {
            if (callback != null) {
                callback.onStopProgressBar();
            }
            String strUri = "http://play.google.com/store/apps/details?id=" + context.getPackageName();
            Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + context.getString(R.string.file_provider), file);
            intentShare(context, fileUri, strUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareApp(String s) {
        String app_link = s + "Download " + context.getString(R.string.app_name) + " app. \nLink : http://play.google.com/store/apps/details?id=";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, app_link + context.getPackageName());
        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void intentShare(Context context, Uri uri, String deepLink) {
        String text = "\nChick here to open : \n" + deepLink;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
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

    private void loadWebView(WebView webView, String data, WebViewClient listener) {
        try {
            setDataWebView(webView, data, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setDataWebView(WebView webView, String data, WebViewClient listener) {
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
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
            String s = htmlData(data, colorBlack, colorWhite);
            webView.loadDataWithBaseURL("file:///android_asset/", s, "text/html", "UTF-8", null);
        }
    }

    private String htmlData(String myContent, String text, String bg) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<html><head>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">"
                + "<style type=\"text/css\">"
                + "@font-face { font-family: 'roboto_regular'; src: url('rr.ttf'); } "
                + "body{color: " + text + "; font-family:roboto_regular; background-color: " + bg + ";}img{display: inline;height: auto;max-width: 100%;}"
                + "</style>"
                + "<head><body>" + myContent + "</body></html>";

    }

}
