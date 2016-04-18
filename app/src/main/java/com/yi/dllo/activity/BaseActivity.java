package com.yi.dllo.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_start, R.anim.anim_end);
    }
}
