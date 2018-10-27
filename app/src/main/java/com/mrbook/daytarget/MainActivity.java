package com.mrbook.daytarget;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mrbook.daytarget.fragment.TodayFrag;
import com.mrbook.daytarget.fragment.TomorrowFrag;
import com.mrbook.daytarget.fragment.YesterdayFrag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String> titles;
    private PagerAdapter adapter;
    private int lyear;
    private int lmonth;
    private int ldate;
    private int year;
    private int month;
    private int date;
    private MyDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        database = new MyDatabase(this, "MyDatabase.db", null, 1);

        init();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void init() {
        SQLiteDatabase data = database.getWritableDatabase();
        Cursor cursor = data.query("time", null, null,null, null,
                null,null,null);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = calendar.get(Calendar.DATE);
        if (cursor.moveToFirst()) {
            do {
                lyear = Integer.parseInt(cursor.getString(cursor.getColumnIndex("year")));
                lmonth = Integer.parseInt(cursor.getString(cursor.getColumnIndex("month")));
                ldate = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mydate")));
            } while (cursor.moveToNext());
        } else {
            lyear = year;
            lmonth = month;
            ldate = date;
        }
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        titles.add("昨天");
        titles.add("今天");
        titles.add("明天");
        YesterdayFrag yesterdayFrag = new YesterdayFrag();
        TodayFrag todayFrag = new TodayFrag();
        TomorrowFrag tomorrowFrag = new TomorrowFrag();
        yesterdayFrag.setDatabase(database);
        yesterdayFrag.lyear = lyear;
        yesterdayFrag.lmonth = lmonth;
        yesterdayFrag.ldate = ldate;
        todayFrag.setDatabase(database);
        todayFrag.lyear = lyear;
        todayFrag.lmonth = lmonth;
        todayFrag.ldate = ldate;
        tomorrowFrag.setDatabase(database);
        tomorrowFrag.lyear = lyear;
        tomorrowFrag.lmonth = lmonth;
        tomorrowFrag.ldate = ldate;

        fragments.add(yesterdayFrag);
        fragments.add(todayFrag);
        fragments.add(tomorrowFrag);
        adapter = new PagerAdapter(getSupportFragmentManager(), titles, fragments);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SQLiteDatabase data = database.getWritableDatabase();
        Cursor cursor = data.query("time", null, null,null, null,
                null,null,null);
        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put("year", year);
            values.put("month", month);
            values.put("mydate", date);
            data.insert("time", null, values);
        }
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("month", month);
        values.put("mydate", date);
        data.update("time", values, null, null);
    }
}
