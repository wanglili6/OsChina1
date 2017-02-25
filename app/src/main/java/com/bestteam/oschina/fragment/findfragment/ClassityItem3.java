package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.softwareadapter.ClassifyRvAdapter3;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.bestteam.oschina.view.RefreshRecyleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class ClassityItem3 extends Fragment implements RefreshRecyleView.OnLoadMoreListenter {

    private RefreshRecyleView recyclerView;
    private int tag;
    private String url;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler = new Handler();
    private SoftwareList softwareList;


    private void loadData() {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                    }
                });
    }

    public void processData(String response) {
        softwareList = XmlUtils.toBean(SoftwareList.class, response.getBytes());
        ClassifyRvAdapter3 adapter = new ClassifyRvAdapter3(getContext(), softwareList.getList());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        url = Cantents.CLISSIFTY_CLASSIFY_URl + tag;
        View view = inflater.inflate(R.layout.base_reclyview, container, false);
        recyclerView = (RefreshRecyleView) view.findViewById(R.id.base_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
        //设置上拉加载
        recyclerView.setOnLoadMoreListenter(this);
        return view;
    }


    public void setItemTag(int tag) {
        this.tag = tag;

    }


    private int pageIndex = 1;

    @Override
    public void onLoadMaore() {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("pageIndex", ""+pageIndex ++)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SoftwareList softwareListfoot = XmlUtils.toBean(SoftwareList.class, response.getBytes());
                        softwareList.getList().addAll(softwareList.getList().size(), softwareListfoot.getList());
                        //隐藏头
                        recyclerView.hindFooterView();

                    }
                });
    }
}
