package com.bestteam.oschina.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by 45011 on 2017/2/23.
 */
public class ImgBigActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private Bitmap bitmap;
    private ImageView ivExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgbig);

        Intent intent = getIntent();
        String imgBig = intent.getStringExtra("imgBig");

        iv = (ImageView) findViewById(R.id.iv_big);
        ivExit = (ImageView) findViewById(R.id.iv_exit);
        ivExit.setOnClickListener(this);

        initImg(imgBig);

    }

    private void initImg(final String url) {

        Picasso.with(this).load(url).into(iv);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
