package com.mrbook.daytarget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private Context mContext;
    private static final String CREATE_TODAY = "create table day (" +
            "id integer primary key autoincrement, " +
            "dateid text, " +
            "time text, " +
            "time_id text, " +
            "checked text, " +
            "content text)";
    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TODAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
