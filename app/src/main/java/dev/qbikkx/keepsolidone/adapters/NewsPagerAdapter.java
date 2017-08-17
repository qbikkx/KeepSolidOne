package dev.qbikkx.keepsolidone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import mvp.newsdetails.NewsFragment;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsPagerAdapter extends CursorFragmentPagerAdapter {

    public NewsPagerAdapter(Context ctx, FragmentManager fm, Cursor cursor) {
        super(ctx, fm, cursor);
    }

    @Override
    public Fragment getItem(Context context, Cursor cursor) {
        Uri url = Uri.parse(cursor.getString(cursor.getColumnIndex(NewsDbSchema.NewsTable.Cols.URL)));
        return NewsFragment.newInstance(url);
    }
}
