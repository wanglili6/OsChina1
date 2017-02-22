package com.bestteam.oschina.base;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bestteam.oschina.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Why on 2017/2/21.
 */

public class BaseRecyclerViewFragment extends BaseFragment {

    public XRecyclerView xRecyclerView;
    public boolean isRefresh = true;
    public boolean isLoaderMore = false;


    @Override
    public int getLayoutID() {
        return R.layout.layout_base_recyclerview;
    }

    @Override
    protected void initView(View view) {
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        //下拉刷新进度条样式
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        //加载更多进度条的样式
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        //循序下拉刷新和上拉加载
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);

        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {   //刷新
                isRefresh = true;
                requestData();
            }

            @Override
            public void onLoadMore() {  //加载
                isLoaderMore = true;
                requestData();
            }
        });
    }


    @Override
    protected void requestData() {
        if (isRefresh) {
            refreshRequestData();
        }

        if (isLoaderMore) {
            loaderMoreRequestData();
        }

    }


    public void refreshRequestData() {

    }

    public void loaderMoreRequestData() {

    }
}
