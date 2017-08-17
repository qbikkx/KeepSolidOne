package mvp.newslist;

import android.database.Cursor;
import android.view.View;

import dev.qbikkx.keepsolidone.NewsApplication;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.models.NewsResponce;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;
import dev.qbikkx.keepsolidone.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsListPresenterImpl implements NewsListContract.NewsListPresenter {
    private NewsListContract.NewsListView mView;

    /**
     * Optimize query
     * TODO: Плохая гибкость, куча зависимостей
     */
    private String[] COLUMNS = {NewsTable.Cols._ID, NewsTable.Cols.TITLE,
            NewsTable.Cols.PUBLISHED_AT, NewsTable.Cols.URL_TO_IMAGE, NewsTable.Cols.URL};

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
                        mView.swapCursor(loadNewsFromStorage());
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
    public Cursor loadNewsFromStorage() {
        return NewsApplication.getDatabaseAPI().queryNews(COLUMNS, null, null);
    }
}
