package com.example.onetwofour.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "myVocabulary";
    static final int dbVersion = 1;

    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMyVocab = "CREATE TABLE MyVocab(" +
                "keyword TEXT , " +
                "mota TEXT )";
        db.execSQL(createTableMyVocab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableMyVocab = "DROP TABLE IF EXISTS MyVocab";
        db.execSQL(dropTableMyVocab);

        onCreate(db);
    }
}
