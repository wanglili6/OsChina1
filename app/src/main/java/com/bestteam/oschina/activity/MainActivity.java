package com.bestteam.oschina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.fragment.ExploreFragment;
import com.bestteam.oschina.fragment.NewFragment;
import com.bestteam.oschina.fragment.NewMeFragment;
import com.bestteam.oschina.fragment.TweetFragment;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @BindView(R.id.main_search)
    ImageButton mainSearch;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private List<Fragment> fragments;
    private boolean tweetHasLoad;
    private TweetFragment tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragments();
        //设置监听事件
        initEvent();
        //设置默认的显示
        tvTitle.setText("综合");
        switchFragments(0);
        radiogroup.check(R.id.rb_new);
    }

    private void initEvent() {

        //给radiogroup设置选择监听
        radiogroup.setOnCheckedChangeListener(this);

        //中间加号的点击事件
        tvPostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid =  SPUtils.getString(getApplicationContext(),Cantents.MY_UID,"");

                if (TextUtils.isEmpty(uid)){
                   startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this, FlipActivity.class));
                }
            }
        });


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
        tweet = new TweetFragment();
        fragments = new ArrayList<>();
        fragments.add(new NewFragment());
        fragments.add(tweet);
        fragments.add(new ExploreFragment());
        fragments.add(new NewMeFragment());
    }

    /**
     * 选中监听
     *
     * @param group
     * @param checkedId
     */

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_new:
                toolbar.setVisibility(View.VISIBLE);
                switchFragments(0);
                tvTitle.setText("综合");
                mainSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_tweet:
                toolbar.setVisibility(View.VISIBLE);
                switchFragments(1);
                tvTitle.setText("动弹");
                mainSearch.setVisibility(View.VISIBLE);

                //((DadTweetFragment)((TweetFragment)fragments.get(1)).fragments.get(0)).loadNetData();
                break;
            case R.id.rb_explore:
                toolbar.setVisibility(View.VISIBLE);
                switchFragments(2);
                tvTitle.setText("发现");
                mainSearch.setVisibility(View.VISIBLE);


                break;
            case R.id.rb_me:
                toolbar.setVisibility(View.GONE);
                switchFragments(3);
                mainSearch.setVisibility(View.GONE);
                tvTitle.setText("");
                break;
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.main_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

}
