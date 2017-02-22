package com.bestteam.oschina.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.UpToLoadAdapter;


/**
 * Created by JTY on 2017/2/19.
 * <p>
 * 列表样式Fragment，列表通过RecyclerView展现，所以构造需要传入Adapter
 */

public class RefreshListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private BasePresenter presenter;
    private UpToLoadAdapter upToLoadAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;

    //定义状态
    private final int STATUS_IDLE = 0;
    private final int STATUS_LOADING = 1;
    private final int STATUS_REFRESHING = 2;
    private int status;


    public RefreshListFragment(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        upToLoadAdapter = new UpToLoadAdapter(adapter);
        status = STATUS_IDLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment_list, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.base_fragment_list_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.base_fragment_list_recycler);
        initView();
        return view;
    }

    private void initView() {
        //设置下拉刷新进度条的颜色
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);
        //设置刷新监听器
        refreshLayout.setOnRefreshListener(this);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(upToLoadAdapter);

        //监听滑动状态，判断啥时候到底部
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isScrolledToEnd();
            }
        });

        initData();
    }

    /**
     * 初始化数据，从缓存获取数据显示，并开始请求网络
     */
    private void initData() {
        //TODO：从缓存获取数据

        //从网络获取
        onRefresh();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //isScrolledToEnd();
    }

    private boolean isAtEnd;

    /**
     * 判断是否滑动到底部，如果滑动到底部，执行加载更多
     */
    private void isScrolledToEnd() {
        int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = linearLayoutManager.findLastVisibleItemPosition();

        //如果一页没有显示完
        if (firstPosition > 0) {
            //之前不是在底部
            if (!isAtEnd) {
                //判断是否到达底部
                if (lastPosition == upToLoadAdapter.getItemCount() - 1) {
                    //滑动到了底部,加载更多
                    if (status == STATUS_IDLE) {
                        isAtEnd = true;
                        status = STATUS_LOADING;
                        onLoad();
                    }
                }
            } else {
                if (lastPosition != upToLoadAdapter.getItemCount() - 1) {
                    //最下面的条目已经不是foot了，才可以恢复状态
                    isAtEnd = false;
                }
            }
        } else {
            //一页就加载完了，露出了脚布局
            requestLoadIsEnd(true);
        }


    }

    /**
     * 设置网络加载业务逻辑的主持者
     *
     * @param presenter
     */
    public void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 通知下拉刷新完成
     */
    public void requestFinishRefresh() {
        refreshLayout.setRefreshing(false);
        status = STATUS_IDLE;
    }

    /**
     * 通知上拉加载更多完成
     */
    public void requestFinishLoad() {
        upToLoadAdapter.notifyDataSetChanged();
        status = STATUS_IDLE;
    }

    /**
     * 通知已经没有更多了
     *
     * @param isEnd
     */
    public void requestLoadIsEnd(boolean isEnd) {
        upToLoadAdapter.isEnd(isEnd);
        status = STATUS_IDLE;
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        if (presenter != null) {
            status = STATUS_REFRESHING;
            presenter.doRefresh();
        }
    }

    /**
     * 上拉加载更多
     */
    public void onLoad() {
        if (presenter != null) {
            presenter.doLoad();
        }
    }

    /**
     * 获取适配器
     * @return RecyclerView的Adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    /**
     * 通知适配器数据改变
     */
    public void notifyAdapterDateListChange(){
        adapter.notifyDataSetChanged();
        upToLoadAdapter.notifyDataSetChanged();

    }
}
