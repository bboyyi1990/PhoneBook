package com.yi.dllo.BroadCastRexceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.yi.dllo.activity.MainActivity;
import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/25.
 */
public class PersonBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("~~~~~~~~~~~~~~~~~", "这波不亏");

        showNotify(context);
    }

    private void showNotify(Context context) {

        Notification.Builder builder = new Notification.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("广播通知").setContentText("邹玮,你ADC打的太怂了!");
        //加一个跳转意图
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);//最后参数点击一次意图就失效
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);//设置点击一次通知自动消失

        //通过 bulder 建立一个对象
        Notification notification = builder.build();
        //获取Notification 对象
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);


    }
}
