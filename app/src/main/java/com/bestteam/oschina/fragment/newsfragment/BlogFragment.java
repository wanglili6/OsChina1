package com.bestteam.oschina.fragment.newsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.BlogFragmentAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.BlogList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Why on 2017/2/19.
 */

public class BlogFragment extends Fragment {

    private XRecyclerView xRecyclerView;
    private String pageSize = "20";
    private BlogFragmentAdapter adapter;
    private int pageIndex = 0;
    private boolean isRefresh = true;
    private boolean isLoaderMore = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog,container,false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.blog_rv);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BlogFragmentAdapter(getContext());
        xRecyclerView.setAdapter(adapter);


        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 0;
                requestData();
            }

            @Override
            public void onLoadMore() {
                isLoaderMore = true;
                pageIndex = pageIndex + 1;
                requestData();
            }
        });

        requestData();
    }

    private void requestData() {
        String url = Cantents.BOLG_URL;
        Map<String,String> params = new HashMap<>();
        //pageIndex=0&pageSize=20&type=latest
        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("pageSezi",pageSize);
        params.put("type","latest");

        OKHttp3Helper.create().get(url, null, params, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                BlogList blogList = XmlUtils.toBean(BlogList.class, data.getBytes());

                if (isRefresh) {
                    adapter.clear();
                    adapter.addAll(blogList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }

                if (isLoaderMore) {
                    adapter.addAll(blogList.getList());
                    xRecyclerView.loadMoreComplete();
                    isLoaderMore = false;
                }
            }

            @Override
            public void onFail(Exception e) {
                MyToast.show(getContext(),"获取数据失败");
            }
        });
    }
}
