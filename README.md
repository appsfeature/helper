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