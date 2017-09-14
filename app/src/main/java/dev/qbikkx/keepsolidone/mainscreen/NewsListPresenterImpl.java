package dev.qbikkx.keepsolidone.mainscreen;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import dev.qbikkx.keepsolidone.NewsApplication;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.models.NewsResponce;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsListPresenterImpl implements NewsListContract.NewsListPresenter {
    private NewsListContract.NewsListView mView;

    public NewsListPresenterImpl(NewsListContract.NewsListView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadLatestNewsFromWeb() {
        mView.setRefreshing(true);
        NewsApplication.getWebAPI().getNews("latest").enqueue(new Callback<NewsResponce>() {
            @Override
            public void onResponse(Call<NewsResponce> call, Response<NewsResponce> response) {
                NewsResponce newsResponce = response.body();
                if (newsResponce != null) {
                    if (newsResponce.getStatus().equals("ok")) {
                        NewsApplication.getDatabaseAPI().addNewsMany(newsResponce.getArticles());
                    } else {
                        mView.showToast(R.string.bad_responce);
                    }
                } else {
                    mView.showToast(R.string.null_responce);
                }
                mView.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewsResponce> call, Throwable t) {
                mView.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }
}
