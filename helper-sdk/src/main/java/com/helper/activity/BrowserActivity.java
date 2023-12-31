package com.helper.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.helper.Helper;
import com.helper.R;
import com.helper.util.BaseConstants;
import com.helper.util.BaseUtil;
import com.helper.util.FileUtils;
import com.helper.util.Logger;
import com.helper.util.RunTimePermissionUtility;
import com.helper.util.SocialUtil;

import java.io.File;


public class BrowserActivity extends AppCompatActivity {

    private final String TAG = BrowserActivity.class.getSimpleName();
    private String url;

    private static final int WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF = 101;
    public ProgressBar progressBar;
    public RelativeLayout container;
    public WebView webView;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_browser);

        initDataFromIntent();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!TextUtils.isEmpty(title)) {
                getSupportActionBar().setTitle(title);
            }
        }
        BaseUtil.loadBanner(findViewById(R.id.rlBannerAds), this);
    }


    @Override
    public void onPause() {
        webView.onPause();
        webView.pauseTimers();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
        webView.onResume();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initDataFromIntent() {
        progressBar = findViewById(R.id.progressBar);
        container = findViewById(R.id.container);
        Intent intent = getIntent();

        if (intent.hasExtra(BaseConstants.WEB_VIEW_URL)) {
            url = intent.getStringExtra(BaseConstants.WEB_VIEW_URL);
        }
        if (intent.hasExtra(BaseConstants.TITLE)) {
            title = intent.getStringExtra(BaseConstants.TITLE);
        }
        if (TextUtils.isEmpty(url)) {
            BaseUtil.showToast(this, "Invalid Url");
            finish();
            return;
        }
        Logger.e(TAG, "WebView Url => " + url);

//        webView = findViewById(R.id.webView);
        webView = new WebView(getApplicationContext());
        container.addView(webView);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                super.onConsoleMessage(consoleMessage);
                if (consoleMessage.message().startsWith(TAG)) {
                    if (consoleMessage.message().toLowerCase().contains("viewable only") && consoleMessage.message().toLowerCase().contains("landscape")) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                }
                return false;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                super.shouldOverrideUrlLoading(view, request);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String requestUrl = request.getUrl().toString();
                    return filterUrl(view, requestUrl);
                }
                return false;
            }

            @Override
            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    return filterUrl(view, url);
                }
                return false;
            }

            private boolean filterUrl(WebView view, String mUrl) {
                url = mUrl;
                if (mUrl.endsWith("viewer.action=download")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(mUrl));
                    startActivity(i);
                    return false;
                }
                if (isUrlPdfType(mUrl)) {
                    openPDF(mUrl);
                    return false;
                }
                if (isUrlIntentType(mUrl)) {
                    SocialUtil.openIntentUrl(BrowserActivity.this, mUrl);
                    progressBar.setVisibility(View.GONE);
                    webView.stopLoading();
                    webView.goBack();
                    return false;
                }
                view.loadUrl(mUrl);
                return true;
            }


            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                if (!isUrlPdfType(url))
                    view.loadUrl("javascript:console.log('" + TAG + "'+document.getElementsByTagName('html')[0].innerHTML);");
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.loadUrl(url);
    }

    private boolean isUrlPdfType(String url) {
        return url.toLowerCase().endsWith(".pdf");
    }

    private boolean isUrlIntentType(String url) {
        return url.toLowerCase().startsWith("intent://");
    }

    private void openPDF(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (Helper.getInstance().getListener() != null) {
                Helper.getInstance().getListener().onOpenPdf(this, (int) System.currentTimeMillis(), TextUtils.isEmpty(title) ? "From Browser" : title, url);
            } else {
                SocialUtil.openUrlExternal(this, url);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {
            shareLink();
            return true;
        } else if (id == R.id.action_refresh) {
            progressBar.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            webView.loadUrl(url);
            return true;
        } else if (id == R.id.action_pdf) {
            if (RunTimePermissionUtility.doWeHaveWriteExternalStoragePermission(BrowserActivity.this)) {
                try {
                    saveWebPageToPDF(webView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                RunTimePermissionUtility.requestWriteExternalStoragePermission(BrowserActivity.this, WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Logger.e(TAG, "requestCode : " + requestCode + ", permissions :" + permissions + ",grantResults : " + grantResults);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Logger.e(TAG, "WRITE_EXTERNAL permission has now been granted. Showing result.");
            if (requestCode == WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF) {
                try {
                    saveWebPageToPDF(webView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.e(TAG, "WRITE_EXTERNAL permission was NOT granted.");
            if (requestCode == WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF) {
                RunTimePermissionUtility.showReasonBoxForWriteExternalStoragePermission(this, WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF);
            }
        }
    }

    private boolean isRunning = false;

    /**
     * Alternate Class ShareHtmlContent
     */
    private void saveWebPageToPDF(WebView webView){
        //FBAnalytics.fbLogStringValue(BaseConstants.FBAnalyticsEvent.CREATE_PDF, url);
        if (Build.VERSION.SDK_INT >= 21) {
            if (!isRunning) {
                isRunning = true;
                showHideProgressBar(true);
                String jobName = getString(R.string.app_name);
                PrintAttributes attributes = new PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                        .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
//                File path = FileUtils.getFilePublic(this, jobName);
                final File path = FileUtils.getFileStoreDirectory(this, jobName);
                String fileName = "Result" + System.currentTimeMillis() + ".pdf";
                PdfPrint pdfPrint = new PdfPrint(attributes);
                pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, fileName, new PdfPrint.PDFSaveInterface() {
                    @Override
                    public void onSaveFinished(boolean isSuccess) {
                        showHideProgressBar(false);
                        //sharePDF(fullPath);
                        isRunning = false;
                        if (isSuccess) {
                            showPDFSaveDialog("PDF saved to Documents", new File(path, fileName));
                        } else {
                            showPDFFailDialog();
                        }
                    }
                });
            } else {
                BaseUtil.showToast(this, "Already processing existing request. Please wait...");
            }
        } else {
            showPDFFailDialog();
        }
    }

    private void showPDFFailDialog() {
        BaseUtil.showToast(this, "Your Phone not able to perform this Action.");
    }

    private void showHideProgressBar(boolean show) {
        if (show) {
            String processing = "Saving PDF to SD Card....";
            BaseUtil.showDialog(this, processing, false);
        } else {
            BaseUtil.hideDialog();
        }
    }

    private void sharePDF(File fullPath) {
        Logger.e(TAG, "sharePDF Path : " + fullPath);
        SocialUtil.sharePdf(this, fullPath);
    }

    private void showPDFSaveDialog(String message, final File pdfPath) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        alertDialogBuilder.setTitle("Alert");

        //alertDialogBuilder.setIcon(R.drawable.notification_template_icon_bg);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> arg0.cancel());

        alertDialogBuilder.setNegativeButton("Share", (dialog, which) -> sharePDF(pdfPath));

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void shareLink() {
        if (url != null) {
            SocialUtil.shareText(this, url);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 21) {
                String croppedImagePath = data.getStringExtra("image_path");
                SocialUtil.shareImageIntent(BrowserActivity.this, croppedImagePath);
            }
        } else {
            Logger.e(TAG, "onActivityResult : " + resultCode);
        }
    }

    @Override
    public void onBackPressed() {
//        if (webView != null && webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//        }
        super.onBackPressed();
    }
}