package com.helper.network;

public interface NetworkListener {
    /**
     * @param isConfigLoaded give the state of config library
     * @param isConnected give the status of network connectivity
     */
    void onNetworkStateChanged(boolean isConfigLoaded, boolean isConnected);
}
