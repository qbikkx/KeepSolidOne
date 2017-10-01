package dev.qbikkx.keepsolidone.mainscreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.detailscreen.NewsActivity;
import dev.qbikkx.keepsolidone.di.ActivityScoped;
import dev.qbikkx.keepsolidone.mainscreen.recycler.NewsListAdapter;
import dev.qbikkx.keepsolidone.mainscreen.recycler.OnNewsItemClickListener;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;
import dev.qbikkx.keepsolidone.utils.ToastUtils;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
@ActivityScoped
public class NewsListFragment extends DaggerFragment
        implements NewsListContract.View, LoaderManager.LoaderCallbacks<Cursor> {

    @Inject
    NewsListContract.Presenter mPresenter;



    private final static int DB_LOADER_ID = 1;

    private String[] PROJECTION = {NewsTable.Cols._ID, NewsTable.Cols.TITLE,
            NewsTable.Cols.PUBLISHED_AT, NewsTable.Cols.URL_TO_IMAGE, NewsTable.Cols.URL};

    @BindView(R.id.rv_news)
    RecyclerView mRecyclerView;

    private NewsListAdapter mAdapter;

    @BindView(R.id.srl_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    public NewsListFragment(){}

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
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.news_list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsListAdapter(null,
                new OnNewsItemClickListener() {
                    @Override
                    public void onClick(android.view.View v, int position) {
                        Intent intent = NewsActivity.newIntent(getActivity(), mAdapter.getItem(position).getUrl());
                        startActivity(intent);
                    }
                });
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLatestNewsFromWeb();
            }
        });
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
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
        mPresenter.takeView(this);
        //mPresenter.loadLatestNewsFromWeb();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DB_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new NewsCursorLoader(getActivity(), PROJECTION);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.changeCursor(null);
    }
}