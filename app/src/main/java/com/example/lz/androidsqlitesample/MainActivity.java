package com.example.lz.androidsqlitesample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button createDatabase;

    private Button updateDatabase;

    private Button deleteDatabase;

    private Button insertData;

    private Button batchInsertData;

    private Button queryData;

    private Button queryRawData;

    private Button deleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateDatabase = (Button) findViewById(R.id.update_database);
        updateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 2);
                SQLiteDatabase readableDatabase = extendedSQLiteOpenHelper.getReadableDatabase();
            }
        });

        createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 1);
                SQLiteDatabase db = extendedSQLiteOpenHelper.getReadableDatabase();
            }
        });
        deleteDatabase = (Button) findViewById(R.id.delete_database);
        deleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 2);
                File file = new File(extendedSQLiteOpenHelper.getWritableDatabase().getPath());
                if (file.exists()) {
                    file.delete();
                }
            }
        });

        insertData = (Button) findViewById(R.id.insert);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 1);
                SQLiteDatabase db = extendedSQLiteOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("ID", 1);
                cv.put("NAME", "rose");
                cv.put("SEX", "male");
                cv.put("AGE", 18);
                db.insert("test", null, cv);
                db.close();
            }
        });

        batchInsertData = (Button) findViewById(R.id.batch);
        batchInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 1);
                SQLiteDatabase db = extendedSQLiteOpenHelper.getWritableDatabase();
                try {
                    db.beginTransaction();
                    String sql = "insert into test(ID, NAME, SEX, AGE)" +
                            " values(?,?,?,?)";
                    for (int id = 20; id < 30; id++) {
                        db.execSQL(sql, new Object[]{id, "rose" + id, "male" + id, new Random().nextInt()});
                    }
                    db.setTransactionSuccessful();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });

        queryData = (Button) findViewById(R.id.query);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 1);
                SQLiteDatabase db = extendedSQLiteOpenHelper.getReadableDatabase();
                Cursor cursor = db.query("test", new String[]{"ID", "NAME", "AGE", "SEX"}, "id=?",
                        new String[]{"1"}, null, null, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    String age = cursor.getString(cursor.getColumnIndex("AGE"));
                    String sex = cursor.getString(cursor.getColumnIndex("SEX"));
                    System.out.println("query------->" + "ID:" + id + "name:" + name + " " + "age:" + age + " " + "sex:" + sex);
                }
                cursor.close();
                db.close();
            }
        });

        queryRawData = (Button) findViewById(R.id.query_raw);
        queryRawData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 1);
                SQLiteDatabase db = extendedSQLiteOpenHelper.getReadableDatabase();
                String sql = "select * from test where name like ?";
                Cursor cursor = db.rawQuery(sql, new String[]{"%rose%"});
                if (null != cursor) {
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex("ID"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        String age = cursor.getString(cursor.getColumnIndex("AGE"));
                        String sex = cursor.getString(cursor.getColumnIndex("SEX"));
                        System.out.println("query------->" + "ID:" + id + " name:" + name + " " + "age:" + age + " " + "sex:" + sex);
                    }
                    cursor.close();
                }
                db.close();

            }
        });

        deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedSQLiteOpenHelper extendedSQLiteOpenHelper = new ExtendedSQLiteOpenHelper(v.getContext(), "stu", null, 1);
                SQLiteDatabase db = extendedSQLiteOpenHelper.getWritableDatabase();
                String whereClauses = "name like ?";
                String[] whereArgs = new String[]{"%rose%"};
                db.delete("test", whereClauses, whereArgs);
                db.close();
            }
        });
    }
}
