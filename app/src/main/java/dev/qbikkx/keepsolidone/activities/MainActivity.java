package dev.qbikkx.keepsolidone.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import dev.qbikkx.keepsolidone.R;
import mvp.newslist.NewsListContract;
import mvp.newslist.NewsListFragment;
import mvp.newslist.NewsListPresenterImpl;

/**
 * MainActivity.
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private NewsListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFragment();
        initPresenter();
    }

    private void startFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = (NewsListFragment) fragmentManager.findFragmentById(R.id.fl_fragment_container);
        if (mFragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            mFragment = NewsListFragment.newInstance();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.add(R.id.fl_fragment_container, mFragment);
            transaction.commit();
        }
    }

    private void initPresenter() {
        new NewsListPresenterImpl(mFragment);
    }
}
