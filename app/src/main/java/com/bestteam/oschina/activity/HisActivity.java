package com.bestteam.oschina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bestteam.oschina.R;

/**
 * Created by 45011 on 2017/2/25.
 */
public class HisActivity extends AppCompatActivity{

    private TextView tv;
    private int authorid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his);
        Intent intent = getIntent();
        authorid = intent.getIntExtra("authorId", 0);

    }
}
