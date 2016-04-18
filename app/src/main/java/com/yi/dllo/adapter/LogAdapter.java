package com.yi.dllo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yi.dllo.activity.OtherActivity;
import com.yi.dllo.bean.LogListView;
import com.yi.dllo.fragment.Message;
import com.yi.dllo.phonebook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dllo on 16/1/8.
 */
public class LogAdapter extends BaseAdapter {

    private ArrayList<LogListView> data;
    private Context context;
    private ImageView imageView;

    public LogAdapter(Context context) {

        this.context = context;

        init();//适配器获取通话记录信息
    }

    private void init() {
        data = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " desc");
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
            String time = dateFormat.format(date);

            data.add(new LogListView(name, number, null, time));

        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_log, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.number = (TextView) convertView.findViewById(R.id.num);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.next = (ImageView) convertView.findViewById(R.id.next);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogListView person = (LogListView) getItem(position);

        holder.number.setText(person.getNumber());
        holder.type.setText(person.getType());
        holder.time.setText(person.getTime());
        holder.name.setText(person.getName());
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherActivity.class);
                intent.putExtra("name", data.get(position).getName());
                intent.putExtra("number", data.get(position).getNumber());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView name, type, number, time;
        ImageView next;

    }
}
