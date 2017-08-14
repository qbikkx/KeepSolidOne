package dev.qbikkx.keepsolidone.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsResponce {
    @SerializedName("status")
    @Expose
    private String mStatus;

    @SerializedName("source")
    @Expose
    private String mSource;

    @SerializedName("sortBy")
    @Expose
    private String mSortBy;

    @SerializedName("articles")
    @Expose
    private List<News> mArticles = null;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        this.mSource = source;
    }

    public String getSortBy() {
        return mSortBy;
    }

    public void setSortBy(String sortBy) {
        this.mSortBy = sortBy;
    }

    public List<News> getArticles() {
        return mArticles;
    }

    public void setArticles(List<News> articles) {
        this.mArticles = articles;
    }
}
