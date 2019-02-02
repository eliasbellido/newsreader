package com.beyondthecode.newsreader.data.source;

import com.beyondthecode.newsreader.data.source.local.NewsDao;
import com.beyondthecode.newsreader.data.source.local.NewsLocalDataModule;
import com.beyondthecode.newsreader.data.source.local.NewsLocalDataSource;
import com.beyondthecode.newsreader.data.source.remote.NewsRemoteDataModule;
import com.beyondthecode.newsreader.data.source.remote.NewsRemoteDataSource;
import com.beyondthecode.newsreader.data.source.remote.NewsService;
import com.beyondthecode.newsreader.data.source.scopes.Local;
import com.beyondthecode.newsreader.data.source.scopes.Remote;
import com.beyondthecode.newsreader.di.scopes.AppScoped;
import com.beyondthecode.newsreader.util.ExecutorUtils.AppExecutors;

import dagger.Module;
import dagger.Provides;

/**
 * NewsRepositoryModule contains both Local And Remote Data Sources modules
 */


@Module(includes = {
        NewsRemoteDataModule.class,
        NewsLocalDataModule.class
})
public class NewsRepositoryModule {

    @Provides
    @Local
    @AppScoped
    NewsDataSource provideNewsLocalDataSource(AppExecutors appExecutors, NewsDao newsDao){
        return new NewsLocalDataSource(appExecutors, newsDao);
    }

    @Provides
    @Remote
    @AppScoped
    NewsDataSource provideNewsRemoteDataSource(NewsService newsService){
        return new NewsRemoteDataSource(newsService);
    }
}
