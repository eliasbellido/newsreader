package com.beyondthecode.newsreader.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "news")
public class News {

    @PrimaryKey
    @ColumnInfo(name = "entryid")
    private int id;

    @Ignore
    @SerializedName("source")
    @Expose
    private SourceData sourceData;

    @ColumnInfo(name = "sourcce")
    private String sourceDataString;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    private String author;

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    private String publishedAt;

    @ColumnInfo(name = "archived")
    private boolean archived;

    @ColumnInfo(name = "category")
    private String category;

    public News(String title, String author, String url, String description, String urlToImage, String publishedAt, String category, boolean archived) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.archived = archived;
        this.category = category;
        this.description = description;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    @Ignore
    public News(int id, String title, String author, boolean archived, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.archived = archived;
        this.category = category;
    }

    @Ignore
    public News(String title, String author, boolean archived, String category) {
        this.title = title;
        this.author = author;
        this.archived = archived;
        this.category = category;
    }

    @Ignore
    public News(String title, String author, boolean archived, String category, String publishedAt) {
        this.title = title;
        this.author = author;
        this.archived = archived;
        this.category = category;
        this.publishedAt = publishedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SourceData getSourceData() {
        return sourceData;
    }

    public void setSourceData(SourceData sourceData) {
        this.sourceData = sourceData;
    }

    public String getSourceDataString() {
        return sourceDataString;
    }

    public void setSourceDataString(String sourceDataString) {
        this.sourceDataString = sourceDataString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return (title + author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(title, news.title) &&
                Objects.equals(url, news.url) &&
                Objects.equals(description, news.description) &&
                Objects.equals(category, news.category);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, description);
    }

    /*---------------inner class-----------*/

    public class SourceData{
        @SerializedName("name")
        @ColumnInfo(name = "source_name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
