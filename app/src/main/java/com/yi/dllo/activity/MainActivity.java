package com.yi.dllo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yi.dllo.adapter.DialAdapter;
import com.yi.dllo.fragment.DialFragment;
import com.yi.dllo.fragment.LinkManFragment;
import com.yi.dllo.fragment.LogFragment;
import com.yi.dllo.fragment.Message;
import com.yi.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/1/7.
 */
public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DialAdapter adapter;
    private ArrayList<Fragment> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //进入viewpage显示第一个界面( 先滑到第二页再滑到第一页) 就启动变色了
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);

    }


    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        data = new ArrayList<>();
        data.add(new DialFragment());
        data.add(new LogFragment());
        data.add(new LinkManFragment());
        data.add(new Message());

        adapter = new DialAdapter(getSupportFragmentManager(), data, this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));

            }
        }
    }
}
