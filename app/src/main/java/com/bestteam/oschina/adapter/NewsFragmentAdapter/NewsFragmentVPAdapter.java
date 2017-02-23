package com.bestteam.oschina.adapter.newsfragmentadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Why on 2017/2/19.
 */

public class NewsFragmentVPAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] title;


    public NewsFragmentVPAdapter(FragmentManager fm,List<Fragment> fragments,String[] title) {
        super(fm);
        this.fragments = fragments;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
