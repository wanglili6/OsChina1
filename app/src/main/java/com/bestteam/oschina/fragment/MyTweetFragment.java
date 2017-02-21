package com.bestteam.oschina.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.bestteam.oschina.adapter.NewTweetAdapter;

/**
 * Created by 45011 on 2017/2/19.
 */

public class MyTweetFragment extends DadTweetFragment {

    @Override
    public void setFlag() {
        refresh = PULL_REFRESH_ME;
        loadmore = LOAD_MORE_ME;
    }
}
