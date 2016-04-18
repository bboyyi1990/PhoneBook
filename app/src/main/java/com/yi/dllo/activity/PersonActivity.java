package com.yi.dllo.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yi.dllo.adapter.MessageRecyelerAdapterSms;
import com.yi.dllo.adapter.PresonAdapter;
import com.yi.dllo.bean.SingleSmsBean;
import com.yi.dllo.bean.SmsByContectBean;
import com.yi.dllo.phonebook.R;
import com.yi.dllo.service.PersonService;
import com.yi.dllo.value.BroadCastValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dllo on 16/1/25.
 */
public class PersonActivity extends BaseActivity implements View.OnClickListener {
    //于某一个联系人的所有静态信息
    public static SmsByContectBean smsByContectBean;
    public static MessageRecyelerAdapterSms adapterSms;
    private PresonAdapter presonAdapter;
    private EditText editText;
    private RecyclerView recyclerView;
    private PersonService.PersonBind personBind;//服务绑定对象
    private ServiceConnection connection;
    private RefreshSmsReceiver r;//动态广播接收者
    private SharedPreferences sp;//轻量级数据库存短信内容
    private SharedPreferences.Editor editor;//数据库编辑器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initView();//初始化组件
        initData();//加载数据
        Bind();//绑定服务
        registReceiver();//接收广播
        saveDraft();//存草稿
    }

    private void saveDraft() {
        //生成草稿
        sp = getSharedPreferences("Draft", Context.MODE_PRIVATE);
        editor = sp.edit();

        SharedPreferences read = getSharedPreferences("Draft", MODE_PRIVATE);
        String phoneNumber = smsByContectBean.getNumber();
        String body = read.getString(phoneNumber, "");
        editText.setText(body);
        String body1 = editText.getText().toString();
        editor.putString(phoneNumber, body1);
        editor.commit();
    }


    private void registReceiver() {

        //注册广播
        IntentFilter intentFilater = new IntentFilter(BroadCastValue.DETAIL_SMS);
        r = new RefreshSmsReceiver();
        registerReceiver(r, intentFilater);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        personBind = null;//bind对象置空
        unregisterReceiver(r);//解除广播
        unbindService(connection);//解绑服务

        //生命结束存草稿
        EditText body = (EditText) findViewById(R.id.person_edit);
        String smsBody = body.getText().toString();
        String phoneNumber = smsByContectBean.getNumber();
        editor.putString(phoneNumber, smsBody);
        editor.commit();
        Toast.makeText(this, "草稿保存成功", Toast.LENGTH_SHORT).show();
    }

    private void Bind() {

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                personBind = (PersonService.PersonBind) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                personBind = null;
            }
        };

        Intent intent = new Intent(this, PersonService.class);
        //两种启动 Service 方式都使用,是服务可以在后台长时间运行即使 Activity 消失 服务也会继续执行
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }

    private void initData() {
        presonAdapter = new PresonAdapter(this, smsByContectBean);
        recyclerView.setAdapter(presonAdapter);
    }


    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_toobar);
        toolbar.setTitle(smsByContectBean.getName());
        toolbar.setTitleTextColor(Color.RED);
        setSupportActionBar(toolbar);

        Button btn = (Button) findViewById(R.id.person_btn);
        btn.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.person_edit);
        recyclerView = (RecyclerView) findViewById(R.id.person_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }


    //用来启动我们的这个 Activity
    public static void startJump(Context context, SmsByContectBean smsByContectBean, MessageRecyelerAdapterSms adapterSms) {  //第三个参数 把 messageFragment 里面的适配器传过来进行操作 达到 message 页面进行实时刷新的效果
        //对这个类里的数据进行赋值
        PersonActivity.smsByContectBean = smsByContectBean;
        PersonActivity.adapterSms = adapterSms;

        Intent intent = new Intent(context, PersonActivity.class);//跳转
        context.startActivity(intent);
    }

    @Override//Button 监听
    public void onClick(View v) {
        //发短信
        String body = editText.getText().toString();
        String number = smsByContectBean.getNumber();

        if (number.isEmpty() | body.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();

        } else {
            SmsManager smsManager = SmsManager.getDefault();//信息管理者
            List<String> b = smsManager.divideMessage(body);
            for (String sms : b) {
                smsManager.sendTextMessage(number, null, sms, null, null);
            }

            Toast.makeText(this, "短信发送成功", Toast.LENGTH_SHORT).show();
            editText.setText("");//让输入框内容发送后自动置空

            refreshUI(body);//刷新 UI 信息内容
        }
    }

    public void refreshUI(String body) {

        //刷新 UI
        //往单条信息里装内容
        SingleSmsBean singleSmsBean = new SingleSmsBean();
        singleSmsBean.setSmsBody(body);
        singleSmsBean.setType(1);

        //获取系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date(System.currentTimeMillis());//系统时间
        String date = dateFormat.format(d);
        singleSmsBean.setDate(date);

        //加入到 Adapter 里并刷新
        smsByContectBean.addUser(singleSmsBean);
        presonAdapter.addSingleSms(smsByContectBean);

        //新的要求,需要滚动到最后一行
        recyclerView.scrollToPosition(smsByContectBean.getSize() - 1);
        adapterSms.notifyDataSetChanged();//把 message 的适配器传递过来 就是为了刷新一下达到更新实时更新效果

    }

    @Override//生成menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_menu_item:
                showDiaLog();//显示 Dialog
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("                                 延时发送");
        final View view = getLayoutInflater().inflate(R.layout.dialog_person, null);
        builder.setView(view);

        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            @Override//积极按钮监听事件
            public void onClick(DialogInterface dialog, int which) {
                // 实现延时发送短信功能
                //短信信息从 Activity 的 edittext 获取  延时时间从 dialog 的 edittext 获取 电话号码从当前联系人获取

                //获得延时发送时间
                EditText ed = (EditText) view.findViewById(R.id.person_dialog_edittext);
                String times = ed.getText().toString();//得到 dialog 延迟时间
                int time = Integer.valueOf(times);//时间转型
                String body = editText.getText().toString();// get Message body
                String number = smsByContectBean.getNumber();// get Message number
                // Pass value to Service
                if (personBind != null) { //如果服务对象不是空的就把信息传送给 Service
                    personBind.setTime(time, body, number);//把信息需要的 时间 号码 内容传给 Service
                }
            }
        });
        builder.show();
    }

    //接收服务发送的广播来刷新当前界面的 UI(内部类)
    class RefreshSmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //如果接到了广播,说明短信发送成功
            String num = intent.getStringExtra("num");
            String body = intent.getStringExtra("body");
            //判断一下这个条短信是否是我们当前页面的人发送的
            if (smsByContectBean.getNumber().equals(num)) {
                //如果是当前这个人发送的
                refreshUI(body);//刷新 UI
            }
        }
    }


}
