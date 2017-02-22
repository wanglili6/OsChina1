package com.bestteam.oschina.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bestteam.oschina.R;
import com.bestteam.oschina.fragment.MessageCenterFragment.MessageCenterFragment1;
import com.bestteam.oschina.fragment.MessageCenterFragment.MessageCenterFragment2;
import com.bestteam.oschina.fragment.MessageCenterFragment.MessageCenterFragment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_000 on 2017/2/18.
 */

public class MessageCenterActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecenter);
        init();
    }
    private void init(){
        tablayout= (TabLayout) findViewById(R.id.tab_messagecenter);
        viewPager = (ViewPager) findViewById(R.id.vp_messagecenter);
        fragments = new ArrayList<>();
        fragments.add(setTitile(new MessageCenterFragment1(),"@我"));
        fragments.add(setTitile(new MessageCenterFragment2(),"评论"));
        fragments.add(setTitile(new MessageCenterFragment3(),"私信"));
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
    }
    private Fragment setTitile(Fragment fragment,String title){
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
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
            return fragments.get(position).getArguments().getString("title");
        }
    }

}
