package dev.qbikkx.keepsolidone.mainscreen;

import dev.qbikkx.keepsolidone.BasePresenter;
import dev.qbikkx.keepsolidone.BaseView;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public interface NewsListContract {

    interface View extends BaseView<Presenter> {
        void setRefreshing(boolean isRefreshing);

        void showToast(int stringId);
    }

    interface Presenter extends BasePresenter<View> {

        void loadLatestNewsFromWeb();

    }
}
