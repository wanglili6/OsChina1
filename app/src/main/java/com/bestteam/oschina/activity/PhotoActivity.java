package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lx on 2017/2/25.
 */

public class PhotoActivity extends Activity {


    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_softwore)
    TextView tvSoftwore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.ib_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                startActivity(new Intent(this, FlipActivity.class));
                finish();
                break;

        }
    }

}
