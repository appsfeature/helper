package com.helper.util;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewHelper {

    public final WebView webView;

    public WebViewHelper(WebView mWebView) {
        this.webView = mWebView;
        setEnableDefaultSettings(mWebView);
    }

    public WebViewHelper(WebView mWebView, boolean isDisableDefaultSetting) {
        this.webView = mWebView;
        if (!isDisableDefaultSetting)
            setEnableDefaultSettings(mWebView);
    }

    public WebViewHelper setEnableAllSettings() {
        setEnableAllSettings(webView);
        return this;
    }

    public void setEnableAllSettings(WebView... webView) {
        for (WebView web : webView) {
            setEnableDefaultSettings(web);
            setEnableHardwareAcceleration(web);
            setEnableCaching(web);
        }
    }

    public WebViewHelper setEnableDefaultSettings() {
        return setEnableDefaultSettings(webView);
    }
    @SuppressLint("SetJavaScriptEnabled")
    public WebViewHelper setEnableDefaultSettings(WebView web) {
        if (web != null && web.getSettings() != null) {
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setSupportZoom(true);
            web.getSettings().setBuiltInZoomControls(true);
            web.getSettings().setDisplayZoomControls(false);
        }
        return this;
    }


    /**
     * @param webView through xml android:hardwareAccelerated="true"
     */
    public void setEnableHardwareAcceleration(WebView... webView) {
        for (WebView webView1 : webView) {
            setEnableHardwareAcceleration(webView1);
        }
    }

    public WebViewHelper setEnableHardwareAcceleration() {
        return setEnableHardwareAcceleration(webView);
    }
    public WebViewHelper setEnableHardwareAcceleration(WebView webView) {
        if (webView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // chromium, enable hardware acceleration
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, new Paint());
            } else {
                // older android version, disable hardware acceleration
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, new Paint());
            }
        }
        return this;
    }

    public WebViewHelper setEnableCaching() {
        return setEnableCaching(webView);
    }
    public WebViewHelper setEnableCaching(WebView web) {
        if (web != null && web.getSettings() != null) {
            web.getSettings().setAllowFileAccess(true);
            web.getSettings().setAppCacheEnabled(true);
            web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        return this;
    }

    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    public void setDataWebView(String data) {
        setDataWebView(data, "#000");
    }
    public void setDataWebView(String data, String textColor, String bgColor) {
        setDataWebView(data, textColor + "; background-color: " + bgColor);
    }
    public void setDataWebView(String data, String textColor) {
        if (webView != null) {
            webView.loadDataWithBaseURL("file:///android_asset/", htmlData(data, textColor), "text/html", "UTF-8", null);
        }
    }

    public void setDataWebView(WebView webView, String data) {
        setDataWebView(webView, data, "#000");
    }
    public void setDataWebView(WebView webView, String data, String textColor, String bgColor) {
        setDataWebView(webView, data, textColor + "; background-color: " + bgColor);
    }
    public void setDataWebView(WebView webView, String data, String textColor) {
        if (webView != null) {
            webView.loadDataWithBaseURL("file:///android_asset/", htmlData(data, textColor), "text/html", "UTF-8", null);
        }
    }

    private String htmlData(String myContent, String color) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<html><head>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"
                + "<style type=\"text/css\">body{color: " + color + "; font-size:large; font-family:roboto_regular;"
                + " }"
                + "</style>"
                + "<head><body>" + myContent + "</body></html>";
    }

    public void destroyWebView(WebView webView) {
        if (webView != null) {
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
    }

}
