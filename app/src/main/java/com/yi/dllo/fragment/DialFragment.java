package com.yi.dllo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.yi.dllo.activity.BaseActivity;
import com.yi.dllo.activity.BaseFragment;
import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/6.
 */
public class DialFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageView number1, number2, number3, number4, number5, number6, number7, number8, number9, number0, numberx, numberj,
            call, delete, sum;
    private EditText editText;
    private String phoneNumber;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dial, null);
        //这view当成一个形式参数定义给我们下面的方法 使下面的方法可以使用view这个参数,view.find
        initView(view);
        return view;

    }

    private void initView(View view) {
        number0 = (ImageView) view.findViewById(R.id.imageview_number0);
        number1 = (ImageView) view.findViewById(R.id.imageview_number1);
        number2 = (ImageView) view.findViewById(R.id.imageview_number2);
        number3 = (ImageView) view.findViewById(R.id.imageview_number3);
        number4 = (ImageView) view.findViewById(R.id.imageview_number4);
        number5 = (ImageView) view.findViewById(R.id.imageview_number5);
        number6 = (ImageView) view.findViewById(R.id.imageview_number6);
        number7 = (ImageView) view.findViewById(R.id.imageview_number7);
        number8 = (ImageView) view.findViewById(R.id.imageview_number8);
        number9 = (ImageView) view.findViewById(R.id.imageview_number9);
        numberx = (ImageView) view.findViewById(R.id.imageview_xing);
        numberj = (ImageView) view.findViewById(R.id.imageview_jing);
        delete = (ImageView) view.findViewById(R.id.imageview_delete);
        call = (ImageView) view.findViewById(R.id.imageview_call);
        editText = (EditText) view.findViewById(R.id.edit_nubmer);
        sum = (ImageView) view.findViewById(R.id.imageview_sum);

        number0.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        numberx.setOnClickListener(this);
        numberj.setOnClickListener(this);
        call.setOnClickListener(this);
        delete.setOnClickListener(this);
        delete.setOnLongClickListener(this);
        sum.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        phoneNumber = editText.getText().toString();

        switch (v.getId()) {
            case R.id.imageview_number0:
                phoneNumber += "0";
                break;
            case R.id.imageview_number1:
                phoneNumber += "1";
                break;
            case R.id.imageview_number2:
                phoneNumber += "2";
                break;
            case R.id.imageview_number3:
                phoneNumber += "3";
                break;
            case R.id.imageview_number4:
                phoneNumber += "4";
                break;
            case R.id.imageview_number5:
                phoneNumber += "5";
                break;
            case R.id.imageview_number6:
                phoneNumber += "6";
                break;
            case R.id.imageview_number7:
                phoneNumber += "7";
                break;
            case R.id.imageview_number8:
                phoneNumber += "8";
                break;
            case R.id.imageview_number9:
                phoneNumber += "9";
                break;
            case R.id.imageview_jing:
                phoneNumber += "#";
                break;
            case R.id.imageview_xing:
                phoneNumber += "*";
                break;
            case R.id.imageview_call:
                if (phoneNumber.isEmpty()) {

                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                    startActivity(intent);
                }

                break;
            case R.id.imageview_delete:

                if (phoneNumber.length() > 0) {
                    phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);

                }

                break;
            case R.id.imageview_sum:

                Intent intent1 = new Intent(Intent.ACTION_INSERT);
                intent1.setType("vnd.android.cursor.dir/person");    //启动意图跳转到电话本界面
                startActivity(intent1);

                break;

        }
        //如果号码为空隐藏删除键和加号键
        editText.setText(phoneNumber);
        if (phoneNumber.length() > 0) {
            delete.setVisibility(View.VISIBLE);
            sum.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
            sum.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onLongClick(View v) { //设置长按监听事件,长按删除键会删除全部号码

        switch (v.getId()) {
            case R.id.imageview_delete:
                if (phoneNumber.length() > 0) {
                    editText.setText(null);
                }
                break;
        }

        if (phoneNumber.length() > 0) {
            delete.setVisibility(View.VISIBLE);
            sum.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
            sum.setVisibility(View.GONE);
        }
        return false;
    }
}
