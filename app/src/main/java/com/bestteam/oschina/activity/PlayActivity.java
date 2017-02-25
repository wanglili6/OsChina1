package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ImgVPAdapter;

import com.bestteam.oschina.adapter.NomalAdapter;
import com.bestteam.oschina.bean.AcrivityMessageBean;
import com.bestteam.oschina.bean.EventList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/18.
 * <p>
 * 线下活动
 */
public class PlayActivity extends Activity implements XRecyclerView.LoadingListener, ViewPager.OnPageChangeListener {


    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_activity)
    TextView tvActivity;
    @BindView(R.id.play_rv)
    XRecyclerView playRv;
    @BindView(R.id.load)
    ProgressBar load;
    private NomalAdapter adapter;

    private boolean isRefresh = true;
    private boolean isLoadMore = false;
    private int pageIndex = 0;
    private List<ImageView> imgs;
    private AcrivityMessageBean acrivityMessageBean;
    private AcrivityMessageBean.ResultBean.ItemsBean itemsBean;
    private ViewPager imgVp;
    private LinearLayout pointContant;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        //初始化View
        initRecyleView();
        //加载数据
        loadData();


        //设置adapter

        adapter = new NomalAdapter(this);
        playRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


//        initPoint();
    }

    private void initRecyleView() {
        //设置布局管理器
        playRv.setLayoutManager(new LinearLayoutManager(this));


        playRv.setLoadingMoreEnabled(true);
        playRv.setPullRefreshEnabled(true);
        playRv.setLoadingListener(this);

        //添加头布局
        if (getHeadView() != null) {
            playRv.addHeaderView(getHeadView());
        }
    }

    public View getHeadView() {

        View view = LayoutInflater.from(this).inflate(R.layout.img_viewpage, null);
        imgVp = (ViewPager) view.findViewById(R.id.img_vp);
        pointContant = (LinearLayout) view.findViewById(R.id.point_contant);

        //加载轮播图片数据
        loadIMg();
        //创建点


        //给ViewPager设置滑动监听
        imgVp.addOnPageChangeListener(this);
        //开始轮播
        startSwitch();

        initPoint();
        return view;
    }

    private void initPoint() {

        for (int i = 0; i < 3; i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            view.setBackgroundResource(R.drawable.guide_point_gray_shape);
            params.rightMargin = 10;
            pointContant.addView(view, params);

        }
        //让第一个为红色
        pointContant.getChildAt(0).setBackgroundResource(R.drawable.guide_point_red_shape);
    }

    /**
     * 加载轮播图片的数据
     */
    private void loadIMg() {
        String url = "http://www.oschina.net/action/apiv2/banner?catalog=3&nextPageToken=226B2C51A4EC6281";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(PlayActivity.this, "加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyToast.show(PlayActivity.this, "加载");
                        ProcessData(response);


                    }
                });

    }

    private void ProcessData(String response) {
        Gson gson = new Gson();
        acrivityMessageBean = gson.fromJson(response, AcrivityMessageBean.class);
        //加载轮播图片
        initViewPager();
    }


    private void initViewPager() {
        imgs = new ArrayList<>();
        for (int i = 0; i < acrivityMessageBean.getResult().getItems().size(); i++) {
            itemsBean = acrivityMessageBean.getResult().getItems().get(i);
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(this).load(itemsBean.getImg()).into(imageView);
            imgs.add(imageView);
        }
        ImgVPAdapter imgadapter = new ImgVPAdapter(imgs, acrivityMessageBean.getResult().getItems(), this);
        imgVp.setAdapter(imgadapter);

        //让轮播图片显示第一个
        imgVp.setCurrentItem(1, false);


    }


    //加载rv的条目
    private void loadData() {
        load.setVisibility(View.VISIBLE);

        String url = "http://www.oschina.net/action/api/event_list?uid=-1&pageIndex=0&pageSize=20";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("pageIndex", String.valueOf(pageIndex))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(PlayActivity.this, "加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        load.setVisibility(View.GONE);
                        EventList eventList = XmlUtils.toBean(EventList.class, response.getBytes());
                        if (isRefresh) {
                            adapter.clear();
                            adapter.addAll(eventList.getFriendlist());
                            playRv.refreshComplete();
                            isRefresh = false;
                        }
                        if (isLoadMore) {
                            adapter.addAll(eventList.getFriendlist());
                            playRv.loadMoreComplete();
                            isLoadMore = false;
                        }


                    }
                });
    }


    @OnClick(R.id.ib_back)
    public void onClick() {
        finish();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        pageIndex = 0;
        loadData();


    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        pageIndex++;
        loadData();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        // 获取所有孩子
        int childCount = pointContant.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (position == i) {
                pointContant.getChildAt(i).setBackgroundResource(R.drawable.guide_point_red_shape);
            } else {
                pointContant.getChildAt(i).setBackgroundResource(R.drawable.guide_point_gray_shape);
            }

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class SwitchTask implements Runnable {

        @Override
        public void run() {
            if (imgVp != null) {
                int currentItem = imgVp.getCurrentItem();

                if (currentItem == imgs.size() - 1) {
                    //切换到第一页
                    currentItem = 0;
                } else {
                    //进入到下一页
                    currentItem++;
                }
                imgVp.setCurrentItem(currentItem);
                handler.postDelayed(this, 3000);
            }

        }
    }

    //开始切换
    public void startSwitch() {

        handler.postDelayed(new SwitchTask(), 3000);

    }


}
