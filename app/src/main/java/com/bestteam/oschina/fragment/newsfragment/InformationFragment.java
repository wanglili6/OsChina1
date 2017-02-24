package com.bestteam.oschina.fragment.newsfragment;

import android.net.Uri;
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
    private ViewPager viewPager;
    private List<ImageView> imageViews;
    private SwitchImageViewBean switchImageViewBean;
    private TextView tvTitle;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.information_rv);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewPager = (ViewPager) view.findViewById(R.id.information_vp);
        tvTitle = (TextView) view.findViewById(R.id.swtich_tv_title);
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

                initSwitchImageView();

            }
        });
    }

    //初始化轮播图
    private void initSwitchImageView() {

        imageViews = new ArrayList<>();

        int size = switchImageViewBean.getResult().getItems().size();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String url = switchImageViewBean.getResult().getItems().get(i).getImg();
            Picasso.with(getContext()).load(url).into(imageView);

            imageViews.add(imageView);

            tvTitle.setText(switchImageViewBean.getResult().getItems().get(0).getName());
        }

        SwitchImageViewAdapter switchImageViewAdapter = new SwitchImageViewAdapter(imageViews);
        viewPager.setAdapter(switchImageViewAdapter);

        switchImageViewAdapter.notifyDataSetChanged();

        viewPager.addOnPageChangeListener(this);

        mHandler.postDelayed(new SwitchTask(),3000);
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
        tvTitle.setText(switchImageViewBean.getResult().getItems().get(position).getName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class SwitchTask implements Runnable {

        @Override
        public void run() {
            if (viewPager != null) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem == imageViews.size() -1) {
                    currentItem = 0;
                }else {
                    currentItem ++;
                }
                viewPager.setCurrentItem(currentItem);
            }
            mHandler.postDelayed(new SwitchTask(),3000);
        }
    }
}
