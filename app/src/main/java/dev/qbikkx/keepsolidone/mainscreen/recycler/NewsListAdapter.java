package dev.qbikkx.keepsolidone.mainscreen.recycler;

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
    private OnNewsItemClickListener mOnNewsItemClickListener;

    public NewsListAdapter(Cursor cursor, OnNewsItemClickListener listener) {
        super(cursor);
        mOnNewsItemClickListener = listener;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new NewsHolder(inflater, parent, mOnNewsItemClickListener);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, Cursor cursor) {
        News news = getNews(cursor);
        holder.bind(news);
    }

    public News getItem(int position) {
        return getNews(position);
    }

    private News getNews(int positon) {
        if (getCursor().moveToPosition(positon)) {
            return getNews(getCursor());
        }
        return null;
    }

    /**
     * Отбираем только необходимые столбцы
     */
    private News getNews(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(NewsTable.Cols.TITLE));
        Uri url = Uri.parse(cursor.getString(cursor.getColumnIndex(NewsTable.Cols.URL)));
        Uri urlToImage = Uri.parse(cursor.getString(cursor.getColumnIndex(NewsTable.Cols.URL_TO_IMAGE)));
        long date = cursor.getLong(cursor.getColumnIndex(NewsTable.Cols.PUBLISHED_AT));
        News news = new News(url);
        news.setTitle(title);
        news.setUrlToImage(urlToImage);
        news.setPublishedAt(new Date(date));
        return news;
    }
}
