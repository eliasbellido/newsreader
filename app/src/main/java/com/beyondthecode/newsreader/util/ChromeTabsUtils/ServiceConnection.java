package com.beyondthecode.newsreader.util.ChromeTabsUtils;

import android.content.ComponentName;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;

import java.lang.ref.WeakReference;

public class ServiceConnection extends CustomTabsServiceConnection {

    // A weak reference to the ServiceConnectionCallback to avoid leaking it.
    private WeakReference<ServiceConnectionCallback> connectionCallback;

    public ServiceConnection(ServiceConnectionCallback connectionCallback) {
        this.connectionCallback = new WeakReference<>(connectionCallback);
    }

    @Override
    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
        ServiceConnectionCallback connectionCallback = this.connectionCallback.get();
        if (connectionCallback != null)
            connectionCallback.onServiceConnected(client);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        ServiceConnectionCallback connectionCallback = this.connectionCallback.get();
        if(connectionCallback != null)
            connectionCallback.onServiceDisconnected();
    }
}
