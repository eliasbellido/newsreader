package com.beyondthecode.newsreader.data.source;

import android.support.annotation.NonNull;

import com.beyondthecode.newsreader.data.models.News;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface NewsDataSource {
    interface LoadNewsCallBack{
        void onDisposableAcquired(Disposable disposable);

        void onNewsLoaded(List<News> news);

        void onDataNotAvailable();
    }

    interface LoadSavedNewsCallback{
        void onNewsLoaded(List<News> news);

        void onDataNotAvailable();
    }

    void getNews(String category, @NonNull LoadNewsCallBack callBack);

    void getArchivedNews(@NonNull LoadSavedNewsCallback callback);

    void insertNews(News news);

    void updateNews(News news);

    void refreshNews(String category);

    void deleteNews();

}
