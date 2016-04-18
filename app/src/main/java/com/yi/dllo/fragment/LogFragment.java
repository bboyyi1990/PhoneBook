package com.yi.dllo.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yi.dllo.activity.OtherActivity;
import com.yi.dllo.adapter.LogAdapter;
import com.yi.dllo.bean.LogListView;
import com.yi.dllo.phonebook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by dllo on 16/1/7.
 */
public class LogFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private LogAdapter logAdapter;
    private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, null);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initView();
    }

    private void initView() {


        listView = (ListView) getView().findViewById(R.id.lise_view);
        toolbar = (Toolbar) getView().findViewById(R.id.log_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        logAdapter = new LogAdapter(getActivity());
        listView.setAdapter(logAdapter);
        toolbar.setTitle("通话记录");


        //listview设置监听事件
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.log_menu, menu);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }
}
