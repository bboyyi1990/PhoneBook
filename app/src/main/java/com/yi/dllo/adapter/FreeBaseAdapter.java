package com.yi.dllo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yi.dllo.bean.FreeListView;
import com.yi.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/1/9.
 */
public class FreeBaseAdapter extends BaseAdapter {

    private ArrayList<FreeListView> data;
    private Context context;

    public FreeBaseAdapter(Context context) {
        this.context = context;
        getData();//得到联系人数据方法
    }

    private void getData() {
        data = new ArrayList<>();

        Bitmap inviteMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.o5);
        Bitmap flowerMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a7b);

        data.add(0, new FreeListView("花名册", null, flowerMap));
        data.add(1, new FreeListView("邀请好友", null, inviteMap));

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);//获取联系人 Id
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(columnIndex);
            Cursor phone_cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone._ID + "=" + contactId, null, null);
            if (phone_cursor != null) {
                while (phone_cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));

                    String photoId = phone_cursor.getString(phone_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                    Cursor c = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data.DATA15}, ContactsContract.Data._ID + "=" + photoId, null, null);
                    c.moveToFirst();
                    byte array[] = null;
                    try {
                        array = c.getBlob(0);//当手机联系人没有头像的时候
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    if (array != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                        data.add(new FreeListView(name, number, bitmap));
                    }
                    c.close();
                }
            }
            phone_cursor.close();
        }
        cursor.close();
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_linkman_free, null);
            holder.handimage = (ImageView) convertView.findViewById(R.id.linkman_free_listview_hand);
            holder.name = (TextView) convertView.findViewById(R.id.linkman_free_listview_name);
            holder.number = (TextView) convertView.findViewById(R.id.linkman_free_listview_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FreeListView freeListView = (FreeListView) getItem(position);
        holder.handimage.setImageBitmap(freeListView.getBitmap());
        holder.name.setText(freeListView.getName());
        holder.number.setText(freeListView.getNumber());
        holder.handimage.setImageBitmap(freeListView.getBitmap());
        return convertView;
    }

    public class ViewHolder {
        TextView name, number;
        ImageView handimage;
    }
}
