package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter3;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.bestteam.oschina.view.RefreshRecyleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class PlayActivity extends Activity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_activity)
    TextView tvActivity;
    @BindView(R.id.base_rv)
    RefreshRecyleView baseRv;
    @BindView(R.id.srefresh)
    SwipeRefreshLayout srefresh;
    private Handler mHandler = new Handler();
    private SoftwareList softwareList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        initSwipe();
    }


    private void initSwipe() {
        //设置下拉刷新的颜色
        srefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置下拉刷新回调
        srefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                },1000);
                srefresh.setRefreshing(false);
            }
        });
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url("https://www.oschina.net/action/api/software_list?searchTag=recommend")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(PlayActivity.this,"数据加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyToast.show(PlayActivity.this,"加载成功");
                        softwareList = XmlUtils.toBean(SoftwareList.class,
                                response.getBytes());
                        baseRv.setLayoutManager(new LinearLayoutManager(PlayActivity.this));
                        ClassifyRvAdapter3 classifyRvAdapter3 = new ClassifyRvAdapter3(PlayActivity.this,softwareList.getList());
                        baseRv.setAdapter(classifyRvAdapter3);

                        softwareList.getList().addAll(0,softwareList.getList());
                        classifyRvAdapter3.notifyDataSetChanged();
                    }
                });
    }

    @OnClick(R.id.ib_back)
    public void onClick() {
        finish();
    }
}
