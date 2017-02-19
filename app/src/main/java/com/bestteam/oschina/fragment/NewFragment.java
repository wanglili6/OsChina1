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
import com.bestteam.oschina.adapter.NewsFragmentVPAdapter;
import com.bestteam.oschina.fragment.newsfragment.BlogFragment;
import com.bestteam.oschina.fragment.newsfragment.CommendFragment;
import com.bestteam.oschina.fragment.newsfragment.HotSpotFragment;
import com.bestteam.oschina.fragment.newsfragment.InformationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class NewFragment extends android.support.v4.app.Fragment {

    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments;
    private String[] title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.newfragmengt, (ViewGroup) getView(),false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        vp = (ViewPager) view.findViewById(R.id.vp);

        //title集合
        title = getResources().getStringArray(R.array.news_viewpage_arrays);

        initFragments();
        initViewParge();
        return view;
    }

    private void initViewParge() {

        NewsFragmentVPAdapter adapter = new NewsFragmentVPAdapter(getFragmentManager(),fragments,title);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new InformationFragment());
        fragments.add(new HotSpotFragment());
        fragments.add(new BlogFragment());
        fragments.add(new CommendFragment());

    }
//    private Fragment setTitle(Fragment fragment,String title) {
//        Bundle bundle = new Bundle();
//        bundle.putString("title",title);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
}
