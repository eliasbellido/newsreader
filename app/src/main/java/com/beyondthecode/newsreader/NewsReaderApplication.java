package com.beyondthecode.newsreader;

import android.app.Application;

import com.beyondthecode.newsreader.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class NewsReaderApplication extends DaggerApplication{

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
