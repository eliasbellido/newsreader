package com.beyondthecode.newsreader.di;

import com.beyondthecode.newsreader.di.scopes.ActivityScoped;
import com.beyondthecode.newsreader.presentation.news.NewsActivity;
import com.beyondthecode.newsreader.presentation.news.NewsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of
 * whichever module ActivityBindingModule is on (AppComponent, here).
 * we never need to tell AppComponent that it's going to have all or any of these subcomponents
 * nor do we need to tell these subcomponents that AppComponent exists.
 * We're also telling Dagger.Android that this generated SubComponent needs to include the specified modules and
 * be aware of a scope annotation @ActivityScoped
 * In this case, when Dagger.Android annotation processor runs, it will create 1 subcomponent for us
* */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = NewsModule.class)
    abstract NewsActivity newsActivity();
}
