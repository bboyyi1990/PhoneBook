package com.yi.dllo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/1/16.
 * 数据库操作类  实现我们写好的DBPhone 接口 来操作数据库
 */
public class DBTool implements DBPhone {
    private DBHelper helper; //工具类
    private SQLiteDatabase db;//数据类
    private ContentValues values;
    private Context context;//这个context 是 helper 工具类里构造方法里面的型参,谁用就承接谁


    //DBTool类的构造方法 ,在这里直接把工具类和数据类初始化
    public DBTool(Context context) {
        this.context = context;
        helper = new DBHelper(context, "Phone.db", null, 1);
        db = helper.getWritableDatabase();
    }


    //判断数据是否存在名字列数据
    public boolean hasTextName(String value) {
        String sqlName = "select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = ? "; //这里是判断列表里 列的 value 不是判断 key;
        Cursor cursorName = db.rawQuery(sqlName, new String[]{value});//让游标找到目标位置的值

        return cursorName.getCount() > 0;//返回 游标里数据的个数是否大于0
    }


    //插入数据的方法
    public void insertData(String name, String number) {
        String numbers = formatNumber(number);//数据库载入之前格式化号码
        if (name.length() < 1) {  //判断  插入数据   的个数小于1 ,就是没有插入的数据,直接跳出该方法
            return;
        }

        if (hasTextName(name)) {  //判断 插入数据  是否已经存在

        } else {
            values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_NUMBER, numbers);
            db.insert(TABLE_NAME, null, values);
        }
    }

    //获取数据库内容方法 返回值类型时 装数据的集合
    public List<String> getAllInfo() {
        List<String> nameData = new ArrayList<>();
        List<String> numberData = new ArrayList<>();

        String sql = " select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);//默认为空指示完成当前
        return nameData;
    }

    //用号码通过数据库找到联系人姓名方法 (找到联系人返回联系人名,没有找到联系人名返回陌生人)
    public String getNameFromName(String number) {
        number = formatNumber(number);//格式化代码
        String sql = "select * from " + TABLE_NAME +
                " where (" + COLUMN_NUMBER + "="
                + "'" + number + "')";
        Cursor cursor = db.rawQuery(sql, null);
        
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            return name;
        }
        cursor.close();
        return "陌生人";
    }

    private String formatNumber(String number) {
        number = number.replace("+86", "");
        number = number.replace(" ", "");
        number = number.replace("-", "");
        number = number.replace("(", "");
        number = number.replace(")", "");
        return number;
    }
}
