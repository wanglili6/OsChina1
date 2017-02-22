package com.bestteam.oschina.base;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter3;
import com.bestteam.oschina.bean.Software;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.bestteam.oschina.view.RefreshRecyleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/21.
 */

public class BaseLoadData{
    private View view;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler = new Handler();
    private RefreshRecyleView havaHeaderRv;
    private SoftwareList softwareList;

    public BaseLoadData(Context context) {
        this.context = context;
        view = initView();
    }

    private View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.base_reclyview, null);
        initReFreshView(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srefresh);
        initSwipe();
        return view;
    }
    //初始化RefreshRecyleView
    private void initReFreshView(View view) {
        havaHeaderRv = (RefreshRecyleView) view.findViewById(R.id.base_rv);


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
                        OkHttpUtils
                                .post()
                                .url("https://www.oschina.net/action/api/software_list?searchTag=recommend")
                                .build()
                                .execute(new StringCallback() {

                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        MyToast.show(context,"数据加载失败");
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        MyToast.show(context,"加载成功");
                                        softwareList = XmlUtils.toBean(SoftwareList.class,
                                                response.getBytes());
                                        havaHeaderRv.setLayoutManager(new LinearLayoutManager(context));
                                        ClassifyRvAdapter3 classifyRvAdapter3 = new ClassifyRvAdapter3(context,softwareList.getList());
                                        havaHeaderRv.setAdapter(classifyRvAdapter3);

                                        softwareList.getList().addAll(0,softwareList.getList());
                                        classifyRvAdapter3.notifyDataSetChanged();
                                    }
                                });
                    }
                },1000);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
