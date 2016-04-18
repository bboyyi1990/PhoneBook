package com.yi.dllo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/1/16.
 * 数据库工具类 实现我们写好的 DBPhone 接口
 */

public class DBHelper extends SQLiteOpenHelper implements DBPhone {

    private static final String CREATE_TABLE = " create table " + TABLE_NAME + " ( id integer primary key autoincrement, " + COLUMN_NAME + " text, " + COLUMN_NUMBER + " text)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);//生成数据库 列表

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
