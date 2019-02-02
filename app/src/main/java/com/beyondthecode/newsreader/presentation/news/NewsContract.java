package com.beyondthecode.newsreader.presentation.news;

import com.beyondthecode.newsreader.data.models.News;
import com.beyondthecode.newsreader.mvp.BaseView;
import com.squareup.picasso.Picasso;

import java.util.List;

public interface NewsContract {
    interface View extends BaseView<Presenter>{
        void showNews(List<News> news);

        void showNoNews();

        void showSuccessfullyArchivedNews();

        void getImageLoaderService(Picasso picasso);
    }

    interface Presenter{
        void onLoadNews(String category);

        void onShowNewsDetail(News newsItem);

        void onLoadSavedNews();

        void onArchiveNews(News newsItem);
    }
}
