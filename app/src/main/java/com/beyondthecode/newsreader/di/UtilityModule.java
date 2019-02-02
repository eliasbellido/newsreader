package com.beyondthecode.newsreader.di;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.beyondthecode.newsreader.di.scopes.AppScoped;
import com.beyondthecode.newsreader.util.ConnectivityUtils.DefaultOnlineChecker;
import com.beyondthecode.newsreader.util.ConnectivityUtils.OnlineChecker;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilityModule {
    @Provides
    @AppScoped
    ConnectivityManager provideConnectivityManager(Application context){
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @AppScoped
    OnlineChecker onlineChecker(ConnectivityManager cm){
        return new DefaultOnlineChecker(cm);
    }

    @Provides
    @AppScoped
    Picasso providePicasso(Application app){
        return Picasso.with(app);
    }
}
