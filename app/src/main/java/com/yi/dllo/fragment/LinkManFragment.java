package com.yi.dllo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/7.
 */
public class LinkManFragment extends Fragment {

    private TabHost tabHost;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkman, null);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initView();
    }

    private void initView() {
        tabHost = (TabHost) getActivity().findViewById(android.R.id.tabhost);
        tabHost.setup();
        toolbar = (Toolbar) getView().findViewById(R.id.linkman_toolbar);
        toolbar.setTitle("联系人");
        toolbar.setTitleTextColor(Color.RED);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        TabHost.TabSpec all = tabHost.newTabSpec("全部");
        all.setIndicator(getActivity().getLayoutInflater().inflate(R.layout.tab_linkman_all, null));
        all.setContent(R.id.linkman_all);
        tabHost.addTab(all);

        TabHost.TabSpec free = tabHost.newTabSpec("免费");
        free.setIndicator(getActivity().getLayoutInflater().inflate(R.layout.tab_linkman_free, null));
        free.setContent(R.id.linkman_free);
        tabHost.addTab(free);


        //Fragment替换占位布局需要管理类 和业务类
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.linkman_free, new FreeTabFragmeng());
        transaction.replace(R.id.linkman_all, new AllTabFragment());

        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.linkman_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
