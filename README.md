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

### Step:1 Add dependency in your gradle file.
```gradle
 dependencies {
     implementation 'com.google.firebase:firebase-dynamic-links:20.1.1'
 }
```
### Step:2 Create new class with name DynamicUrlCreator.class
```java
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.helper.util.BaseDynamicUrlCreator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class DynamicUrlCreator extends BaseDynamicUrlCreator {

    public DynamicUrlCreator(Context context) {
        super(context);
    }

    @Override
    protected void onBuildDeepLink(@NonNull Uri deepLink, int minVersion, Context context, BaseDynamicUrlCreator.DynamicUrlCallback callback) {
        String uriPrefix = context.getResources().getString(R.string.url_public_short_host);
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
                            if (resultCallBack != null && linkData != null && linkData.getLink() != null) {
                                resultCallBack.onDynamicUrlResult(linkData.getLink()
                                        , BaseDynamicUrlCreator.EncryptData.decode(linkData.getLink().getQueryParameter(PARAM_EXTRA_DATA)));
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
}
```
### Step:3 DynamicUrl Usage methods
```java
public class MainActivity extends AppCompatActivity implements DynamicUrlCreator.DynamicUrlResult {

    private static final String TAG = "DynamicUrlCreator";
    private BaseDynamicUrlCreator dynamicShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dynamicShare = new DynamicUrlCreator(this)
                .register(this);
    }

    public void onShareClicked(View view) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", "995");
        param.put("type", "1005");
        String extraData = "{Large data in form of Json or anything else}";
        dynamicShare.generate(param, extraData, 1, new DynamicUrlCreator.DynamicUrlCallback() {
            @Override
            public void onDynamicUrlGenerate(String url) {
                Log.d(TAG, "Url:" + url);
                shareMe(url);
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError:" + e.toString());
            }
        });
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

    private void shareMe(String deepLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }
}
```

## ChangeLog

#### Version 1.3-beta01:
* Update Tracking class and usage methods.

#### Version 1.0:
* Initial build