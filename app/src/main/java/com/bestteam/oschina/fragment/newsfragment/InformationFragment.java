package com.bestteam.oschina.fragment.newsfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.newsfragmentadapter.InformationFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.NewsList;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.OkHeaders;

/**
 * Created by Why on 2017/2/19.
 */

public class InformationFragment extends Fragment {

    private XRecyclerView rv;
    private InformationFragmentRVAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_information, container, false);
        rv = (XRecyclerView) view.findViewById(R.id.rv);

        initDatas();
        return view;
    }


    private void initDatas() {
        loadNetDatas();

        //设置xRecyclerView样式
        rv.setRefreshProgressStyle(ProgressStyle.BallBeat);
        rv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);

        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                refreshData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                loadMoreData();
            }
        });
    }

    //下拉刷新
    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNetDatas();
                adapter.notifyDataSetChanged();
                rv.refreshComplete();
            }
        }, 1000);
    }

    //上拉加载
    private void loadMoreData() {

    }

    //获取网络数据
    private void loadNetDatas() {

        String url = Cantents.NEWS;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        processDatas(response);
                    }
                });
    }

    //解析数据
    private void processDatas(String response) {

        NewsList newsList = XmlUtils.toBean(NewsList.class, response.getBytes());
        //设置Adapter
        adapter = new InformationFragmentRVAdapter(getContext(),newsList.getList());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }
}
