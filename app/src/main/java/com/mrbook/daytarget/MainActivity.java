package com.mrbook.daytarget;

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
        todayFrag.setDatabase(database);
        tomorrowFrag.setDatabase(database);
        fragments.add(yesterdayFrag);
        fragments.add(todayFrag);
        fragments.add(tomorrowFrag);
        adapter = new PagerAdapter(getSupportFragmentManager(), titles, fragments);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
    }

}
