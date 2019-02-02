package com.beyondthecode.newsreader.data.source.remote;

import android.support.annotation.NonNull;

import com.beyondthecode.newsreader.data.models.News;
import com.beyondthecode.newsreader.data.models.NewsResponse;
import com.beyondthecode.newsreader.data.source.NewsDataSource;
import com.beyondthecode.newsreader.util.Constants;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class NewsRemoteDataSource implements NewsDataSource {
    private NewsService newsService;

    @Inject
    public NewsRemoteDataSource(NewsService newsService){
        this.newsService = newsService;
    }

    /**
     * We get fresh news (items) from Remote API
     * */
    @Override
    public void getNews(String category, @NonNull LoadNewsCallBack callBack) {
        newsService.getNews(Constants.NEWS_API_COUNTRY_STRING, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<NewsResponse, List<News>>(){

                    @Override
                    public List<News> apply(NewsResponse newsResponse) throws Exception {
                        return newsResponse.articles;
                    }
                })
                .subscribe(new SingleObserver<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(List<News> news) {
                        callBack.onNewsLoaded(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onDataNotAvailable();
                    }
                });
    }

    /**
    * These methods should be implemented when required
     * (e.g: when a cloud service is integrated)
    * */
    @Override
    public void getArchivedNews(@NonNull LoadSavedNewsCallback callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void insertNews(News news) {

    }

    @Override
    public void updateNews(News news) {

    }

    @Override
    public void refreshNews(String category) {

    }

    @Override
    public void deleteNews() {

    }


}
