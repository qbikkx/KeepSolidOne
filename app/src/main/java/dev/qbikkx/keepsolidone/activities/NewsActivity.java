package dev.qbikkx.keepsolidone.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;

import dev.qbikkx.keepsolidone.NewsApplication;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.adapters.NewsPagerAdapter;
import dev.qbikkx.keepsolidone.fragments.NewsFragment;
import dev.qbikkx.keepsolidone.models.News;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;

/**
 * Activity which call the external Email Sender Activity
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsActivity extends AppCompatActivity {
    public static final String EXTRA_NEWS_URL = "dev.qbikkx.keepsolidone.activities.NewsActivity.url";

    private ViewPager mPager;
    private NewsPagerAdapter mAdapter;

    public static Intent newIntent(Context ctx, Uri url) {
        Intent intent = new Intent(ctx, NewsActivity.class);
        intent.putExtra(EXTRA_NEWS_URL, url);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mPager = (ViewPager) findViewById(R.id.vp_news_pager);
        mAdapter = new NewsPagerAdapter(this, getSupportFragmentManager(),
                NewsApplication.getDatabaseAPI().queryNews(new String[]{NewsTable.Cols.URL, "_id"},
                        null, null));
        mPager.setAdapter(mAdapter);
        Intent intent = getIntent();
        if (intent != null) {
            Uri url = getIntent().getParcelableExtra(EXTRA_NEWS_URL);
            Cursor cursor = mAdapter.getCursor();
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Uri curUrl = Uri.parse(cursor.getString(cursor.getColumnIndex(NewsTable.Cols.URL)));
                if (curUrl.equals(url)) {
                    mPager.setCurrentItem(cursor.getPosition());
                    break;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
