package com.bestteam.oschina.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.SoftwareAdapter;
import com.bestteam.oschina.fragment.findfragment.ClassifyFragment;
import com.bestteam.oschina.fragment.findfragment.DomesticFragment;
import com.bestteam.oschina.fragment.findfragment.HotFragment;
import com.bestteam.oschina.fragment.findfragment.NewestFragment;
import com.bestteam.oschina.fragment.findfragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class SoftwareActivity extends FragmentActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_softwore)
    TextView tvSoftwore;
    @BindView(R.id.software_tab)
    TabLayout softwareTab;
    @BindView(R.id.software_vp)
    public ViewPager softwareVp;
    private String[] titles;

    private List<Fragment> fragments;
    private SoftwareAdapter softwareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);
        ButterKnife.bind(this);
        initData();


    }

    private List<Fragment> initFragment() {
       List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ClassifyFragment());//分类
        fragments.add(new RecommendFragment());//推荐
        fragments.add(new NewestFragment());//最新
        fragments.add(new HotFragment());//热门
        fragments.add(new DomesticFragment());//国产
        return fragments;
    }


    /**
     * 初始化数据
     */
    private void initData() {
        fragments=initFragment();
        titles = getResources().getStringArray(R.array.opensourcesoftware);
        //给viewpager设置适配器
        softwareAdapter = new SoftwareAdapter(getSupportFragmentManager(), fragments, titles);
        softwareVp.setAdapter(softwareAdapter);

        //让vp和tab关联起来
        softwareTab.setupWithViewPager(softwareVp);

        //让viewPager缓存5个页面,默认缓存3个页面
        softwareVp.setOffscreenPageLimit(5);

    }

    @OnClick(R.id.ib_back)
    public void onClick() {
        // 获取当前回退栈中的Fragment个数
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        Log.i("wlll", "onClick: "+backStackEntryCount);
            // 判断当前回退栈中的fragment个数,
            if (backStackEntryCount > 0) {
                // 立即回退一步
                fragmentManager.popBackStackImmediate();

            }else {
                finish();
            }


    }
}
