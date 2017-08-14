package dev.qbikkx.keepsolidone.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "newsDataBase.db";

    public NewsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " +
                NewsTable.NAME +
                "( " + NewsTable.Cols._ID + " integer primary key autoincrement, " +
                NewsTable.Cols.AUTHOR + ", " +
                NewsTable.Cols.TITLE + ", " +
                NewsTable.Cols.DESC + ", " +
                NewsTable.Cols.URL + " text not null unique, " +
                NewsTable.Cols.URL_TO_IMAGE + ", " +
                NewsTable.Cols.PUBLISHED_AT + " integer )";
        db.execSQL(sql);

        db.execSQL("CREATE TRIGGER insert_news_trigger AFTER INSERT ON " + NewsTable.NAME +
                " WHEN (SELECT count(*) FROM " + NewsTable.NAME + ") > " + NewsTable.MAX_CAPACITY +
                " BEGIN " +
                    " delete from " + NewsTable.NAME +
                    " where " + NewsTable.Cols.PUBLISHED_AT +
                            " = (select min(" + NewsTable.Cols.PUBLISHED_AT +
                                ") from " + NewsTable.NAME + ");" +
                " END;"
            );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo migrate
        db.execSQL("DROP TABLE IF EXISTS " + NewsTable.NAME);
        onCreate(db);
    }
}
