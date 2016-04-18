package com.yi.dllo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.yi.dllo.util.IndexView;
import com.yi.dllo.adapter.AllBaseAdapter;
import com.yi.dllo.bean.SonInfo;
import com.yi.dllo.phonebook.R;

import java.util.ArrayList;


/**
 * Created by dllo on 16/1/9.
 */
public class AllTabFragment extends Fragment implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    private ExpandableListView listView;
    private AllBaseAdapter allBaseAdapter;
    private IndexView indexView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, null);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        listView = (ExpandableListView) getView().findViewById(R.id.linkman_all_listview);
        indexView = (IndexView) getView().findViewById(R.id.linkman_all_IndexView);

        allBaseAdapter = new AllBaseAdapter(getActivity());
        listView.setAdapter(allBaseAdapter);
        indexView.setExpandableListView(listView);


        listView.setOnGroupClickListener(this);
        listView.setOnChildClickListener(this);
    }


    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

        return false;

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        ArrayList<SonInfo> data = (ArrayList<SonInfo>) allBaseAdapter.getChild(groupPosition, childPosition);
        String number = data.get(childPosition).getNumber();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + number));
        startActivity(intent);
        return false;
    }
}
