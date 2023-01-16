package com.helper.callback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public interface NetworkListener {

    interface Retry{
        void onRetry();
    }

    interface NetworkState {
        /**
         * @param isConfigLoaded give the state of config library
         * @param isConnected give the status of network connectivity
         */
        void onNetworkStateChanged(boolean isConfigLoaded, boolean isConnected);
    }

    interface NetworkCall {
        void onComplete(boolean status, String data);

        default void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
        }

        default void onFailure(Call<ResponseBody> call, Throwable t) {
        }

        default void onRetry(NetworkListener.Retry retryCallback, Throwable error) {
        }

        default void onError(int responseCode, Throwable error) {
        }
    }
}
