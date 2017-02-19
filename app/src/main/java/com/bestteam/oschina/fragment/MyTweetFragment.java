package com.bestteam.oschina.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.bestteam.oschina.adapter.NewTweetAdapter;

/**
 * Created by 45011 on 2017/2/19.
 */

public class MyTweetFragment extends BaseTweetFragment {
private Handler handler = new Handler();
    @Override
    public void initRecyclerView() {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        NewTweetAdapter adapter = new NewTweetAdapter(getContext());
        mRv.setAdapter(adapter);
}

    @Override
    public void initRefresh() {
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
