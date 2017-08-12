package dev.qbikkx.keepsolidone.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dev.qbikkx.keepsolidone.storage.database.NewsDbSchema.NewsTable;

public class NewsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "newsDataBase.db";

    public NewsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table ");
        stringBuilder.append(NewsTable.NAME);
        stringBuilder.append("( _id integer primary key autoincrement, ");
        stringBuilder.append(NewsTable.Cols.AUTHOR);
        stringBuilder.append(", ");
        stringBuilder.append(NewsTable.Cols.TITLE);
        stringBuilder.append(", ");
        stringBuilder.append(NewsTable.Cols.DESC);
        stringBuilder.append(", ");
        stringBuilder.append(NewsTable.Cols.URL);
        stringBuilder.append(" text not null unique, ");
        stringBuilder.append(NewsTable.Cols.URL_TO_IMAGE);
        stringBuilder.append(", ");
        stringBuilder.append(NewsTable.Cols.PUBLISHED_AT);
        stringBuilder.append(")");
        String sql = stringBuilder.toString();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo migrate
        db.execSQL("DROP TABLE IF EXISTS " + NewsTable.NAME);
        onCreate(db);
    }
}
