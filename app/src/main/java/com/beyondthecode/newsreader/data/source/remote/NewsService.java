package com.beyondthecode.newsreader.data.source.remote;

import com.beyondthecode.newsreader.data.models.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("top-headlines")
    Single<NewsResponse> getNews(@Query("country") String country,
                                 @Query("category") String category);
}
