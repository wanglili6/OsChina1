package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.AttentionFriendAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.FriendsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyuanchao on 2017/2/19.
 */

public class FansActivity extends Activity implements View.OnClickListener {

    private ImageButton ibBack;
    private TextView tvMe;
    private XRecyclerView rvFans;
    private boolean isRefreshing;
    private boolean isLoadingMore;
    private int pageIndex = 0;
    final protected int PULL_REFRESH = 0;
    final protected int LOAD_MORE = 1;
    protected int refresh;
    protected int loadmore;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isRefreshing) {
                        isRefreshing = false;
                        rvFans.refreshComplete();
                    }
                    adapter.refresh((FriendsList) msg.obj);
                    break;
                case 1:
                    if (isLoadingMore) {
                        isLoadingMore = false;
                        rvFans.loadMoreComplete();
                    }
                    adapter.loadMore((FriendsList) msg.obj);
                    break;
            }
        }
    };
    private AttentionFriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        initView();
        loadNetData(refresh);
        initEvent();
    }

    private void loadNetData(final int flag) {
        String url = Cantents.FANS_URL;
        Map<String, String> params = new HashMap<>();
        params.put("uid", "0");
        params.put("pageSize", "20");
        if (flag == PULL_REFRESH) {
            params.put("uid", "0");
            params.put("pageIndex", "0");
        } else if (flag == LOAD_MORE) {
            params.put("uid", "0");
            params.put("pageIndex", String.valueOf(++pageIndex));
        }
        OKHttp3Helper
                .create()
                .get(url, null, params, new OKHttp3Helper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //MyLogger.e("网络数据:",result);
                        FriendsList bean = XmlUtils.toBean(FriendsList.class, result.getBytes());

                        Message msg = new Message();

                        if (flag == PULL_REFRESH) {
                            pageIndex = 0;
                            msg.what = 0;
                        } else if (flag == LOAD_MORE) {
                            msg.what = 1;
                        }
                        msg.obj = bean;
                        handler.sendMessage(msg);
                    }
                    @Override
                    public void onFail(Exception e) {
                        MyToast.show(getApplicationContext(),"获取网络数据失败");
                    }
                });
    }

    private void initEvent() {
        ibBack.setOnClickListener(this);
        tvMe.setOnClickListener(this);
        rvFans.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    isRefreshing = true;
                    loadNetData(refresh);
                    //mRv.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                if (!isLoadingMore) {
                    isLoadingMore = true;
                    loadNetData(loadmore);
                }
            }
        });

    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        tvMe = (TextView) findViewById(R.id.tv_me);
        rvFans = (XRecyclerView) findViewById(R.id.rv_fans);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvFans.setLayoutManager(lm);
        adapter = new AttentionFriendAdapter(this);
        rvFans.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view==ibBack||view==tvMe){
            finish();
        }
    }
}
