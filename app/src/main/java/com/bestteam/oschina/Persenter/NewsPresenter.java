package com.bestteam.oschina.persenter;

import android.content.Context;
import android.widget.Toast;

import com.bestteam.oschina.adapter.newsfragmentadapter.InformationFragmentRVAdapter;
import com.bestteam.oschina.base.BaseRecyclerViewFragment;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.NewsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.XmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Why on 2017/2/21.
 */

public class NewsPresenter {

    private String pageSize = "20";
    private Context context;
    private BaseRecyclerViewFragment fragment;
    private InformationFragmentRVAdapter adapter;

    public NewsPresenter(Context context,BaseRecyclerViewFragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }



    public void getNewsList() {
        //封装地址和请求
        String url = Cantents.NEWS_URL;
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", "0");
        params.put("catalog", "1");
        params.put("pageSize", pageSize);

        OKHttp3Helper.create().get(url, null, params, new OKHttp3Helper.HttpCallback() {



            @Override
            public void onSuccess(String data) {
                NewsList newsList = XmlUtils.toBean(NewsList.class,data.getBytes());
                adapter = new InformationFragmentRVAdapter(context);
                adapter.clear();
                adapter.addAll(newsList.getList());
                fragment.xRecyclerView.setAdapter(adapter);
                fragment.xRecyclerView.refreshComplete();
                fragment.isRefresh = false;
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int pageIndex = 0;
    public void getLoaderMoreNewsList() {
        pageIndex += 1;
        String url = Cantents.NEWS_URL;
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("catalog", "1");
        params.put("pageSize", pageSize);

        OKHttp3Helper.create().get(url, null, params, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                //获取响应结果
                NewsList newsList = XmlUtils.toBean(NewsList.class, data.getBytes());
                adapter.addAll(newsList.getList());
                fragment.xRecyclerView.setAdapter(adapter);
                fragment.xRecyclerView.loadMoreComplete();
                fragment.isLoaderMore = false;
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
