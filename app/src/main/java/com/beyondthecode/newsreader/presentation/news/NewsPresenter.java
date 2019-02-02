package com.beyondthecode.newsreader.presentation.news;

import com.beyondthecode.newsreader.data.models.News;
import com.beyondthecode.newsreader.data.source.NewsDataSource;
import com.beyondthecode.newsreader.data.source.NewsRepository;
import com.beyondthecode.newsreader.di.scopes.ActivityScoped;
import com.beyondthecode.newsreader.mvp.BasePresenter;
import com.beyondthecode.newsreader.util.ChromeTabsUtils.ChromeTabsWrapper;
import com.beyondthecode.newsreader.util.EspressoIdlingResource;
import com.beyondthecode.newsreader.util.SortUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@ActivityScoped
public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {

    private final NewsRepository newsRepository;
    private CompositeDisposable disposables;
    private final ChromeTabsWrapper tabsWrapper;

    @Inject
    public NewsPresenter(NewsRepository newsRepository, CompositeDisposable disposables, ChromeTabsWrapper tabsWrapper) {
        this.newsRepository = newsRepository;
        this.disposables = disposables;
        this.tabsWrapper = tabsWrapper;
    }

    //inject separately ImageLoader client so that tests don't have to care about it
    @Inject
    Picasso picasso;

    /**
     * retrieve all unarchived news (items) from repository
     */
    @Override
    public void onLoadNews(String category) {
        //The request might be handled in a different thread so make sure
        //Espresso knows that the app is busy until the response is handled
        notifyEspressoAppIsBusy();

        newsRepository.getNews(category, new NewsDataSource.LoadNewsCallBack() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNewsLoaded(List<News> news) {
                notifyEspressoAppIsIdle();
                processDataToBeDisplayed(news);
            }

            @Override
            public void onDataNotAvailable() {
                notifyEspressoAppIsIdle();
                processEmptyDataList();
            }
        });

    }

    /**
     * retrieve only archived news (items) from repository
     */
    @Override
    public void onLoadSavedNews() {
        newsRepository.getArchivedNews(new NewsDataSource.LoadSavedNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                processDataToBeDisplayed(news);
            }

            @Override
            public void onDataNotAvailable() {
                processEmptyDataList();
            }
        });
    }

    @Override
    public void onShowNewsDetail(News newsItem) {
        tabsWrapper.openCUstomTab(newsItem.getUrl());
    }


    /**
     * archive {@param newsItem} into repository for possible future reading
     */
    @Override
    public void onArchiveNews(News newsItem) {
        newsItem.setArchived(true);
        newsRepository.updateNews(newsItem);

        view.showSuccessfullyArchivedNews();
    }

    private void processDataToBeDisplayed(List<News> news){
        if(news.isEmpty())
            processEmptyDataList();
        else{
            view.getImageLoaderService(picasso);
            view.showNews(SortUtils.orderNewsByNewest(news));
        }

    }

    private void processEmptyDataList(){
        if(view == null) return;

        view.showNoNews();
    }

    private void notifyEspressoAppIsIdle(){
        //let's make sure the app is still marked as busy then decrement
        if(!EspressoIdlingResource.getIdlingResource().isIdleNow())
            EspressoIdlingResource.decrement(); //set app as idle.
    }

    private void notifyEspressoAppIsBusy(){
        EspressoIdlingResource.increment();
    }

    private void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }
}
