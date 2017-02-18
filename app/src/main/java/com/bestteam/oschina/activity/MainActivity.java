package com.bestteam.oschina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.fragment.ExploreFragment;
import com.bestteam.oschina.fragment.MeFragment;
import com.bestteam.oschina.fragment.NewFragment;
import com.bestteam.oschina.fragment.TweetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.rb_new)
    RadioButton rbNew;
    @BindView(R.id.rb_tweet)
    RadioButton rbTweet;
    @BindView(R.id.tv_post_tweet)
    TextView tvPostTweet;
    @BindView(R.id.rb_explore)
    RadioButton rbExplore;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragments();
        radiogroup.check(R.id.rb_new);
        radiogroup.setOnCheckedChangeListener(this);

        tvPostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        switchFragments(0);


    }

    /**
     * 切换fragment
     */
    private void switchFragments(int index) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == index) {//显示fragment
                if (fragment.isAdded()) {//如果当前fragment已经创建,直接显示fragment
                    fragmentTransaction.show(fragment);
                } else {//第一创建fragment, 将fragment放入到容器中
                    fragmentTransaction.add(R.id.fl_container, fragment);
                }
            } else {//隐藏剩余fragment
                if (fragment.isAdded()) {
                    fragmentTransaction.hide(fragment);
                }
            }
        }

        fragmentTransaction.commitNowAllowingStateLoss();
    }

    /**
     * 初始化四个fragment
     */
    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new NewFragment());
        fragments.add(new TweetFragment());
        fragments.add(new ExploreFragment());
        fragments.add(new MeFragment());
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_new:
                switchFragments(0);
                break;
            case R.id.rb_tweet:

                switchFragments(1);
                break;
            case R.id.rb_explore:

                switchFragments(2);
                break;
            case R.id.rb_me:

                switchFragments(3);
                break;
        }
    }

}
