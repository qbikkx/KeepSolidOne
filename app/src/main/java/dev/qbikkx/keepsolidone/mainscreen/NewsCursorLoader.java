package dev.qbikkx.keepsolidone.mainscreen;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import dev.qbikkx.keepsolidone.NewsApplication;

/**
 * Load news from Database
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */

public class NewsCursorLoader extends CursorLoader {
    public NewsCursorLoader(Context context, String[] projection) {
        this(context, NewsApplication.getDatabaseAPI().getContentUri(), projection, null, null, null);
    }

    public NewsCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Cursor loadInBackground() {
        return NewsApplication.getDatabaseAPI().queryNews(getProjection(), null, null);
    }
}
