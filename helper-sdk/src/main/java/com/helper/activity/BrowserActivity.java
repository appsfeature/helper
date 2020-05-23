package com.helper.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.helper.Helper;
import com.helper.R;
import com.helper.callback.Response;
import com.helper.util.BaseConstants;
import com.helper.util.BaseUtil;
import com.helper.util.FileUtils;
import com.helper.util.Logger;
import com.helper.util.RunTimePermissionUtility;
import com.helper.util.ShareHtmlContent;
import com.helper.util.SocialUtil;

import java.io.File;
import java.util.Objects;


public class BrowserActivity extends AppCompatActivity {

    private String TAG = "BrowserActivity";
    private String url;

    private static final int WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF = 101;
    private static final int WRITE_EXTERNAL_REQUEST_CODE_FOR_SHARE = 102;
    public ProgressBar progressBar;
    public ProgressDialog progressDialog;
    public RelativeLayout container;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_browser);

        initDataFromIntent();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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


    private void initDataFromIntent() {
        progressBar = findViewById(R.id.progressBar);
        container = findViewById(R.id.container);
        Intent intent = getIntent();

        if (intent.hasExtra(BaseConstants.WEB_VIEW_URL)) {
            url = intent.getStringExtra(BaseConstants.WEB_VIEW_URL);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String requestUrl = request.getUrl().toString();
                    if (request.getUrl().toString().endsWith("viewer.action=download")) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(requestUrl));
                        startActivity(i);
                        return false;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (isUrlPdfType(request.getUrl().toString())) {
                            openPDF(request.getUrl().toString());
                            return false;
                        }
                    }
                    if (isUrlIntentType(request.getUrl().toString())) {
                        SocialUtil.openIntentUrl(BrowserActivity.this, request.getUrl().toString());
                        webView.stopLoading();
                        progressBar.setVisibility(View.GONE);
                        webView.goBack();
                        return false;
                    }
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                if (url.endsWith("viewer.action=download")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    return false;
                }
                if (isUrlPdfType(url)) {
                    openPDF(url);
                    return false;
                }
                if (isUrlIntentType(url)) {
                    SocialUtil.openIntentUrl(BrowserActivity.this, url);
                    progressBar.setVisibility(View.GONE);
                    webView.stopLoading();
                    webView.goBack();
                    return false;
                }
                view.loadUrl(url);
                return true;
//                }
            }


            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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
        if (Helper.getInstance().getListener() != null) {
            Helper.getInstance().getListener().onOpenPdf(this, url);
        } else {
            SocialUtil.openUrlExternal(this, url);
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
            //SubCategoriesActivity.share( this );
            if (RunTimePermissionUtility.doWeHaveWriteExternalStoragePermission(BrowserActivity.this)) {
                shareResult();
            } else {
                RunTimePermissionUtility.requestWriteExternalStoragePermission(BrowserActivity.this, WRITE_EXTERNAL_REQUEST_CODE_FOR_SHARE);
            }
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Logger.e(TAG, "requestCode : " + requestCode + ", permissions :" + permissions + ",grantResults : " + grantResults);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Logger.e(TAG, "WRITE_EXTERNAL permission has now been granted. Showing result.");
            switch (requestCode) {
                case WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF:
                    try {
                        saveWebPageToPDF(webView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case WRITE_EXTERNAL_REQUEST_CODE_FOR_SHARE:
                    shareResult();
                    break;
            }
        } else {
            Logger.e(TAG, "WRITE_EXTERNAL permission was NOT granted.");
            switch (requestCode) {
                case WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF:
                    RunTimePermissionUtility.showReasonBoxForWriteExternalStoragePermission(this, WRITE_EXTERNAL_REQUEST_CODE_FOR_PDF);
                    break;
                case WRITE_EXTERNAL_REQUEST_CODE_FOR_SHARE:
                    RunTimePermissionUtility.showReasonBoxForWriteExternalStoragePermission(this, WRITE_EXTERNAL_REQUEST_CODE_FOR_SHARE);
                    break;
            }
        }
    }

    private boolean isRunning = false;

    private void saveWebPageToPDF(WebView webView) throws Exception {
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
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + jobName);
                String fileName = "Result" + System.currentTimeMillis() + ".pdf";
                final String fullPath = path + "/" + fileName;
                PdfPrint pdfPrint = new PdfPrint(attributes);
                pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, fileName, new PdfPrint.PDFSaveInterface() {
                    @Override
                    public void onSaveFinished(boolean isSuccess) {
                        showHideProgressBar(false);
                        //sharePDF(fullPath);
                        isRunning = false;
                        if (isSuccess) {
                            showPDFSaveDialog("PDF saved to Documents", fullPath);
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
            progressDialog = new ProgressDialog(this, R.style.DialogTheme);
            progressDialog.setMessage(processing);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void sharePDF(String fullPath) {
        Logger.e(TAG, "sharePDF Path : " + fullPath);
        SocialUtil.shareImage(this, FileUtils.getUriForFile(this, new File(fullPath)));
    }

    private void showPDFSaveDialog(String message, final String pdfPath) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
        alertDialogBuilder.setTitle("Alert");

        //alertDialogBuilder.setIcon(R.drawable.notification_template_icon_bg);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });

        alertDialogBuilder.setNegativeButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharePDF(pdfPath);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void shareResult() {
        if (webView != null) {
            final String fileName = ShareHtmlContent.getFileName("Article");
            ShareHtmlContent.getInstance(webView.getContext(), new Response.Progress() {
                @Override
                public void onStartProgressBar() {
                    BaseUtil.showDialog(BrowserActivity.this, "Processing, Please wait...", true);
                }

                @Override
                public void onStopProgressBar() {
                    BaseUtil.hideDialog();
                }
            }).share(fileName, url);
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
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}