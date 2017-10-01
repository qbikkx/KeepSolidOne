package dev.qbikkx.keepsolidone.mainscreen;


import javax.annotation.Nullable;
import javax.inject.Inject;

import dev.qbikkx.keepsolidone.NewsApplication;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.di.ActivityScoped;
import dev.qbikkx.keepsolidone.models.NewsResponce;
import dev.qbikkx.keepsolidone.network.NewsAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
@ActivityScoped
public class NewsListPresenter implements NewsListContract.Presenter {
    @Nullable
    private NewsListContract.View mView;

    @Inject
    NewsListPresenter(){}

    @Inject
    NewsAPIService newsAPIService;

    @Override
    public void loadLatestNewsFromWeb() {
        mView.setRefreshing(true);
        newsAPIService.getNews("latest").enqueue(new Callback<NewsResponce>() {
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

    @Override
    public void takeView(NewsListContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
