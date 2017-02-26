package com.bestteam.oschina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.LoginActivity;
import com.bestteam.oschina.activity.TweetDetailActivity;
import com.bestteam.oschina.adapter.NewTweetAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Tweet;
import com.bestteam.oschina.bean.TweetsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.SPUtils;
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

public abstract class DadTweetFragment extends BaseTweetFragment {


    private NewTweetAdapter adapter;
    private boolean isRefreshing;
    private boolean isLoadingMore;
    private int pageIndex = 0;
    final protected int PULL_REFRESH_NEW = 0;
    final protected int LOAD_MORE_NEW = 1;
    final protected int PULL_REFRESH_HOT = 2;
    final protected int LOAD_MORE_HOT = 3;
    final protected int PULL_REFRESH_ME = 4;
    final protected int LOAD_MORE_ME = 5;
    final public int MYTWEET_CODE = 101;
    protected int refresh;
    protected int loadmore;
    public int tweetCount;
    private int pageSize =20;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isRefreshing) {
                        isRefreshing = false;
                        mRv.refreshComplete();
                    }
                    adapter.refresh((TweetsList) msg.obj);
                    break;
                case 1:
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
        loadNetData(refresh);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivityForResult(intent,MYTWEET_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            loadNetData(PULL_REFRESH_ME);
            rlWait.setVisibility(View.GONE);
            mRv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initRecyclerView() {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewTweetAdapter(getContext());
        mRv.setAdapter(adapter);
    }

    @Override
    public void initRefresh() {
        mRv.setLoadingListener(new XRecyclerView.LoadingListener() {
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


    public void loadNetData(final int flag) {
        //String url = Cantents.BASE_URL_TWEET + Cantents.NEW_TWEET_URL;
        String uid = SPUtils.getString(getContext(), Cantents.MY_UID, "");
        OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
        Map<String, String> params = new HashMap<>();
        params.put("uid", "0");
        params.put("pageSize", pageSize+"");
        if (flag == PULL_REFRESH_NEW) {
            pageIndex = 0;
            params.put("uid", "0");
            params.put("pageIndex", pageIndex+"");
        } else if (flag == LOAD_MORE_NEW) {
            params.put("uid", "0");
            params.put("pageIndex", String.valueOf(++pageIndex));
        } else if (flag == PULL_REFRESH_HOT) {
            pageIndex = 0;
            params.put("uid", "-1");
            params.put("pageIndex", "0");
        } else if (flag == LOAD_MORE_HOT) {
            params.put("uid", "-1");
            params.put("pageIndex", String.valueOf(++pageIndex));
        } else if (flag == PULL_REFRESH_ME) {
            if(TextUtils.isEmpty(uid)){
                rlWait.setVisibility(View.VISIBLE);
                mRv.setVisibility(View.GONE);
            }else {
                rlWait.setVisibility(View.GONE);
                mRv.setVisibility(View.VISIBLE);
            }
            pageIndex = 0;
            params.put("uid", uid);
            params.put("pageIndex", "0");
        } else if (flag == LOAD_MORE_ME) {
            params.put("uid", uid);
            params.put("pageIndex", String.valueOf(++pageIndex));
        }

        okHttp3Helper.get(Cantents.BASE_URL_TWEET, null, params, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                final TweetsList bean = XmlUtils.toBean(TweetsList.class, data.getBytes());
                tweetCount = bean.getTweetCount();
                SPUtils.saveInt(getContext(),"count",tweetCount);
                Message msg = new Message();

                if (flag == PULL_REFRESH_NEW || flag ==PULL_REFRESH_HOT || flag == PULL_REFRESH_ME) {
                    pageIndex = 0;
                    msg.what = 0;
                } else if (flag == LOAD_MORE_NEW || flag == LOAD_MORE_HOT || flag == LOAD_MORE_ME) {
                    //pageIndex++;
                    msg.what = 1;
                }
                msg.obj = bean;
                handler.sendMessage(msg);
                Log.i("pageIndex", "............." + pageIndex);
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(getActivity(),"获取网络失败",Toast.LENGTH_SHORT).show();
                mRv.refreshComplete();
                mRv.loadMoreComplete();
            }
        });
        /*OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
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
        }*/

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
