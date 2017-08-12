package dev.qbikkx.keepsolidone.models;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Уникальный ключ - URL новости
 */
public class News {
    @SerializedName("author")
    @Expose
    private String mAuthor;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("url")
    @Expose
    private final Uri mUrl;

    @SerializedName("urlToImage")
    @Expose
    private Uri mUrlToImage;

    @SerializedName("publishedAt")
    @Expose
    private Date mPublishedAt;

    public News(Uri url) {
        mUrl = url;
    }
    public News(String author, String title, String description, Uri url, Uri urlToImage, Date publishedAt) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
        mUrl = url;
        mUrlToImage = urlToImage;
        mPublishedAt = publishedAt;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Uri getUrl() {
        return mUrl;
    }

    public Uri getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(Uri urlToImage) {
        mUrlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        mPublishedAt = publishedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        return mUrl.equals(news.mUrl);

    }

    @Override
    public int hashCode() {
        return mUrl.hashCode();
    }

    @Override
    public String toString() {
        return "News{" +
                "mAuthor='" + mAuthor + '\'' +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }
}
