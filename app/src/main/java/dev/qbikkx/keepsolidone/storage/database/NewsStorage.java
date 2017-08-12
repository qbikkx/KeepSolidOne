package dev.qbikkx.keepsolidone.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Date;
import java.util.List;

import dev.qbikkx.keepsolidone.models.News;
import dev.qbikkx.keepsolidone.storage.DatabaseAPI;
import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;


public class NewsStorage implements DatabaseAPI {
    private static NewsStorage instance;

    private SQLiteDatabase mDatabase;

    public static NewsStorage getInstance(Context ctx) {
        if (instance == null) {
            instance = new NewsStorage(ctx);
        }
        return instance;
    }

    private NewsStorage(Context ctx) {
        Context context = ctx.getApplicationContext();
        mDatabase = new NewsSQLiteOpenHelper(context).getWritableDatabase();
    }

    @Override
    public Cursor queryNews(String[] columns, String whereClause, String[] whereArgs) {
        return mDatabase.query(NewsTable.NAME, columns, whereClause, whereArgs, null, null,
                NewsTable.Cols.PUBLISHED_AT + " DESC");
    }

    @Override
    public void addNews(News news) {
        ContentValues values = getContentValues(news);
        mDatabase.insertWithOnConflict(NewsTable.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public void addNewsMany(List<News> newsList) {
        for (News news : newsList) {
            addNews(news);
        }
    }

    @Override
    public void updateNews(News news) {
        String uuidString = news.getUrl().toString();
        ContentValues values = getContentValues(news);
        mDatabase.update(NewsTable.NAME, values,
                NewsTable.Cols.URL + " = ?", new String[] {uuidString});
    }

    @Override
    public void removeNews(News News) {
        String uuidString = News.getUrl().toString();
        mDatabase.delete(NewsTable.NAME, NewsTable.Cols.URL + " = ?", new String[]{uuidString});
    }

    @Override
    public void clearAll() {
        mDatabase.delete(NewsTable.NAME, null, null);
    }

    private static ContentValues getContentValues(News news) {
        ContentValues values = new ContentValues();
        values.put(NewsTable.Cols.AUTHOR, news.getAuthor());
        values.put(NewsTable.Cols.TITLE, news.getTitle());
        values.put(NewsTable.Cols.DESC, news.getDescription());
        values.put(NewsTable.Cols.URL, news.getUrl().toString());
        Uri urlToImg = news.getUrlToImage();
        values.put(NewsTable.Cols.URL_TO_IMAGE, urlToImg != null ? urlToImg.toString() : null);
        Date d = news.getPublishedAt();
        values.put(NewsTable.Cols.PUBLISHED_AT, d != null ? d.getTime() : null);
        return values;
    }
}
