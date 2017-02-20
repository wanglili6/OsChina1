package com.bestteam.oschina.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bestteam.oschina.R;
import com.bestteam.oschina.fragment.MessagePagerAdapter;
import com.bestteam.oschina.fragment.messageFragment.CommentFragment;
import com.bestteam.oschina.fragment.messageFragment.MyselfFragment;
import com.bestteam.oschina.fragment.messageFragment.PersonalLetterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Tywei on 2017/2/19.
 */
public class MessageActivity extends FragmentActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_item);
        ButterKnife.bind(this);
        //给viewPager进行初始化来填充数据
        initViewPager();

    }

    private void initViewPager() {
        //要联合Fragment和FragmentPagerAdapter
        fragments = new ArrayList<>();
        fragments.add(setTitle(new MyselfFragment(), "@我"));
        fragments.add(setTitle(new CommentFragment(), "评论"));
        fragments.add(setTitle(new PersonalLetterFragment(), "私信"));

        //把适配器设置给ViewPager
        viewPager.setAdapter(new MessagePagerAdapter(getSupportFragmentManager(), fragments));
        //关联tabLayout和Viewpager
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * 给fragment设置标题
     *
     * @param fragment
     * @param title
     * @return
     */
    private Fragment setTitle(Fragment fragment, String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


}
