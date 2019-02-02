package com.beyondthecode.newsreader.data.source;

import android.support.annotation.NonNull;

import com.beyondthecode.newsreader.data.models.News;
import com.beyondthecode.newsreader.data.source.scopes.Local;
import com.beyondthecode.newsreader.data.source.scopes.Remote;
import com.beyondthecode.newsreader.di.scopes.AppScoped;
import com.beyondthecode.newsreader.util.ConnectivityUtils.OnlineChecker;
import com.beyondthecode.newsreader.util.SortUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * News repository which handles all data retrieval logic
 */
@AppScoped
public class NewsRepository implements NewsDataSource{
    private final NewsDataSource newsRemoteDataSource;
    private final NewsDataSource newsLocalDatasource;
    private final OnlineChecker onlineChecker;

    @Inject
    public NewsRepository(@Remote NewsDataSource newsRemoteDataSource,
                          @Local NewsDataSource newsLocalDatasource,
                          OnlineChecker onlineChecker) {
        this.newsRemoteDataSource= newsRemoteDataSource;
        this.newsLocalDatasource = newsLocalDatasource;
        this.onlineChecker = onlineChecker;
    }

    /**
     * Online First Scenario
     * First try accessing remote API if there is an active internet connection
     * further if remote DataSource fails, local API is queried
     * if offline, query directly local DataSource
     * */

    @Override
    public void getNews(final String category, @NonNull LoadNewsCallBack callBack) {
        //let's check for internet connection availability
        if(onlineChecker.isOnline()){
            newsRemoteDataSource.getNews(category, new LoadNewsCallBack() {
                @Override
                public void onDisposableAcquired(Disposable disposable) {
                    callBack.onDisposableAcquired(disposable);
                }

                @Override
                public void onNewsLoaded(List<News> news) {
                    callBack.onNewsLoaded(news);

                    //let's refresh repository
                    refreshNews(category, news);
                }

                @Override
                public void onDataNotAvailable() {
                    getNewsFromLocalDataSource(category, callBack);
                }
            });
        }else{
            // if offline, retrieve data from local data source
            getNewsFromLocalDataSource(category, callBack);
        }
    }

    /**
     * Offline First Scenario
     * First retrieve only saved news (items) from local DataSource
     * if local API fails, query Remote DataSource only if there's an
     * active internet connection
     * */
    @Override
    public void getArchivedNews(@NonNull LoadSavedNewsCallback callback) {
        newsLocalDatasource.getArchivedNews(new LoadSavedNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                if(onlineChecker.isOnline())
                    getSavedNewsFromRemoteDataSource(callback);
                else
                    callback.onDataNotAvailable();
            }
        });
    }

    private void getSavedNewsFromRemoteDataSource(@NonNull final LoadSavedNewsCallback callback){
        newsRemoteDataSource.getArchivedNews(new LoadSavedNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getNewsFromLocalDataSource(String category, @NonNull final LoadNewsCallBack callBack){
        newsLocalDatasource.getNews(category, new LoadNewsCallBack() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callBack.onDisposableAcquired(disposable);
            }

            @Override
            public void onNewsLoaded(List<News> news) {
                callBack.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    /**
    * this method refreshes repository data
     * 1st phase - deleting all unsaved News (items) to make space for others
     * 2nd phase - replenishing repository with fresh news (items)
    * */
    private void refreshNews(String category, List<News> news){
        //1st phase
        refreshNews(category);

        //2nd phase
        for(News newsItem : news){
            setNewsItemContent(newsItem, category);
            insertNews(newsItem);
        }
    }

    /**
     * just save news (item) to both remote and local data sources
     */
    @Override
    public void insertNews(News news) {
        newsLocalDatasource.insertNews(news);
        newsRemoteDataSource.insertNews(news);
    }

    /**
     * just update news (item) to both remote and local data sources
     */
    @Override
    public void updateNews(News news) {
        newsLocalDatasource.updateNews(news);
        newsRemoteDataSource.updateNews(news);
    }

    /**
     * deletes all news (excepting saved ones) from the repository
     * in order to make space for fresh items
     */
    @Override
    public void refreshNews(String category) {
        newsRemoteDataSource.refreshNews(category);
        newsLocalDatasource.refreshNews(category);
    }

    /**
     * complete deletion of all records in repository (with no exception)
     */
    @Override
    public void deleteNews() {
        newsLocalDatasource.deleteNews();
        newsRemoteDataSource.deleteNews();
    }

    private void setNewsItemContent(News newsItem, String category){
        //set unique id, category and source for current item before inserting it into repository
        newsItem.setId(SortUtils.hashCode(newsItem.getTitle()));
        newsItem.setCategory(category);
        newsItem.setSourceDataString(newsItem.getSourceData().getName());
    }


}
