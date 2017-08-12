package dev.qbikkx.keepsolidone.recycler;

import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Date;

import dev.qbikkx.keepsolidone.models.News;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */

public class NewsListAdapter extends CursorRecyclerViewAdapter<NewsHolder> {
    private Cursor mCursor;
    private OnNewsItemClickListener mOnNewsItemClickListener;

    public NewsListAdapter(Cursor cursor, OnNewsItemClickListener listener) {
        super(cursor);
        mCursor = cursor;
        mOnNewsItemClickListener = listener;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new NewsHolder(inflater, parent, mOnNewsItemClickListener);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, Cursor cursor) {
        //todo optimize
        News news = new News(Uri.parse(cursor.getString(cursor.getColumnIndex(NewsTable.Cols.URL))));
        news.setTitle(cursor.getString(cursor.getColumnIndex(NewsTable.Cols.TITLE)));
        news.setPublishedAt(new Date(cursor.getLong(cursor.getColumnIndex(NewsTable.Cols.PUBLISHED_AT))));
        news.setUrlToImage(Uri.parse(cursor.getString(cursor.getColumnIndex(NewsTable.Cols.URL_TO_IMAGE))));
        holder.bind(news);
    }

    public News getItem(int position) {
        return getNews(position);
    }

    private News getNews(int positon) {
        if (mCursor.moveToPosition(positon)) {
            return getNews();
        }
        return null;
    }

    private News getNews() {
        String title = mCursor.getString(mCursor.getColumnIndex(NewsTable.Cols.TITLE));
        Uri url = Uri.parse(mCursor.getString(mCursor.getColumnIndex(NewsTable.Cols.URL)));
        Uri urlToImage = Uri.parse(mCursor.getString(mCursor.getColumnIndex(NewsTable.Cols.URL_TO_IMAGE)));
        long date = mCursor.getLong(mCursor.getColumnIndex(NewsTable.Cols.PUBLISHED_AT));
        News news = new News(url);
        news.setTitle(title);
        news.setUrlToImage(urlToImage);
        news.setPublishedAt(new Date(date));
        return news;
    }
}
