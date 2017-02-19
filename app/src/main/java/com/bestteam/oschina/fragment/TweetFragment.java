package com.bestteam.oschina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.TweetFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class TweetFragment extends android.support.v4.app.Fragment {

    private TabLayout tab_tweet;
    private ViewPager vp_tweet;
    private List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.total_dongtan,container,false);
        //初始化控件
        initView(view);
        initViewPager();
        return view;
    }

    private void initView(View view) {
        tab_tweet = (TabLayout) view.findViewById(R.id.tab);
        vp_tweet = (ViewPager) view.findViewById(R.id.vp);
    }

    private void initViewPager(){
        fragments = new ArrayList<>();
        fragments.add(new NewTweetFragment());
        fragments.add(new HotTweetFragment());
        fragments.add(new MyTweetFragment());

        TweetFragmentAdapter adapter = new TweetFragmentAdapter(getChildFragmentManager(), fragments);
        vp_tweet.setAdapter(adapter);
        tab_tweet.setupWithViewPager(vp_tweet);
    }


}
