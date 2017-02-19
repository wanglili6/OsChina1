package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class FriendActivity extends Activity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_friend)
    TextView tvFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ib_back)
    public void onClick() {
        finish();
    }
}
