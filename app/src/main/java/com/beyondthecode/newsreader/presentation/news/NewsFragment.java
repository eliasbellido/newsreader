package com.beyondthecode.newsreader.presentation.news;

import com.beyondthecode.newsreader.data.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.android.support.DaggerFragment;

public class NewsFragment extends DaggerFragment implements NewsContract.View {

    @Override
    public void showNews(List<News> news) {

    }

    @Override
    public void showNoNews() {

    }

    @Override
    public void showSuccessfullyArchivedNews() {

    }

    @Override
    public void getImageLoaderService(Picasso picasso) {

    }
}
