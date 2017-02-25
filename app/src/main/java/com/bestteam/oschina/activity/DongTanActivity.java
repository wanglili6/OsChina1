package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.CollectAdapter;
import com.bestteam.oschina.adapter.DongTanAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Favorite;
import com.bestteam.oschina.bean.FavoriteList;
import com.bestteam.oschina.bean.Tweet;
import com.bestteam.oschina.bean.TweetsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tywei on 2017/2/20.
 */
public class DongTanActivity extends Activity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;

    private boolean isRefresh = true;
    private boolean isLoadMore = false;
    private int pageIndex = 0;
    private String pageSize = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongtan);
        ButterKnife.bind(this);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        //设置下拉刷新的监听
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//刷新处理
                isRefresh = true;
                initRequestData();
            }

            @Override
            public void onLoadMore() {//加载更多
                isLoadMore = true;
                pageIndex = pageIndex + 1;
                initRequestData();
            }
        });
        initRequestData();
    }

    private void initRequestData() {
        String url = Cantents.DONG_TAN_URL;
        String uid = SPUtils.getString(this, Cantents.MY_UID, "");
        String cookie = SPUtils.getString(this, Cantents.MY_COOKIE, "");
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", uid);
        parmas.put("pageIndex", String.valueOf(pageIndex));
        parmas.put("pageSize", pageSize);

        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);
        OKHttp3Helper.create().get(url, headers, parmas, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String result) {//直接运行在主线程中
                TweetsList tweetsLists = XmlUtils.toBean(TweetsList.class, result.getBytes());
                //创建适配器
                DongTanAdapter dongtanAdapter = new DongTanAdapter(DongTanActivity.this);
                List<Tweet> lists = tweetsLists.getList();
                if (isRefresh) {//刷新
                    dongtanAdapter.clear();
                    dongtanAdapter.addAll(lists);
                    xRecyclerView.refreshComplete();//结束刷新的隐藏
                    isRefresh = false;
                }
                if (isLoadMore) {//加载更多
                    dongtanAdapter.addAll(lists);
                    xRecyclerView.loadMoreComplete();
                    isLoadMore = false;
                }
                //创建adapter
                xRecyclerView.setAdapter(dongtanAdapter);


            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(DongTanActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.ib_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
