package com.example.lz.androidsqlitesample;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lz on 2016/7/12.
 */
public class ExtendedSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = ExtendedSQLiteOpenHelper.class.getSimpleName();

    String TABLENAME = "test";

    public ExtendedSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ExtendedSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "create database " + db.getPath());
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "upgrading database from version " + oldVersion + " to " + newVersion);
//        if (newVersion > oldVersion) {}如果数据库版本相同，不会进入onUpgrade方法
        updateTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        Log.v(TAG, "create table " + TABLENAME);
        db.execSQL("CREATE TABLE " + TABLENAME +
                " (ID TEXT NOT NULL PRIMARY KEY, " +
                " NAME TEXT NOT NULL, " +
                " SEX VARCHAR NOT NULL, " +
                " AGE INTEGER NOT NULL) ");
    }

    private void updateTable(SQLiteDatabase db) {
        Log.v(TAG, "update table " + TABLENAME);
        db.execSQL("ALTER TABLE " + TABLENAME + " ADD COLUMN ADDRESS TEXT;");
    }
}
