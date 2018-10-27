package com.mrbook.daytarget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private Context mContext;
    private static final String CREATE_TODAY = "create table today (" +
            "id integer primary key autoincrement, " +
            "time text, " +
            "time_id text, " +
            "checked text, " +
            "content text)";
    private static final String CREATE_YESTERDAY = "create table yesterday (" +
            "id integer primary key autoincrement, " +
            "time text, " +
            "time_id text, " +
            "checked text, " +
            "content text)";
    private static final String CREATE_TOMORROW = "create table tomorrow (" +
            "id integer primary key autoincrement, " +
            "time text, " +
            "time_id text, " +
            "checked text, " +
            "content text)";
    public static final String CREATE_TIME = "create table time (" +
            "id integer primary key autoincrement, " +
            "year text, " +
            "month text, " +
            "mydate text)";
    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TODAY);
        sqLiteDatabase.execSQL(CREATE_YESTERDAY);
        sqLiteDatabase.execSQL(CREATE_TOMORROW);
        sqLiteDatabase.execSQL(CREATE_TIME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
