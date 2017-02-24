package com.bestteam.oschina.fragment.newsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.oschina.R;

import com.bestteam.oschina.adapter.NewsFragmentAdapter.InformationFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.NewsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Why on 2017/2/21.
 */

public class InformationFragment extends Fragment{

    private XRecyclerView xRecyclerView;

    private String pageSize = "20";
    private boolean isRefresh = true;
    private boolean isLoaderMore = false;
    private int pageIndex = 0;
    private InformationFragmentRVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information,container,false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.information_rv);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new InformationFragmentRVAdapter(getContext());
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
        String url = Cantents.NEWS_URL;
        Map<String,String> parmas = new HashMap<>();
        parmas.put("pageIndex", String.valueOf(pageIndex));
        parmas.put("catalog","1");
        parmas.put("pageSize", pageSize);

        OKHttp3Helper.create().get(url, null, parmas, new OKHttp3Helper.HttpCallback() {

            @Override
            public void onSuccess(String data) {
                NewsList newsList = XmlUtils.toBean(NewsList.class, data.getBytes());

                if (isRefresh) {
                    adapter.clear();
                    adapter.addAll(newsList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }

                if (isLoaderMore) {
                    adapter.addAll(newsList.getList());
                    xRecyclerView.loadMoreComplete();
                    isLoaderMore = false;
                }
            }

           @Override
            public void onFail(Exception e) {
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
