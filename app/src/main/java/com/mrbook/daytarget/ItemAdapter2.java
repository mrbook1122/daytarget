package com.mrbook.daytarget;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter2 extends RecyclerView.Adapter<ItemAdapter2.ViewHolder> {
    private Day day;
    private MyDatabase database;
    public ItemAdapter2(Day day, MyDatabase database) {
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
        List<Item> afternoon = day.getAfternoon();
        boolean checked = afternoon.get(position).isChecked();
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("checked", checked + "");
        db.update(day.name, values, "time_id = ?", new String[] {position+1+"a"});
        if (checked) {
            holder.textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.checkBox.setChecked(checked);
        } else {
            holder.textView.getPaint().setFlags(Paint.DEV_KERN_TEXT_FLAG);
            holder.checkBox.setChecked(false);
        }
        holder.textView.setText(afternoon.get(position).getText());
        final int ps = position;
        final boolean checkBox_checked = holder.checkBox.isChecked();
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox_checked)
                    day.getAfternoon().get(ps).setChecked(false);
                else day.getAfternoon().get(ps).setChecked(true);
                notifyItemChanged(ps);
            }
        });
    }

    @Override
    public int getItemCount() {
        return day.getAfternoon().size();
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
