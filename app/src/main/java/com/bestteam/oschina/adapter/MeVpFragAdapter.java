package com.bestteam.oschina.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bestteam.oschina.fragment.DadTweetFragment;

import java.util.List;

/**
 * Created by 45011 on 2017/2/19.
 */

public class MeVpFragAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public MeVpFragAdapter(FragmentManager fm, List<Fragment> fragments) {
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
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "动弹 "+666;
            case 1:
                return "收藏 "+"老铁";
            case 2:
                return "关注 "+ "没毛病";
            case 3:
                return "粉丝 "+ "666";
        }
        return null;
    }
}
