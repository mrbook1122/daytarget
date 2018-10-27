package com.mrbook.daytarget;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Day day;
    private MyDatabase database;
    private Context context;
    public ItemAdapter(Day day, MyDatabase database, Context context) {
        this.day = day;
        this.database = database;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        List<Item> morning = day.getMorning();
        boolean checked = morning.get(position).isChecked();
        SQLiteDatabase db = database.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("checked", checked + "");
        db.update("day", values, "dateid = ? and time_id = ?", new String[] {day.getDate(),
                position+1+"m"});
        if (checked) {
            holder.textView.setTextColor(Color.parseColor("#cdcdcd"));
            holder.checkBox.setChecked(checked);
        } else {
            holder.textView.setTextColor(Color.parseColor("#000000"));
            holder.checkBox.setChecked(checked);
        }
        holder.textView.setText(morning.get(position).getText());
        final int ps = position;
        final boolean checkBox_checked = checked;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox_checked)
                    day.getMorning().get(ps).setChecked(false);
                else day.getMorning().get(ps).setChecked(true);
                notifyItemChanged(ps);
            }
        });
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("修改");
                final EditText editText = new EditText(context);
                editText.setText(holder.textView.getText());
                builder.setView(editText);
                builder.setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase data = database.getWritableDatabase();
                        ContentValues val = new ContentValues();
                        val.put("content", editText.getText().toString());
                        data.update("day", val, "dateid = ? and time_id = ?",
                                new String[] {day.getDate(), ps + 1 + "m"});
                        day.getMorning().get(ps).setText(editText.getText().toString());
                        data.close();
                        notifyItemChanged(ps);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase data = database.getWritableDatabase();
                        data.delete("day", "dateid = ? and time_id = ?",
                                new String[] {day.getDate(), ps + 1 + "m"});
                        day.getMorning().remove(ps);
                        data.close();
                        notifyItemRemoved(ps);

                    }
                });
                builder.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return day.getMorning().size();
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
