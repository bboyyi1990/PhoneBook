package com.yi.dllo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/18.
 */
public class OtherActivity extends BaseActivity {

    private TextView name_tv, number_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        initView();
    }

    @Override
    public void initView() {
        name_tv = (TextView) findViewById(R.id.otheractivity_name);
        number_tv = (TextView) findViewById(R.id.otheractivity_number);
        name_tv.setText(getIntent().getStringExtra("name"));
        number_tv.setText(getIntent().getStringExtra("number"));
    }


}
