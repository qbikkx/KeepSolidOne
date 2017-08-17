package mvp.newslist;

import android.database.Cursor;

import mvp.BaseView;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public interface NewsListContract {
    interface NewsListView extends BaseView<NewsListPresenter> {
        void setRefreshing(boolean isRefreshing);

        void swapCursor(Cursor cursor);

        void showToast(int stringId);
    }

    interface NewsListPresenter {
        void loadLatestNewsFromWeb();

        Cursor loadNewsFromStorage();
    }
}
