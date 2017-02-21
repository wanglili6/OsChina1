package com.bestteam.oschina.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bestteam.oschina.adapter.NewTweetAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Tweet;
import com.bestteam.oschina.bean.TweetsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bestteam.oschina.activity.MyApplication.getContext;


/**
 * Created by 45011 on 2017/2/19.
 */

public class NewTweetFragment extends BaseTweetFragment {


    private NewTweetAdapter adapter;
    private boolean isRefreshing;
    private boolean isLoadingMore;
    private int pageIndex = 0;
    final private int PULL_REFRESH = 0;
    final private int LOAD_MORE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PULL_REFRESH:
                    if (isRefreshing) {
                        isRefreshing = false;
                        mRv.refreshComplete();
                    }
                    adapter.refresh((TweetsList) msg.obj);
                    break;
                case LOAD_MORE:
                    if (isLoadingMore) {
                        isLoadingMore = false;
                        mRv.loadMoreComplete();
                    }
                    adapter.loadMore((TweetsList) msg.obj);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNetData(PULL_REFRESH);
    }

    @Override
    public void initRecyclerView() {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewTweetAdapter(getContext());
        mRv.setAdapter(adapter);
    }

    @Override
    public void initRefresh() {
        /*mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetData();
                mSwipe.setRefreshing(false);
            }
        });*/
        mRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    isRefreshing = true;
                    loadNetData(PULL_REFRESH);
                    //mRv.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                if (!isLoadingMore) {
                    isLoadingMore = true;
                    loadNetData(LOAD_MORE);
                }
            }
        });
    }

    public void loadNetData(int flag) {
        String url = Cantents.BASE_URL_TWEET + Cantents.NEW_TWEET_URL;

        OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
        if (flag == PULL_REFRESH) {
            okHttp3Helper.get(url, new OKHttp3Helper.HttpCallback() {
                @Override
                public void onSuccess(String data) {
                    final TweetsList bean = XmlUtils.toBean(TweetsList.class, data.getBytes());
                    pageIndex++;
                    Message msg = new Message();
                    msg.what = PULL_REFRESH;
                    msg.obj = bean;
                    handler.sendMessage(msg);
                }

                @Override
                public void onFail(Exception e) {

                }
            });
        } else if (flag == LOAD_MORE) {
            Map<String, String> params = new HashMap<>();
            params.put("uid", "0");
            params.put("pageSize", "20");
            params.put("pageIndex", String.valueOf(pageIndex));
            okHttp3Helper.get(Cantents.BASE_URL_TWEET, null, params, new OKHttp3Helper.HttpCallback() {
                @Override
                public void onSuccess(String data) {
                    pageIndex++;
                    final TweetsList bean = XmlUtils.toBean(TweetsList.class, data.getBytes());

                    Message msg = new Message();
                    msg.what = LOAD_MORE;
                    msg.obj = bean;
                    handler.sendMessage(msg);
                }

                @Override
                public void onFail(Exception e) {

                }
            });
        }

        /*OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "网络加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i("testload", "成功" + response);
                final TweetsList bean = XmlUtils.toBean(TweetsList.class, response.body().bytes());
                *//*Log.i("testload","成功"+bean.toString());
                Log.i("testload","成功...."+bean.getTweetCount());
                Log.i("testload","成功...."+bean.getPagesize());
                Log.i("testload","成功...."+bean.getTweetslist().size());*//*
                *//*Message msg = new Message();
                msg.what = 1;
                msg.obj = bean;
                handler.sendMessage(msg);*//*
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefreshing) {
                            isRefreshing = false;
                            mRv.refreshComplete();
                        }
                        adapter.refresh(bean);

                    }
                });
            }
        });*/
    }
}
