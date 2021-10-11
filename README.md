# Helper 

#### Library size is : Kb
  
## Setup Project

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven {
            url "https://jitpack.io"
        }
    }
}
```

Add this to your project build.gradle

#### Dependency
[![](https://jitpack.io/v/org.bitbucket.android-dennislabs/helper.svg)](https://jitpack.io/#org.bitbucket.android-dennislabs/helper)
```gradle
dependencies {
        implementation 'org.bitbucket.android-dennislabs:helper:1.4'
}
```


### Statistics Usage methods
```java
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ...
        Helper.getInstance()
                .setDebugMode(BuildConfig.DEBUG)
                .setDownloadDirectory(getInstance(), AppConstant.DOWNLOAD_DIRECTORY)
                .setAdsEnable(AppConstant.IS_ADS_ENABLED)
                .setListener(new Response.Helper() {
                    @Override
                    public void onOpenPdf(Activity activity, int id, String title, String url) {
                        BooksUtil.openPDF(activity, id, title, url);
                    }
                });
    }

```
# DynamicUrl Integration

#### Step:1 Add dependency in your gradle file.
```gradle
 dependencies {
     implementation 'com.google.firebase:firebase-dynamic-links:20.1.1'
 }
```
#### Step:2 Add this in your string.xml file
```xml
    <integer name="url_min_app_version">01</integer>
    <string name="url_public_short_host_postfix">invite</string>
    <string name="url_public_domain_host_manifest">appName.page.link</string>
```
#### Step:3 Add this in your AndroidManifest.xml file
```xml
    <activity android:name=".SplashActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>

        <intent-filter>
            <action android:name="android.intent.action.VIEW"/>
            <category android:name="android.intent.category.DEFAULT"/>
            <category android:name="android.intent.category.BROWSABLE"/>
            <data
                android:host="@string/url_public_domain_host_manifest"
                android:scheme="https"/>
        </intent-filter>

    </activity>

    <activity
        android:name=".activity.MainActivity"
        android:launchMode="singleTop" />
```
#### Step:4 Create new class with name DynamicUrlCreator.class
```java
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.helper.callback.Response;
import com.helper.util.BaseDynamicUrlCreator;
import com.helper.util.BaseUtil;
import com.pdfviewer.util.PDFDynamicShare;

import java.util.HashMap;

public class DynamicUrlCreator extends BaseDynamicUrlCreator {

    public static final String TYPE_YOUR_MODULE_NAME = "your_module_name";
    private Response.Progress progressListener;

    public DynamicUrlCreator(Context context) {
        super(context);
    }

    public static void openActivity(Activity activity, Uri url, String extraData) {
        if(url != null){
            if(url.getQueryParameter(ACTION_TYPE).equals(PDFDynamicShare.TYPE_PDF)) {
                PDFDynamicShare.open(activity, url, extraData);
            }else if(url.getQueryParameter(ACTION_TYPE).equals(TYPE_YOUR_MODULE_NAME)) {
                 //Handle your module event here.
                ModelName modelName = fromJson(extraData, new TypeToken<ModelName>(){});
                if (modelName != null) {
                    SupportUtil.openItem(activity, modelName);
                }
            }
        }
    }

    public void share(String id, String extraData, String description) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", id);
        param.put(ACTION_TYPE, TYPE_YOUR_MODULE_NAME);
        showProgress(View.VISIBLE);
        String extraDataJson = toJson(extraData, new TypeToken<ModelName>() {});
        generate(param, extraDataJson, new DynamicUrlCreator.DynamicUrlCallback() {
            @Override
            public void onDynamicUrlGenerate(String url) {
                showProgress(View.GONE);
                Log.d(DynamicUrlCreator.class.getSimpleName(), "Url:" + url);
                shareMe(description, url);
            }

            @Override
            public void onError(Exception e) {
                showProgress(View.GONE);
                Log.d(DynamicUrlCreator.class.getSimpleName(), "onError:" + e.toString());
                shareMe(description, getPlayStoreLink());
            }
        });
    }

    @Override
    protected void onBuildDeepLink(@NonNull Uri deepLink, int minVersion, Context context, BaseDynamicUrlCreator.DynamicUrlCallback callback) {
        String uriPrefix = getDynamicUrl();
        if (!TextUtils.isEmpty(uriPrefix)) {
            DynamicLink.Builder builder = FirebaseDynamicLinks.getInstance()
                    .createDynamicLink()
                    .setLink(deepLink)
                    .setDomainUriPrefix(uriPrefix)
                    .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                            .setMinimumVersion(minVersion)
                            .build());

            // Build the dynamic link
            builder.buildShortDynamicLink().addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                @Override
                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                    if (task.isComplete() && task.isSuccessful() && task.getResult() != null
                            && task.getResult().getShortLink() != null) {
                        callback.onDynamicUrlGenerate(task.getResult().getShortLink().toString());
                    } else {
                        callback.onError(new Exception(task.getException()));
                    }
                }
            });
        } else {
            callback.onError(new Exception("Invalid Dynamic Url"));
        }
    }

    @Override
    protected void onDeepLinkIntentFilter(Activity activity) {
        if (activity != null && activity.getIntent() != null) {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(activity.getIntent())
                    .addOnSuccessListener(activity, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData linkData) {
                            if(resultCallBack != null) {
                                if (linkData != null && linkData.getLink() != null) {
                                    resultCallBack.onDynamicUrlResult(linkData.getLink()
                                            , BaseDynamicUrlCreator.EncryptData.decode(linkData.getLink().getQueryParameter(PARAM_EXTRA_DATA)));
                                } else {
                                    resultCallBack.onError(new Exception("Invalid Dynamic Url"));
                                }
                            }
                        }
                    })
                    .addOnFailureListener(activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (resultCallBack != null) {
                                resultCallBack.onError(e);
                            }
                        }
                    });
        }
    }

    private String getPlayStoreLink() {
        return "http://play.google.com/store/apps/details?id=" + context.getPackageName();
    }

    private void showProgress(int visibility) {
        if(visibility == View.VISIBLE) {
            if (progressListener != null) {
                progressListener.onStartProgressBar();
            }else {
                BaseUtil.showDialog(context, "Processing, Please wait...", false);
            }
        }else {
            if (progressListener != null) {
                progressListener.onStopProgressBar();
            }else {
                BaseUtil.hideDialog();
            }
        }
    }

    public void addProgressListener(Response.Progress progressListener) {
        this.progressListener = progressListener;
    }

    public void shareMe(String description, String deepLink) {
        shareMe(description, deepLink, null);
    }

    public void shareMe(String description, String deepLink, Uri imageUri) {
        if (BaseUtil.isValidUrl(deepLink)) {
            Log.d(TAG, deepLink);
            String openLink = "\nChick here to open : \n" + deepLink;

            String extraText = description + "\n\n" + openLink;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, extraText);
            intent.setType("text/plain");
            if(imageUri != null) {
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.setType("image/*");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(intent, "Share With"));
        }
    }
}
```
#### Step:5 DynamicUrl Usage methods in SplashActivity
```java
public class  SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        Intent intent = getIntent();
        intent.setClass(this, HomeDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    finishAffinity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
```
#### Step:6 DynamicUrl Usage methods in MainActivity
```java
public class MainActivity extends AppCompatActivity implements DynamicUrlCreator.DynamicUrlResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if(DynamicUrlCreator.isValidIntent(this)) {
                new DynamicUrlCreator(this)
                    .register(this);
            }
        }
    }

    public void onShareClicked(View view) {
            String id = "01";
            String extraData = "{Large data in form of Json or anything else}";
            new DynamicUrlCreator(this).share(id, extraData);
        }

    @Override
    public void onDynamicUrlResult(Uri uri, String extraData) {
        DynamicUrlCreator.openActivity(this, uri, extraData);
    }

    @Override
    public void onError(Exception e) {
        Log.d("DynamicUrlCreator", "onError:" + e.toString());
    }
}
```

## ChangeLog

#### Version 1.4-alpha01:
* Removed AdsSdkMaster library

#### Version 1.3-beta01:
* Update Tracking class and usage methods.

#### Version 1.0:
* Initial build