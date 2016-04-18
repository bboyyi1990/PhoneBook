package com.yi.dllo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yi.dllo.Interface.MessageIterface;
import com.yi.dllo.activity.PersonActivity;
import com.yi.dllo.adapter.MessageRecyelerAdapterSms;
import com.yi.dllo.bean.SmsByContectBean;
import com.yi.dllo.bean.SmsData;
import com.yi.dllo.phonebook.R;

import java.util.List;

/**
 * Created by dllo on 16/1/7.
 */
public class Message extends Fragment implements MessageIterface {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MessageRecyelerAdapterSms recyclerAdapter;
    private SmsData smsData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_message, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);//让Fragment获得菜单的功能,Activity有这个功能,因此不需要而Fragment没有需要这句话
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.message_toolbar);
        toolbar.setTitle("短信");
        toolbar.setTitleTextColor(Color.RED);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        smsData = new SmsData(getActivity());

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.message_recyclerview);
        recyclerAdapter = new MessageRecyelerAdapterSms();
        recyclerAdapter.addData(smsData);
        recyclerAdapter.setSmsDataListener(this);
        recyclerView.setAdapter(recyclerAdapter);

        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
        gm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gm);
    }

    @Override//生成菜单
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.message_menu, menu);
    }

    @Override//菜单选项监听
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message_toolbar_menu1:
                showDiaLog();//发短信 dialog
                break;
            case R.id.message_toolbar_menu2:
                Toast.makeText(getActivity(), "波艮!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.message_toolbar_menu3:
                Toast.makeText(getActivity(), "啊斗艮!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("                                 编辑短信");
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_message_toolbat_menu_editmessage, null);
        builder.setView(view);

        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText body = (EditText) view.findViewById(R.id.dialog_body);
                EditText number = (EditText) view.findViewById(R.id.dialog_number);
                String phoneNumber = number.getText().toString();
                String smsBody = body.getText().toString();

                if (phoneNumber.isEmpty() | smsBody.isEmpty()) {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();

                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    List<String> b = smsManager.divideMessage(smsBody);//分割短信字符串,当短息长度超过70个字 会再发一条短信 一条短信是一个元素,不是字符

                    for (String sms : b) { //这里遍历的是字符串不是字符
                        smsManager.sendTextMessage(phoneNumber, null, sms, null, null);
                    }

                    Toast.makeText(getContext(), "短信发送成功", Toast.LENGTH_SHORT).show();

                    try {
                        new Thread().sleep(200);//实时刷新发送信息
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    smsData = new SmsData(getContext());
                    recyclerAdapter.addData(smsData);
                }
            }
        });

        builder.setNeutralButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 保存草稿
                EditText body = (EditText) view.findViewById(R.id.dialog_body);
                EditText number = (EditText) view.findViewById(R.id.dialog_number);
                String phoneNumber = number.getText().toString();
                String smsBody = body.getText().toString();

                SharedPreferences sp = getContext().getSharedPreferences("Draft", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("NUMBER", phoneNumber);
                editor.putString("BODY", smsBody);
                editor.commit();
                Toast.makeText(getContext(), "草稿保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override//RecyelerListView监听事件
    public void messageItemlistener(SmsByContectBean smsByContectBean, int position) {

        Toast.makeText(getActivity(), "电话号: " + smsData.getSmsByContectBeans().get(position).getNumber() + "   类型: " + smsData.getSmsByContectBeans().get(0).getSingleSmsBeans().get(0).getType() + "    注意:上面我弹出一条广播", Toast.LENGTH_SHORT).show();
        //自定义跳转传值
        PersonActivity.startJump(getContext(), smsData.getSmsByContectBeans().get(position), recyclerAdapter);
        //发送广播
        getContext().sendBroadcast(new Intent("com.yi.dllo.phonebook.touchMessage"));
    }

}
