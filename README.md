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
        implementation 'org.bitbucket.android-dennislabs:helper:1.2'
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
            if(url.getQueryParameter(PDFDynamicShare.ACTION_TYPE).equals(PDFDynamicShare.TYPE_PDF)) {
                PDFDynamicShare.open(activity, url, extraData);
            }
        }
    }

    public static boolean isValidIntent(Activity activity) {
        return activity.getIntent().getData() != null
                && activity.getIntent().getData().getAuthority().equals(activity.getString(R.string.url_public_domain_host_manifest));
    }

    public void share(String id, String extraData) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", id);
        param.put(ACTION_TYPE, TYPE_YOUR_MODULE_NAME);
        if (progressListener != null) {
            progressListener.onStartProgressBar();
        }
        generate(param, extraData, new DynamicUrlCreator.DynamicUrlCallback() {
            @Override
            public void onDynamicUrlGenerate(String url) {
                if (progressListener != null) {
                    progressListener.onStopProgressBar();
                }
                Log.d(DynamicUrlCreator.class.getSimpleName(), "Url:" + url);
                shareMe(url);
            }

            @Override
            public void onError(Exception e) {
                if (progressListener != null) {
                    progressListener.onStopProgressBar();
                }
                Log.d(DynamicUrlCreator.class.getSimpleName(), "onError:" + e.toString());
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

    public void addProgressListener(Response.Progress progressListener) {
        this.progressListener = progressListener;
    }

    private void shareMe(String deepLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        context.startActivity(intent);
    }
}
```
#### Step:5 DynamicUrl Usage methods
```java
public class MainActivity extends AppCompatActivity implements DynamicUrlCreator.DynamicUrlResult {

    private static final String TAG = "DynamicUrlCreator";

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
            new DynamicUrlCreator(this)
                    .register(this);
        }
    }

    public void onShareClicked(View view) {
            String id = "01";
            String extraData = "{Large data in form of Json or anything else}";
            new DynamicUrlCreator(this).share(id, extraData);
        }

    @Override
    public void onDynamicUrlResult(Uri uri, String extraData) {
        Log.d(TAG, "url:" + uri.toString());
        Log.d(TAG, "id:" + uri.getQueryParameter("id"));
        Log.d(TAG, "type:" + uri.getQueryParameter("type"));
        Log.d(TAG, "extraData:" + extraData);
    }

    @Override
    public void onError(Exception e) {
        Log.d(TAG, "onError:" + e.toString());
    }
}
```

## ChangeLog

#### Version 1.3-beta01:
* Update Tracking class and usage methods.

#### Version 1.0:
* Initial build