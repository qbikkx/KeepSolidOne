package dev.qbikkx.keepsolidone.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.network.NewsAPIService;
import dev.qbikkx.keepsolidone.utils.ActivityUtils;

/**
 * NewsListActivity.
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsListActivity extends DaggerAppCompatActivity {
    private final static String TAG = "NewsListActivity";

    @Inject
    NewsListPresenter mNewsListPresenter;

    @Inject
    Lazy<NewsListFragment> mNewsListFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startFragment();
    }

    private void startFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsListFragment fragment = (NewsListFragment) fragmentManager.findFragmentById(R.id.fl_fragment_container);
        if (fragment == null) {
            fragment = mNewsListFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.fl_fragment_container);
        }
    }
}
