package com.mrbook.daytarget;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter3 extends RecyclerView.Adapter<ItemAdapter3.ViewHolder> {
    private Day day;
    private MyDatabase database;
    public ItemAdapter3(Day day, MyDatabase database) {
        this.day = day;
        this.database = database;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Item> evening = day.getEvening();
        boolean checked = evening.get(position).isChecked();
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("checked", checked + "");
        db.update("day", values, "dateid = ? and time_id = ?", new String[] {day.getDate(),
                position+1+"e"});
        if (checked) {
            holder.textView.setTextColor(Color.parseColor("#cdcdcd"));
            holder.checkBox.setChecked(checked);
        } else {
            holder.textView.setTextColor(Color.parseColor("#000000"));
            holder.checkBox.setChecked(checked);
        }
        holder.textView.setText(evening.get(position).getText());
        final int ps = position;
        final boolean checkBox_checked = checked;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox_checked)
                    day.getEvening().get(ps).setChecked(false);
                else day.getEvening().get(ps).setChecked(true);
                notifyItemChanged(ps);
            }
        });
    }

    @Override
    public int getItemCount() {
        return day.getEvening().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
