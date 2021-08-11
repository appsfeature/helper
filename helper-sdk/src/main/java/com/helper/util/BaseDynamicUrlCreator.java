package com.helper.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.helper.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDynamicUrlCreator {

    public static final String PARAM_EXTRA_DATA = "extras";
    private static final int DEEP_LINK_MAX_LENGTH_SUPPORT = 7168;
    protected DynamicUrlResult resultCallBack;
    private final Context context;

    protected abstract void onBuildDeepLink(@NonNull Uri deepLink, int minVersion, Context context, DynamicUrlCallback callback);

    protected abstract void onDeepLinkIntentFilter(Activity activity);

    public BaseDynamicUrlCreator(Context context) {
        this.context = context;
    }

    public BaseDynamicUrlCreator register(DynamicUrlResult callback) {
        this.resultCallBack = callback;
        if (context instanceof Activity) {
            onDeepLinkIntentFilter(((Activity) context));
        }
        return this;
    }

    public void generate(HashMap<String, String> deepLinkParams, int minVersion, DynamicUrlCallback callback) {
        generate(deepLinkParams, null, minVersion, callback);
    }

    public void generate(HashMap<String, String> deepLinkParams, String extraData, int minVersion, DynamicUrlCallback callback) {
        if (deepLinkParams != null && context != null) {
            Uri.Builder builder = new Uri.Builder()
                    .scheme("https")
                    .authority(context.getString(R.string.url_public_domain_host_manifest))
                    .appendPath(context.getString(R.string.url_public_short_host_postfix));
            for (Map.Entry<String, String> entry : deepLinkParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.appendQueryParameter(key, value);
            }
            if (!TextUtils.isEmpty(extraData)) {
                String encodedExtraData = EncryptData.encode(extraData);
                if((builder.toString().length() + encodedExtraData.length()) < DEEP_LINK_MAX_LENGTH_SUPPORT) {
                    builder.appendQueryParameter(PARAM_EXTRA_DATA, encodedExtraData);
                }
            }
            Uri deepLink = builder.build();
            onBuildDeepLink(deepLink, minVersion, context, callback);
        } else {
            callback.onError(new Exception("Invalid deepLink"));
        }
    }

    public interface DynamicUrlCallback {
        void onDynamicUrlGenerate(String url);
        void onError(Exception e);
    }

    public interface DynamicUrlResult {
        void onDynamicUrlResult(Uri url, String extraData);
        void onError(Exception e);
    }

    public String getUrlParamValue(String url, String key) {
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter(key);
    }

    protected static class EncryptData {
        static String charsetName = "UTF-8";
        // Sending side
        public static String encode(String value) {
            try {
                if(value == null){
                    return value;
                }
                byte[] data = value.getBytes(charsetName);
                return Base64.encodeToString(data, Base64.URL_SAFE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return value;
            }
        }
        // Receiving side
        public static String decode(String base64value) {
            try {
                if(base64value == null){
                    return base64value;
                }
                byte[] data = Base64.decode(base64value, Base64.URL_SAFE);
                return new String(data, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return base64value;
            }
        }
    }
}

//Make new class <DynamicUrlCreator> in your project
//Paste following code.

//import com.helper.util.BaseDynamicUrlCreator;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.dynamiclinks.DynamicLink;
//import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
//import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
//import com.google.firebase.dynamiclinks.ShortDynamicLink;
//
//public class DynamicUrlCreator extends BaseDynamicUrlCreator {
//
//    public DynamicUrlCreator(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected void onBuildDeepLink(@NonNull Uri deepLink, int minVersion, Context context, BaseDynamicUrlCreator.DynamicUrlCallback callback) {
//        String uriPrefix = context.getResources().getString(R.string.url_public_short_host);
//        if (!TextUtils.isEmpty(uriPrefix)) {
//            DynamicLink.Builder builder = FirebaseDynamicLinks.getInstance()
//                    .createDynamicLink()
//                    .setLink(deepLink)
//                    .setDomainUriPrefix(uriPrefix)
//                    .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
//                            .setMinimumVersion(minVersion)
//                            .build());
//
//            // Build the dynamic link
//            builder.buildShortDynamicLink().addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
//                @Override
//                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
//                    if (task.isComplete() && task.isSuccessful() && task.getResult() != null
//                            && task.getResult().getShortLink() != null) {
//                        callback.onDynamicUrlGenerate(task.getResult().getShortLink().toString());
//                    } else {
//                        callback.onError(new Exception(task.getException()));
//                    }
//                }
//            });
//        } else {
//            callback.onError(new Exception("Invalid Dynamic Url"));
//        }
//    }
//
//    @Override
//    protected void onDeepLinkIntentFilter(Activity activity) {
//        if (activity != null && activity.getIntent() != null) {
//            FirebaseDynamicLinks.getInstance()
//                    .getDynamicLink(activity.getIntent())
//                    .addOnSuccessListener(activity, new OnSuccessListener<PendingDynamicLinkData>() {
//                        @Override
//                        public void onSuccess(PendingDynamicLinkData linkData) {
//                            if (resultCallBack != null && linkData != null && linkData.getLink() != null) {
//                                resultCallBack.onDynamicUrlResult(linkData.getLink()
//                                        , BaseDynamicUrlCreator.EncryptData.decode(linkData.getLink().getQueryParameter(PARAM_EXTRA_DATA)));
//                            }
//                        }
//                    })
//                    .addOnFailureListener(activity, new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            if (resultCallBack != null) {
//                                resultCallBack.onError(e);
//                            }
//                        }
//                    });
//        }
//    }
//}
//<integer name="url_min_app_version">01</integer>
//<string name="url_public_short_host">https://appName.page.link</string>
//<string name="url_public_short_host_postfix">invite</string>
//<string name="url_public_domain_host_manifest">appName.page.link</string>
