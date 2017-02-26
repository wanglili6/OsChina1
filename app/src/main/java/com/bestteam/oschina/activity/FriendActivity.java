package com.bestteam.oschina.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.view.GalaxyView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangyuanchao on 2017/2/26.
 */

public class FriendActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibBack;
    private TextView tvBack;
    private TextView tvName;
    private TextView jifen;
    private CircleImageView faceBig;
    private GalaxyView galaxy;
    private CircleImageView imgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_friend);
        initView();
        ViewTreeObserver viewTreeObserver = faceBig.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initGalaxy();
            }
        });
        initData();
        initEvent();
    }

    private void initData() {
            String gender = getIntent().getStringExtra("gender");
            String name = getIntent().getStringExtra("name");
            if (!TextUtils.isEmpty(gender)) {
                Picasso.with(getApplicationContext()).load(gender).into(faceBig);
            }
            tvName.setText(name);
    }

    /**
     * 初始化Galaxy的方法
     */
    private void initGalaxy() {


        Window window = getWindow();
        Rect decorRect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(decorRect);

        int decorHeight = decorRect.height();
        Display display = getWindowManager().getDefaultDisplay();
        int statusBar = display.getHeight() - decorHeight;

        //计算中心点
        int[] location = new int[2];
        faceBig.getLocationInWindow(location);
        Rect drawRect = new Rect();
        faceBig.getDrawingRect(drawRect);
        int x = location[0] + drawRect.width() / 2;
        int y = location[1] + drawRect.height() / 2;
        //设置Galaxy
        galaxy.setDefaultPlanets();
        galaxy.setGalaxyCenter(x, y - statusBar);
        galaxy.startRotate();
    }

    private void initEvent() {
        ibBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_back_attention);
        tvBack = (TextView) findViewById(R.id.tv_back_attention);
        faceBig = (CircleImageView) findViewById(R.id.face_big);
        galaxy = (GalaxyView) findViewById(R.id.galaxy);
        imgGender = (CircleImageView) findViewById(R.id.img_gender);
        tvName = (TextView) findViewById(R.id.tv_name);
        jifen = (TextView) findViewById(R.id.jifen);

    }

    @Override
    public void onClick(View v) {
        if (v==ibBack||v==tvBack){
            finish();
        }
    }
}
