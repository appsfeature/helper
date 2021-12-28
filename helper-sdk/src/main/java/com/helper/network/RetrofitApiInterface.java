package com.helper.network;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author Created by Abhijit on 2/28/2018.
 */
public interface RetrofitApiInterface {

    @POST("{endpoint}")
    Call<NetworkBaseModel> requestPost(@Path("endpoint") String endpoint, @QueryMap Map<String, String> options);

    @GET("{endpoint}")
    Call<NetworkBaseModel> requestGet(@Path("endpoint") String endpoint, @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("{endpoint}")
    Call<NetworkBaseModel> requestPostDataForm(@Path("endpoint") String endpoint, @FieldMap Map<String, String> options);

}
