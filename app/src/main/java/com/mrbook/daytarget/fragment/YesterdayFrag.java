package com.mrbook.daytarget.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrbook.daytarget.Day;
import com.mrbook.daytarget.Item;
import com.mrbook.daytarget.ItemAdapter;
import com.mrbook.daytarget.ItemAdapter2;
import com.mrbook.daytarget.ItemAdapter3;
import com.mrbook.daytarget.MyDatabase;
import com.mrbook.daytarget.R;

public class YesterdayFrag extends Fragment {
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private FloatingActionButton actionButton;
    private ItemAdapter adapter1;
    private ItemAdapter2 adapter2;
    private ItemAdapter3 adapter3;
    private Day yesterday;
    private MyDatabase database;

    public void setDatabase(MyDatabase database) {
        this.database = database;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        initComponent(view);
        initData();
        init();
        return view;
    }

    private void initData() {
        yesterday = new Day();
        yesterday.name = "yesterday";
        SQLiteDatabase data = database.getWritableDatabase();
        Cursor cursor = data.query("yesterday", null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(cursor.getColumnIndex("time"));
                boolean checked = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("checked")));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                if (time.equals("上午"))
                    yesterday.getMorning().add(new Item(checked, content));
                else if (time.equals("下午"))
                    yesterday.getAfternoon().add(new Item(checked, content));
                else if (time.equals("晚上"))
                    yesterday.getEvening().add(new Item(checked, content));
            } while (cursor.moveToNext());
        }
    }

    private void initComponent(View view) {
        recyclerView1 = view.findViewById(R.id.recyclerView);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);
        actionButton = view.findViewById(R.id.fab);
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void init() {
        adapter1 = new ItemAdapter(yesterday, database);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(adapter1);
        adapter2 = new ItemAdapter2(yesterday, database);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setAdapter(adapter2);
        adapter3 = new ItemAdapter3(yesterday, database);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3.setAdapter(adapter3);
    }

}
