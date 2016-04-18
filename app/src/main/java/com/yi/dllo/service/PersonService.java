package com.yi.dllo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.yi.dllo.value.BroadCastValue;

import java.util.List;

/**
 * Created by dllo on 16/1/28.
 */
public class PersonService extends Service {
    private PersonBind bind = new PersonBind();
    private Thread timeThread;


    public void getThread(final int time, final String body, final String number) {

        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 延迟 time 秒发送
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //发送信息
                sendMessage(number, body);

            }
        });
    }

    //发送短信方法
    public void sendMessage(String number, String body) {
        SmsManager manager = SmsManager.getDefault();
        List<String> b = manager.divideMessage(body);//分割短信字符串,当短息长度超过70个字 会再发一条短信 一条短信是一个元素,不是字符

        for (String sms : b) { //这里遍历的是字符串不是字符
            if (sms != null) {
                manager.sendTextMessage(number, null, sms, null, null);
            }
        }
        //发送广播去刷新 UI
        Intent smsBroadIntent = new Intent(BroadCastValue.DETAIL_SMS);//括号里是发送广播常量
        smsBroadIntent.putExtra("num", number);
        smsBroadIntent.putExtra("body", body);
        sendBroadcast(smsBroadIntent);
        //服务的任务结束 可以销毁了
        stopSelf(); /**在服务的任一个地方 调用该方法都会销毁该服务**/
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bind;
    }

    public class PersonBind extends Binder {
        public void setTime(int time, String smsBody, String number) {
            getThread(time, smsBody, number);//获得一个新的线程
            timeThread.start();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
