package com.beyondthecode.newsreader.util.ChromeTabsUtils;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v4.content.ContextCompat;

import com.beyondthecode.newsreader.R;

public class ChromeTabsWrapper implements ServiceConnectionCallback{

    private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    private Context context;
    private CustomTabsServiceConnection connection;
    private CustomTabsClient client;

    public ChromeTabsWrapper(Context context) {
        this.context = context;
    }

    public void openCUstomTab(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setExitAnimations(context, R.anim.fade_in, R.anim.fade_out);
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    public void bindCustomTabsService(){
        if (client != null)
            return;

        if(connection == null){
            connection = new ServiceConnection(this);
        }

        CustomTabsClient.bindCustomTabsService(context, CUSTOM_TAB_PACKAGE_NAME, connection);

    }

    public void unbindCustomTabsService(){
        if (connection == null) return;

        context.unbindService(connection);
        client = null;
        connection = null;
    }

    @Override
    public void onServiceConnected(CustomTabsClient client) {
        this.client = client;
    }

    @Override
    public void onServiceDisconnected() {
        client = null;
    }
}
