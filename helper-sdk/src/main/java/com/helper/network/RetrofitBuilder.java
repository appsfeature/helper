package com.helper.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.helper.Helper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit on 3/28/2018.
 */

public class RetrofitBuilder {

    public static Retrofit getClient(String host, String securityCode) {
        return getClient(host, securityCode, Helper.getInstance().isEnableDebugMode());
    }

    public static Retrofit getClient(String host, String securityCode, boolean isDebug) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getHttpClient(securityCode, isDebug).build())
                .build();
        return retrofit;
    }

    private static OkHttpClient.Builder getHttpClient(final String securityCode, boolean isDebug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Authorization", securityCode)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                });
        if (isDebug) {
            builder.addInterceptor(loggingInterceptor);
        }
        return builder;
    }

    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

}
