package com.mrbook.daytarget.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;

import com.mrbook.daytarget.Day;
import com.mrbook.daytarget.Item;
import com.mrbook.daytarget.ItemAdapter;
import com.mrbook.daytarget.ItemAdapter2;
import com.mrbook.daytarget.ItemAdapter3;
import com.mrbook.daytarget.MainActivity;
import com.mrbook.daytarget.MyDatabase;
import com.mrbook.daytarget.R;

import java.util.Calendar;

public class TodayFrag extends Fragment {
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private FloatingActionButton actionButton;
    private FloatingActionButton actionButton1;
    private FloatingActionButton actionButton2;
    private FloatingActionButton actionButton3;
    private ItemAdapter adapter1;
    private ItemAdapter2 adapter2;
    private ItemAdapter3 adapter3;
    private Day today;
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
        initActionButton();
        return view;
    }

    private void initData() {
        today = new Day();
        SQLiteDatabase data = database.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        today.setDate(year, month, date);
        Cursor cursor = data.query("day", null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String dateid = cursor.getString(cursor.getColumnIndex("dateid"));
                if (dateid.equals(today.getDate())) {
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    boolean checked = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("checked")));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    if (time.equals("上午"))
                        today.getMorning().add(new Item(checked, content));
                    else if (time.equals("下午"))
                        today.getAfternoon().add(new Item(checked, content));
                    else if (time.equals("晚上"))
                        today.getEvening().add(new Item(checked, content));
                }
            } while (cursor.moveToNext());
        }
        data.close();
    }

    private void initComponent(View view) {
        recyclerView1 = view.findViewById(R.id.recyclerView);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);
        actionButton = view.findViewById(R.id.fab);
        actionButton1 = view.findViewById(R.id.fab1);
        actionButton2 = view.findViewById(R.id.fab2);
        actionButton3 = view.findViewById(R.id.fab3);
    }

    private void init() {
        adapter1 = new ItemAdapter(today, database, getActivity());
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(adapter1);
        adapter2 = new ItemAdapter2(today, database);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setAdapter(adapter2);
        adapter3 = new ItemAdapter3(today, database);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3.setAdapter(adapter3);
    }

    private void initActionButton() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionButton1.getVisibility() == View.INVISIBLE) {
                    actionButton.setImageResource(R.drawable.cha);
                    actionButton1.setVisibility(View.VISIBLE);
                    actionButton2.setVisibility(View.VISIBLE);
                    actionButton3.setVisibility(View.VISIBLE);
                } else {
                    actionButton.setImageResource(R.drawable.plu);
                    actionButton1.setVisibility(View.INVISIBLE);
                    actionButton2.setVisibility(View.INVISIBLE);
                    actionButton3.setVisibility(View.INVISIBLE);
                }
            }
        });
        actionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton("上午");
                actionButton.setImageResource(R.drawable.plu);
            }
        });
        actionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton("下午");
                actionButton.setImageResource(R.drawable.plu);
            }
        });
        actionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton("晚上");
                actionButton.setImageResource(R.drawable.plu);
            }
        });
    }

    private void initAddButton(final String time) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(getActivity());
        builder.setTitle(time);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = editText.getText().toString();
                if (time.equals("上午")) {
                    today.getMorning().add(new Item(false, s));
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("dateid", today.getDate());
                    values.put("time", "上午");
                    values.put("time_id", today.getMorning().size() + "m");
                    values.put("checked", "false");
                    values.put("content", s);
                    db.insert("day", null, values);
                    db.close();
                    adapter1.notifyItemInserted(today.getMorning().size());
                } else if (time.equals("下午")) {
                    today.getAfternoon().add(new Item(false, s));
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("dateid", today.getDate());
                    values.put("time", "下午");
                    values.put("time_id", today.getAfternoon().size() + "a");
                    values.put("checked", "false");
                    values.put("content", s);
                    db.insert("day", null, values);
                    db.close();
                    adapter2.notifyItemInserted(today.getAfternoon().size());
                } else if (time.equals("晚上")) {
                    today.getEvening().add(new Item(false, s));
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("dateid", today.getDate());
                    values.put("time", "晚上");
                    values.put("time_id", today.getEvening().size() + "e");
                    values.put("checked", "false");
                    values.put("content", s);
                    db.insert("day", null, values);
                    db.close();
                    adapter3.notifyItemInserted(today.getEvening().size());
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setView(editText);
        builder.show();
        actionButton1.setVisibility(View.INVISIBLE);
        actionButton2.setVisibility(View.INVISIBLE);
        actionButton3.setVisibility(View.INVISIBLE);
    }
}