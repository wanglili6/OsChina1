package com.bestteam.oschina.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.bestteam.oschina.R;

import com.bestteam.oschina.adapter.NewsFragmentAdapter.InformationFragmentRVAdapter;
import com.bestteam.oschina.fragment.MessageCenterFragment.AtMeFragment;
import com.bestteam.oschina.fragment.MessageCenterFragment.CommentFragment;
import com.bestteam.oschina.fragment.MessageCenterFragment.PrivateMessageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_000 on 2017/2/18.
 */

public class MessageCenterActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private ImageButton ib_back;
    private List<Fragment> fragments;
    private InformationFragmentRVAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecenter);
        init();
    }
    private void init(){
        tablayout= (TabLayout) findViewById(R.id.tab_messagecenter);
        viewPager = (ViewPager) findViewById(R.id.vp_messagecenter);
        ib_back = (ImageButton) findViewById(R.id.ib_messagecenter);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragments = new ArrayList<>();
        fragments.add(setTitile(new AtMeFragment(),"@我"));
        fragments.add(setTitile(new CommentFragment(),"评论"));
        fragments.add(setTitile(new PrivateMessageFragment(),"私信"));
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
