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

import static android.R.id.list;

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
    private List<ImageView> imgs=new ArrayList<>();
    private AcrivityMessageBean acrivityMessageBean;
    private ViewPager imgVp;
    private LinearLayout pointContant;
    private Handler mHandler = new Handler();
    private List<AcrivityMessageBean.ResultBean.ItemsBean> itemsBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        //初始化View
        initRecyleView();
        load.setVisibility(View.VISIBLE);
        //加载数据
        loadData();


        //设置adapter

        adapter = new NomalAdapter(this);
        playRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //加载轮播图片数据
        loadIMg();
        initPoint();
        //让轮播图显示第一张图片
        imgVp.setCurrentItem(1,false);
        //给ViewPager设置滑动监听
        imgVp.addOnPageChangeListener(this);
        startSwitch();
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
        return view;
    }

    private void initPoint() {

        //清空容器里面的布局
        pointContant.removeAllViews();
        //给容器添加布局

        for (int i = 0; i < 3; i++) {
            //小圆点
            View view = new View(this);
            //设置背景颜色
            view.setBackgroundResource(R.drawable.guide_point_gray_shape);
            //设置布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5,5);
            //设置右边距
            params.leftMargin = 10;
            //添加到布局
            pointContant.addView(view,params);
        }
        //设置第一个点为绿色点
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
        itemsBeen = acrivityMessageBean.getResult().getItems();
        int size = acrivityMessageBean.getResult().getItems().size();
        for (int i = -1; i < size +1; i++) {
            String imagPath = null;
            if (i == size) {
                //加载第一张图片
                imagPath = itemsBeen.get(size-1).getImg();
            }else if(i == -1){
                imagPath = itemsBeen.get(0).getImg();
            }else {
                imagPath = itemsBeen.get(i).getImg();
            }
            ImageView imageView = new ImageView(PlayActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(PlayActivity.this).load(imagPath).into(imageView);
            imgs.add(imageView);
        }
        ImgVPAdapter imgadapter = new ImgVPAdapter(imgs, acrivityMessageBean.getResult().getItems(), this);
//        ImgVPAdapter imgadapter = new ImgVPAdapter(imgs);
        imgVp.setAdapter(imgadapter);
        imgadapter.notifyDataSetChanged();


    }


    //加载rv的条目
    private void loadData() {


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

    //开始切换
    public void startSwitch() {
        //注意：如果轮播图未切换轮播，开始发送消失进行，否则反之
        if(!hasSwitch){
            //往Handler里面的消息队列里面发送一个延时的消息
            mHandler.postDelayed(new SwitchTask(),3000);
        }
    }
    private boolean hasSwitch;
    /**
     * 使轮播图自动轮播
     */
    private class SwitchTask implements  Runnable {

        @Override
        public void run() {
            if (imgVp != null) {
                //切换逻辑
                int currentItem = imgVp.getCurrentItem();
                //判断是否在最后一页
                if (currentItem == 0) {
                    currentItem = imgs.size() - 1;
                }else {
                    currentItem ++;
                }
                imgVp.setCurrentItem(currentItem);
            }

            mHandler.postDelayed(this,3000);
        }
    }
    //停止切换
    public void stopSwitch(){
        //清空消息列队
        mHandler.removeCallbacksAndMessages(null);
        hasSwitch = false;
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //修正下标
        int index;
        //正确数据的大小
        int size = itemsBeen.size();
        if(position == 0){
            index = size - 1;
            //切换到最后一个页面
            imgVp.setCurrentItem(size,false);
        }else if(position == size + 1){
            index = 0;
            //切换到第一个页面
            imgVp.setCurrentItem(1,false);
        }else{
            index = position - 1;
        }
        //修改轮播图点的背景
        int childCount =pointContant.getChildCount();
        for (int i = 0; i <childCount ; i++) {
            View child = pointContant.getChildAt(i);
            //此处应改成修正后的角标
            if(index == i){
                //选中的页面
                child.setBackgroundResource(R.drawable.guide_point_red_shape);
            }else{
                //未选中的页面
                child.setBackgroundResource(R.drawable.guide_point_gray_shape);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }




}
