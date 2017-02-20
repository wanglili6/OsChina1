package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;

/**
 * Created by zhangyuanchao on 2017/2/19.
 */

public class FansActivity extends Activity implements View.OnClickListener {

    private ImageButton ibBack;
    private TextView tvMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        initView();
        initEvent();
    }

    private void initEvent() {
        ibBack.setOnClickListener(this);
        tvMe.setOnClickListener(this);
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        tvMe = (TextView) findViewById(R.id.tv_me);
    }

    @Override
    public void onClick(View view) {
        if (view==ibBack||view==tvMe){
            finish();
        }
    }
}
