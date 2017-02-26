package com.bestteam.oschina.fragment.newsfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.InformationFragmentAdapter;
import com.bestteam.oschina.adapter.SwitchImageViewAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.NewsList;
import com.bestteam.oschina.bean.SwitchImageViewBean;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.XmlUtils;
import com.bestteam.oschina.view.SwitchImageViewViewPager;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by Why on 2017/2/21.
 */

public class InformationFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private XRecyclerView xRecyclerView;

    private String pageSize = "20";
    private boolean isRefresh = true;
    private boolean isLoaderMore = false;
    private int pageIndex = 0;
    private InformationFragmentAdapter adapter;
    private SwitchImageViewViewPager viewPager;
    private List<ImageView> imageViews;
    public SwitchImageViewBean switchImageViewBean;
    private TextView tvTitle;
    private Handler mHandler = new Handler();
    private LinearLayout llPoint;
    private boolean hasSwicth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.information_rv);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new InformationFragmentAdapter(getContext());
        xRecyclerView.setAdapter(adapter);

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);


        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 0;
                requestData();
            }

            @Override
            public void onLoadMore() {
                isLoaderMore = true;
                pageIndex = pageIndex + 1;
                requestData();
            }
        });

        initViewPager();
        requestData();

    }


    //初始化viewPager
    private void initViewPager() {

        String url = Cantents.SWITCH_IMAGEVIEW_URL;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                switchImageViewBean = gson.fromJson(response, SwitchImageViewBean.class);

                bindDataToView();


            }
        });

    }

    private void bindDataToView() {

        if (getHeaderView() != null) {
            xRecyclerView.addHeaderView(getHeaderView());
        }

        initSwitchImageView();
        initPoint();


        viewPager.setSwitchImageView(this);
    }

    //添加头
    private View getHeaderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.switch_imageview_viewpager, (ViewGroup) getView(), false);
        viewPager = (SwitchImageViewViewPager) view.findViewById(R.id.information_vp);
        tvTitle = (TextView) view.findViewById(R.id.swtich_tv_title);
        llPoint = (LinearLayout) view.findViewById(R.id.ll_point_container);
        return view;
    }


    //初始化轮播图
    private void initSwitchImageView() {

        imageViews = new ArrayList<>();

        int size = switchImageViewBean.getResult().getItems().size();
        SwitchImageViewBean.ResultBean.ItemsBean itemsBean = null;
        for (int i = -1; i < size + 1; i++) {
            if ( i == -1) {
                itemsBean = switchImageViewBean.getResult().getItems().get(size - 1);

            }else if (i == size) {
                itemsBean =switchImageViewBean.getResult().getItems().get(0);
            }else{
                itemsBean =switchImageViewBean.getResult().getItems().get(i);
            }
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

           // String url = switchImageViewBean.getResult().getItems().get(i).getImg();

            Picasso.with(getContext()).load(itemsBean.getImg()).into(imageView);

            imageViews.add(imageView);


        }



        SwitchImageViewAdapter switchImageViewAdapter = new SwitchImageViewAdapter(
                getContext(),imageViews,switchImageViewBean.getResult().getItems());

        viewPager.setAdapter(switchImageViewAdapter);

        tvTitle.setText(switchImageViewBean.getResult().getItems().get(0).getName());

        switchImageViewAdapter.notifyDataSetChanged();

        viewPager.addOnPageChangeListener(this);

        viewPager.setCurrentItem(1, false);

        mHandler.postDelayed(new SwitchTask(),3000);
    }


    //初始化移动的点
    private void initPoint() {
        llPoint.removeAllViews();
        for (int i = 0; i < switchImageViewBean.getResult().getItems().size(); i++) {
            View view = new View(getContext());
            view.setBackgroundResource(R.drawable.point_white_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
            params.rightMargin = 20;
            llPoint.addView(view, params);
        }
        llPoint.getChildAt(0).setBackgroundResource(R.drawable.point_green_bg);

    }


    //初始化列表数据
    private void requestData() {
        String url = Cantents.NEWS_URL;
        Map<String, String> parmas = new HashMap<>();
        parmas.put("pageIndex", String.valueOf(pageIndex));
        parmas.put("catalog", "1");
        parmas.put("pageSize", pageSize);

        OKHttp3Helper.create().get(url, null, parmas, new OKHttp3Helper.HttpCallback() {

            @Override
            public void onSuccess(String data) {
                NewsList newsList = XmlUtils.toBean(NewsList.class, data.getBytes());

                if (isRefresh) {
                    adapter.clear();
                    adapter.addAll(newsList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }

                if (isLoaderMore) {
                    adapter.addAll(newsList.getList());
                    xRecyclerView.loadMoreComplete();
                    isLoaderMore = false;
                }
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int index = 0;
        int size = switchImageViewBean.getResult().getItems().size();
        if (position == 0) {
            index =size -1;
            viewPager.setCurrentItem(size,false);

        }else if (position == size + 1) {
            index = 0;
            viewPager.setCurrentItem(1,false);

        }else{
            index = position -1;
        }


        tvTitle.setText(switchImageViewBean.getResult().getItems().get(index).getName());

        int childCount = llPoint.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = llPoint.getChildAt(i);
            if (index == i) {
                child.setBackgroundResource(R.drawable.point_green_bg);
            } else {
                child.setBackgroundResource(R.drawable.point_white_bg);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //开始轮播
    public void startSwtich() {
        if (!hasSwicth) {
            mHandler.postDelayed(new SwitchTask(), 3000);
        }
    }

    //结束轮播
    public void stopSwtich() {
        mHandler.removeCallbacksAndMessages(null);
        hasSwicth = false;
    }

    private class SwitchTask implements Runnable {

        @Override
        public void run() {
            if (viewPager != null) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem == imageViews.size() - 1) {
                    currentItem = 0;
                } else {
                    currentItem++;
                }
                viewPager.setCurrentItem(currentItem);
            }
            mHandler.postDelayed(new SwitchTask(), 3000);
        }
    }
}
