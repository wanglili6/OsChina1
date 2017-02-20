package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bestteam.oschina.R;

/**
 * Created by zheng_000 on 2017/2/19.
 */

public class MyBlogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myblog);
        init();
    }
    private void init(){
        ImageButton ib_blog = (ImageButton) findViewById(R.id.ib_myblog);
        ib_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
