package com.mrbook.daytarget.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.EditText;

import com.mrbook.daytarget.Day;
import com.mrbook.daytarget.Item;
import com.mrbook.daytarget.ItemAdapter;
import com.mrbook.daytarget.ItemAdapter2;
import com.mrbook.daytarget.ItemAdapter3;
import com.mrbook.daytarget.MainActivity;
import com.mrbook.daytarget.MyDatabase;
import com.mrbook.daytarget.R;

public class TomorrowFrag extends Fragment {
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
    private Day tomorrow;
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
        tomorrow = new Day();
        tomorrow.name = "tomorrow";
        SQLiteDatabase data = database.getWritableDatabase();
        Cursor cursor = data.query("tomorrow", null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(cursor.getColumnIndex("time"));
                boolean checked = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("checked")));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                if (time.equals("上午"))
                    tomorrow.getMorning().add(new Item(checked, content));
                else if (time.equals("下午"))
                    tomorrow.getAfternoon().add(new Item(checked, content));
                else if (time.equals("晚上"))
                    tomorrow.getEvening().add(new Item(checked, content));
            } while (cursor.moveToNext());
        }
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
        adapter1 = new ItemAdapter(tomorrow, database);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(adapter1);
        adapter2 = new ItemAdapter2(tomorrow, database);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setAdapter(adapter2);
        adapter3 = new ItemAdapter3(tomorrow, database);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3.setAdapter(adapter3);
    }

    private void initActionButton() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionButton1.getVisibility() == View.INVISIBLE) {
                    actionButton1.setVisibility(View.VISIBLE);
                    actionButton2.setVisibility(View.VISIBLE);
                    actionButton3.setVisibility(View.VISIBLE);
                } else {
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
            }
        });
        actionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton("下午");
            }
        });
        actionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton("晚上");
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
                    tomorrow.getMorning().add(new Item(false, s));
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("time", "上午");
                    values.put("time_id", tomorrow.getMorning().size() + "m");
                    values.put("checked", "false");
                    values.put("content", s);
                    db.insert("tomorrow", null, values);
                    adapter1.notifyItemInserted(tomorrow.getMorning().size());
                } else if (time.equals("下午")) {
                    tomorrow.getAfternoon().add(new Item(false, s));
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("time", "下午");
                    values.put("time_id", tomorrow.getAfternoon().size() + "a");
                    values.put("checked", "false");
                    values.put("content", s);
                    db.insert("tomorrow", null, values);
                    adapter2.notifyItemInserted(tomorrow.getAfternoon().size());
                } else if (time.equals("晚上")) {
                    tomorrow.getEvening().add(new Item(false, s));
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("time", "晚上");
                    values.put("time_id", tomorrow.getEvening().size() + "e");
                    values.put("checked", "false");
                    values.put("content", s);
                    db.insert("tomorrow", null, values);
                    adapter3.notifyItemInserted(tomorrow.getEvening().size());
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
