package com.beyondthecode.newsreader.data.source.remote;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.beyondthecode.newsreader.BuildConfig;
import com.beyondthecode.newsreader.di.scopes.AppScoped;
import com.beyondthecode.newsreader.util.Constants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NewsRemoteDataModule {

    @AppScoped
    @Provides
    NewsService provideNewsService(Retrofit retrofit){
        return retrofit.create(NewsService.class);
    }

    @Provides
    @AppScoped
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.NEWS_API_BASE_URL)
                .client(okHttpClient)
                .build();
    }
    @Provides
    @AppScoped
    SharedPreferences providesSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @AppScoped
    Cache provideHttpCache(Application application){
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @AppScoped
    Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @AppScoped
    OkHttpClient provideOkHttpClient(Cache cache){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(
                chain -> {
                    final Request original = chain.request();
                    final HttpUrl originalHttpUrl = original.url();

                    final HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter(Constants.NEWS_API_KEY_STRING, BuildConfig.NewsAPIKEY)
                            .build();

                    final Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    final Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
        );
        return client.cache(cache).build();
    }
}
