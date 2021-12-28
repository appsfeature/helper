package com.helper.network;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class NetworkResponseCallBack implements Callback<NetworkBaseModel> {

    public interface OnNetworkCall {
        void onComplete(boolean status, NetworkBaseModel response);

        void onError(Exception e);
    }

    private final OnNetworkCall onNetworkCall;

    public NetworkResponseCallBack(OnNetworkCall onNetworkCall) {
        this.onNetworkCall = onNetworkCall;
    }

    @Override
    public void onResponse(@NonNull Call<NetworkBaseModel> call, @NonNull Response<NetworkBaseModel> response) {
        if (onNetworkCall == null) {
            return;
        }
        if (response.code() != 0) {
            int responseCode = response.code();
            if (responseCode == 200) {
                if (response.body() != null) {
                    NetworkBaseModel baseModel = response.body();
                    boolean status = !TextUtils.isEmpty(baseModel.getStatus())
                            && baseModel.getStatus().equals("success");
                    onNetworkCall.onComplete(status, baseModel);
                } else {
                    invalidResponse(response);
                }
            } else if (responseCode == INTERNAL_SERVER_ERROR || responseCode == NOT_FOUND
                    || responseCode == BAD_GATEWAY || responseCode == SERVICE_UNAVAILABLE
                    || responseCode == GATEWAY_TIMEOUT) {
                invalidResponse(response);
            } else {
                invalidResponse(response);
            }
        } else {
            invalidResponse(response);
        }
    }

    private void invalidResponse(Response<NetworkBaseModel> response) {
        if (onNetworkCall != null) {
            onNetworkCall.onError(new Exception("ResponseCode : " + response.code() + " Message : " + response.message()));
        }
    }

    @Override
    public void onFailure(@NonNull Call<NetworkBaseModel> call, @NonNull Throwable t) {
        if (onNetworkCall != null) {
            onNetworkCall.onError(new Exception(t.getMessage()));
        }
    }

    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

}
