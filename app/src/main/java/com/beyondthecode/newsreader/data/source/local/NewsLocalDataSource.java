package com.beyondthecode.newsreader.data.source.local;

import android.support.annotation.NonNull;

import com.beyondthecode.newsreader.data.models.News;
import com.beyondthecode.newsreader.data.source.NewsDataSource;
import com.beyondthecode.newsreader.util.ExecutorUtils.AppExecutors;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Inject;

public class NewsLocalDataSource implements NewsDataSource {

    private final NewsDao newsDao;
    private final AppExecutors appExecutors;

    @Inject
    public NewsLocalDataSource(@NonNull AppExecutors appExecutors, @NonNull NewsDao newsDao) {
        this.newsDao = newsDao;
        this.appExecutors = appExecutors;
    }

    @Override
    public void getNews(String category, @NonNull LoadNewsCallBack callBack) {
        Runnable runnable = () ->{
            final List<News> tasks = newsDao.getNews(category);
            appExecutors.getMainThread().execute(
                    () -> {
                        if(tasks.isEmpty()){
                            callBack.onDataNotAvailable();
                        }else{
                            callBack.onNewsLoaded(tasks);
                        }
                    }
            );
        };

        appExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void getArchivedNews(@NonNull final LoadSavedNewsCallback callback) {
        Runnable runnable = () -> {
            final List<News> tasks = newsDao.getArchivedNews();

            appExecutors.getMainThread().execute(
                    () -> {
                        if(tasks.isEmpty())
                            callback.onDataNotAvailable();
                        else
                            callback.onNewsLoaded(tasks);
                    }
            );

        };
        appExecutors.getDiskIO().execute(runnable);
    }

    @Override
    public void insertNews(News news) {
        Preconditions.checkNotNull(news);

        Runnable saveRunnable = () -> {
            newsDao.insertNews(news);
        };

        appExecutors.getDiskIO().execute(saveRunnable);
    }

    @Override
    public void updateNews(@NonNull News news) {
        // let's fail fast
        Preconditions.checkNotNull(news);

        Runnable updateRunnable = () -> {
            newsDao.updateNews(news);
        };

        appExecutors.getDiskIO().execute(updateRunnable);

    }

    /**
     * deletes all news (excepting saved ones) from the Room database
     * in order to make space for fresh news
     */
    @Override
    public void refreshNews(final String category) {
        Runnable refreshRunnable = () -> {
            newsDao.refreshNews(category);
        };

        appExecutors.getDiskIO().execute(refreshRunnable);
    }

    @Override
    public void deleteNews() {
        Runnable deleteRunnable = () -> {
            newsDao.deleteNews();
        };

        appExecutors.getDiskIO().execute(deleteRunnable);
    }
}
