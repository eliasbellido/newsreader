package com.beyondthecode.newsreader.di;

import android.app.Application;

import com.beyondthecode.newsreader.NewsReaderApplication;
import com.beyondthecode.newsreader.data.source.NewsRepositoryModule;
import com.beyondthecode.newsreader.di.scopes.AppScoped;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is the root Dagger component.
 * {@link }
 * is the module from Dagger.Android that helps with the generation
 * and location of subcomponents, which will be in our case,
 * activities
* */

@AppScoped
@Component(modules = {
        NewsRepositoryModule.class,
        AppModule.class,
        UtilityModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class

})
public interface AppComponent extends AndroidInjector<NewsReaderApplication> {

    /*
    * We can now do DaggerAppComponent.builder().application(this.build().inject(this),
    * never having to instantiate any modules or say which module we are passing the application to.
    * Application will just be provided into our app graph
    * */
    @Component.Builder
    interface Builder{

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
