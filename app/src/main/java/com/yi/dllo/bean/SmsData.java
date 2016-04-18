package com.yi.dllo.bean;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.provider.Telephony;

import com.yi.dllo.db.DBTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dllo on 16/1/21.
 */
public class SmsData {
    List<SmsByContectBean> smsByContectBeans;
    DBTool dbTool;


    public List<SmsByContectBean> getSmsByContectBeans() {
        return smsByContectBeans;
    }

    public void setSmsByContectBeans(List<SmsByContectBean> smsByContectBeans) {
        this.smsByContectBeans = smsByContectBeans;
    }


    //构造方法里初始化数据
    public SmsData(Context context) {
        smsByContectBeans = new ArrayList<>();
        dbTool = new DBTool(context);
        initData(context);//自定义获取数据的方法
    }

    private void initData(Context context) {
        ContentResolver cr = context.getContentResolver();
        String projection[] = new String[]{"_id", "address", "body", "date", "type"};
        Cursor cursor = cr.query(Telephony.Sms.CONTENT_URI, projection, null, null, "date desc");

        if (cursor.moveToFirst()) {
            String name;
            String phoneNumber;
            String smsBody;
            String date;
            int type;

            int phoneNumberColumn = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int smsBodyCloumn = cursor.getColumnIndex(Telephony.Sms.BODY);
            int dateColumn = cursor.getColumnIndex(Telephony.Sms.DATE);
            int typeColumn = cursor.getColumnIndex(Telephony.Sms.TYPE);

            do {
                phoneNumber = cursor.getString(phoneNumberColumn);


                SmsByContectBean smsByContectBean = getSmsByContectBeanFromNumber(phoneNumber);//调用通过号码寻找联系人方法,找到了联系人名字
                if (smsByContectBean == null) {
                    smsByContectBean = new SmsByContectBean();
                    smsByContectBean.setNumber(phoneNumber);

                    name = dbTool.getNameFromName(phoneNumber);

                    smsByContectBean.setName(name);

                    smsByContectBeans.add(smsByContectBean);
                }

                SingleSmsBean singleSmsBean = new SingleSmsBean();
                smsBody = cursor.getString(smsBodyCloumn);
                singleSmsBean.setSmsBody(smsBody);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
                date = dateFormat.format(d);
                singleSmsBean.setDate(date);

                type = cursor.getType(typeColumn);
                singleSmsBean.setType(type);

                smsByContectBean.addSingleSmsBean(singleSmsBean);
            }
            while (cursor.moveToNext());
        }
    }

    //向3级类里加入一条新的信息
    public void addSingleSms(SingleSmsBean singleSms, String num) {
        SmsByContectBean smsBycontect = getSmsByContectBeanFromNumber(num);
        if (smsBycontect == null) {
            smsBycontect = new SmsByContectBean();
            smsBycontect.addSingleSmsBean(singleSms);
            smsBycontect.setNumber(num);
            String name = dbTool.getNameFromName(num);
            smsBycontect.setName(name);
            smsByContectBeans.add(0, smsBycontect);
        } else {
            smsBycontect.addSingleSmsBean(singleSms);
        }
    }


    //返会多少联系人 短信个数
    public int getCount() {
        return smsByContectBeans.size();
    }

    //寻找我们的短信数据里是否存在该号码的联系人
    public SmsByContectBean getSmsByContectBeanFromNumber(String num) {
        for (SmsByContectBean smsByContectBean : smsByContectBeans) {
            if (num.equals(smsByContectBean.getNumber())) {
                return smsByContectBean;
            }
        }
        return null;
    }

}
