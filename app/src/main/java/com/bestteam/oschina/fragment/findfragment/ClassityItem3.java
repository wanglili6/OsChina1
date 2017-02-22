package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.BaseFindLoadNetData;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.view.RefreshRecyleView;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class ClassityItem3 extends BaseFindLoadNetData{

    private RefreshRecyleView recyclerView;
    private int tag;
    private String url;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler = new Handler();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNetData(url,recyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_reclyview,container,false);

        recyclerView = (RefreshRecyleView) view.findViewById(R.id.base_rv);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        url = Cantents.CLISSIFTY_CLASSIFY_URl + tag;
        loadNetData(url,recyclerView);
        initSwipe();
        return view;
    }


    public void setItemTag(int tag){
        this.tag=tag;

    }

    private void initSwipe() {
        //设置下拉刷新的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置下拉刷新回调
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadNetData(url, recyclerView);
                    }
                },1000);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
