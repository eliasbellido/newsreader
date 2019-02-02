package com.beyondthecode.newsreader.util.ConnectivityUtils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DefaultOnlineChecker implements OnlineChecker {

    private final ConnectivityManager connectivityManager;

    public DefaultOnlineChecker(ConnectivityManager connectivityManager){
        this.connectivityManager = connectivityManager;
    }
    @Override
    public boolean isOnline() {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
