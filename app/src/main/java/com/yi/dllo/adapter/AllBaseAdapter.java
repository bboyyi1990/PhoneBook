package com.yi.dllo.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yi.dllo.bean.AllContact;
import com.yi.dllo.bean.AllListView;
import com.yi.dllo.bean.FreeListView;
import com.yi.dllo.bean.SonInfo;
import com.yi.dllo.db.DBTool;
import com.yi.dllo.phonebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/1/9.
 */
public class AllBaseAdapter extends BaseExpandableListAdapter {
    private AllContact data;
    private Context context;
    private DBTool dbTool;


    public AllBaseAdapter(Context context) {
        this.context = context;
        init();//加载数据

    }

    private void init() {
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        data = new AllContact();

//        Bitmap frontMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.j3);
//        data.Info(new SonInfo("分组", null, frontMap));

        int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(columnIndex);
            Cursor phone_cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone._ID + "=" + contactId, null, null);
            if (phone_cursor != null) {
                while (phone_cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    dbTool = new DBTool(context);
                    dbTool.insertData(name, number);

                    String photoId = phone_cursor.getString(phone_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                    Cursor c = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data.DATA15}, ContactsContract.Data._ID + "=" + photoId, null, null);
                    c.moveToFirst();
                    byte array[] = null;
                    try {
                        array = c.getBlob(0);
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    if (array != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                        data.Info(new SonInfo(name, number, bitmap));
                    }

                }
            }


        }
    }

    @Override
    public int getGroupCount() {
        return data.getFatherCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.getSonCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.FatherInfo(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.getSonInfo(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FatherViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_listview_father, null);
            holder = new FatherViewHolder();
            holder.father = (TextView) convertView.findViewById(R.id.linkeman_all_expandablelistview_father_tv);
            convertView.setTag(holder);
        } else {
            holder = (FatherViewHolder) convertView.getTag();
        }
        holder.father.setText(data.FatherInfo(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SonViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_listview_son, null);
            holder = new SonViewHolder();
            holder.sonHand = (ImageView) convertView.findViewById(R.id.linkemam_all_expandable_listview_son_hand);
            holder.sonName = (TextView) convertView.findViewById(R.id.linkman_all_expandable_listview_son_name);
            holder.sonNumber = (TextView) convertView.findViewById(R.id.linkman_all_expandable_listview_son_number);
            convertView.setTag(holder);
        } else {
            holder = (SonViewHolder) convertView.getTag();
        }
        holder.sonName.setText(data.getSonInfo(groupPosition).get(childPosition).getName());
        holder.sonNumber.setText(data.getSonInfo(groupPosition).get(childPosition).getNumber());
        holder.sonHand.setImageBitmap(data.getSonInfo(groupPosition).get(childPosition).getHand_iv());
        return convertView;
    }

    public class FatherViewHolder {
        TextView father;
    }

    public class SonViewHolder {
        TextView sonName, sonNumber;
        ImageView sonHand;
    }

    public int getIndexFromString(String s) {

        for (int i = 0; i < data.getFatherCount(); i++) {
            if (data.FatherInfo(i).equals(s)) {
                return i;
            }
        }
        return -1;
    }
}
