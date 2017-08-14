package dev.qbikkx.keepsolidone.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import dev.qbikkx.keepsolidone.NewsApplication;
import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.adapters.NewsPagerAdapter;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;

/**
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
        //optimize database request. load only 2 cols
        mAdapter = new NewsPagerAdapter(this, getSupportFragmentManager(),
                NewsApplication.getDatabaseAPI().queryNews(new String[]{NewsTable.Cols.URL, NewsTable.Cols._ID},
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

    /**
     * Пусть кнопка назад на экшн баре ведет себя как Back
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * finish with animation
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
