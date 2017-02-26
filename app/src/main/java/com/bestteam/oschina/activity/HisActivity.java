package com.bestteam.oschina.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.MeVpFragAdapter;
import com.bestteam.oschina.fragment.HotTweetFragment;
import com.bestteam.oschina.fragment.MyTweetFragment;
import com.bestteam.oschina.fragment.NewTweetFragment;
import com.bestteam.oschina.view.GalaxyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 45011 on 2017/2/25.
 */
public class HisActivity extends AppCompatActivity {

    @BindView(R.id.galaxy)
    GalaxyView galaxy;
    @BindView(R.id.main_code)
    ImageButton mainCode;
    @BindView(R.id.face_big)
    CircleImageView faceBig;
    @BindView(R.id.img_gender)
    CircleImageView imgGender;
    @BindView(R.id.jifen)
    TextView jifen;
    @BindView(R.id.rl_collapsing)
    RelativeLayout rlCollapsing;
    @BindView(R.id.collapsing_tool_bar_test_ctl)
    CollapsingToolbarLayout collapsingToolBarTestCtl;
    @BindView(R.id.tab_me)
    TabLayout tabMe;
    @BindView(R.id.id_appbarlayout)
    AppBarLayout idAppbarlayout;
    @BindView(R.id.vp_me)
    ViewPager vpMe;
    private int authorid;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        authorid = intent.getIntExtra("authorId", 0);

        initGalaxy();
        initViewPager();

    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new NewTweetFragment());
        fragments.add(new HotTweetFragment());
        fragments.add(new MyTweetFragment());
        fragments.add(new NewTweetFragment());
        MeVpFragAdapter adapter = new MeVpFragAdapter(getSupportFragmentManager(),fragments);

        vpMe.setAdapter(adapter);
        tabMe.setupWithViewPager(vpMe);

        vpMe.setOffscreenPageLimit(4);
    }

    private void initData() {

    }

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

    @OnClick(R.id.main_code)
    public void onClick() {
    }
}
