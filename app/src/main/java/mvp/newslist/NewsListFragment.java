package mvp.newslist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import dev.qbikkx.keepsolidone.NewsApplication;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.activities.NewsActivity;
import dev.qbikkx.keepsolidone.adapters.NewsListAdapter;
import dev.qbikkx.keepsolidone.listeners.OnNewsItemClickListener;
import dev.qbikkx.keepsolidone.models.NewsResponce;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;
import dev.qbikkx.keepsolidone.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsListFragment extends AppCompatDialogFragment implements NewsListContract.NewsListView {

    NewsListContract.NewsListPresenter mPresenter;

    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsListAdapter(mPresenter.loadNewsFromStorage(),
                new OnNewsItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = NewsActivity.newIntent(getActivity(), mAdapter.getItem(position).getUrl());
                        startActivity(intent);
                    }
                });
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLatestNewsFromWeb();
            }
        });
        return rootView;
    }

    @Override
    public void setPresenter(NewsListContract.NewsListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void swapCursor(Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtils.showToast(getActivity(), R.string.request_on_failure);
    }

    /**
     * Обновляем список каждый раз при появлении фрагмента на экране
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadLatestNewsFromWeb();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
                mPresenter.loadLatestNewsFromWeb();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        mAdapter.getCursor().close();
        super.onDestroy();
    }
}