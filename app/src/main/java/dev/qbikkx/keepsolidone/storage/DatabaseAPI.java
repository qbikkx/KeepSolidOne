package dev.qbikkx.keepsolidone.storage;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
import android.database.Cursor;

import java.util.List;

import dev.qbikkx.keepsolidone.models.News;

public interface DatabaseAPI {

    Cursor queryNews(String[] columns, String whereClause, String[] whereArgs);

    void addNews(News news);

    void addNewsMany(List<News> newsList);

    void updateNews(News news);

    void removeNews(News news);

    void clearAll();
}
