package com.yi.dllo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yi.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/1/7.
 */
public class DialAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> data;
    private String title[] = {"拨号", "通话记录", "联系人", "短信"};
    private int imageview[] = {R.drawable.dial_imageview_selector, R.drawable.log_imageview_selector, R.drawable.linkman_imageview_selector, R.drawable.message_imageview_selector};
    private Context context;
    private TextView tv;
    private ImageView iv;

    public DialAdapter(FragmentManager fm, ArrayList<Fragment> data, Context context) {
        super(fm);
        this.data = data;
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_tab, null);

        tv = (TextView) view.findViewById(R.id.tv);
        iv = (ImageView) view.findViewById(R.id.iv);
        tv.setText(title[position]);
        iv.setImageResource(imageview[position]);

        return view;
    }
}
