package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bestteam.oschina.R;

/**
 * Created by zheng_000 on 2017/2/25.
 */

public class AtMeDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmedetail);
        String id = getIntent().getStringExtra("id");
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

    }
}
