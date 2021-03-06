package com.beyondthecode.newsreader.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.beyondthecode.newsreader.data.models.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM News WHERE archived = 0 AND category LIKE :specified_category")
    List<News> getNews(String specified_category);

    @Query("SELECT * FROM News WHERE entryid = :newsId")
    News getNewsById(int newsId);

    @Query("SELECT * FROM News WHERE archived = 1")
    List<News> getArchivedNews();

    /**
     * Insert news (item) in the database. If the news (item) already exists, replace it.
     *
     * @param news to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(News news);


    /**
     * Delete a news (item) by id.
     *
     * @return the number of news (items) deleted. This should always be 1.
     */
    @Query("DELETE FROM News WHERE entryid = :newsId")
    int deleteNewsById(int newsId);

    @Query("DELETE FROM News")
    void deleteNews();

    /**
     * Refresh news table for a specific category, keeping only archived news (items)
     *
     * @return the number of news deleted.
     */
    @Query("DELETE FROM News WHERE archived = 0 AND category LIKE :specified_category")
    int refreshNews(String specified_category);

    @Update
    int updateNews(News news);
}
