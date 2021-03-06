package com.bestteam.oschina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bestteam.oschina.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by 45011 on 2017/2/19.
 */

public abstract class BaseTweetFragment extends Fragment {

    //protected SwipeRefreshLayout mSwipe;
    protected XRecyclerView mRv;
    protected RelativeLayout rlWait;
    protected Button btLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_dongtan,container,false);

        //mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_dongtan);
        mRv = (XRecyclerView) view.findViewById(R.id.rv_dongtan);
        rlWait = (RelativeLayout) view.findViewById(R.id.rl_wait);
        btLogin = (Button) view.findViewById(R.id.bt_login_tweet);
        setFlag();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initRefresh();
    }

    public abstract void initRecyclerView();

    public abstract void initRefresh();

    public abstract void setFlag();
}
