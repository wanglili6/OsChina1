package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tywei on 2017/2/19.
 */
public class MessageActivity extends Activity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_item);
        ButterKnife.bind(this);
        //给toolbar初始化标题
        initView();
    }

    private void initView() {
        toolbar.setTitle("aaa");
    }
}
