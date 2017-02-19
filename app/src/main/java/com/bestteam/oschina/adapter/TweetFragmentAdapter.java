package com.bestteam.oschina.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 45011 on 2017/2/19.
 */

public class TweetFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public TweetFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        //return fragments == null ? 0 : fragments.size();
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "最新动弹";
            case 1:
                return "热门动弹";
            case 2:
                return "我们动弹";
        }
        return null;
    }
}
