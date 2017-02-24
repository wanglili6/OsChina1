package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter3;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/18.
 * 国产
 */
public class DomesticFragment extends Fragment implements XRecyclerView.LoadingListener {

    private XRecyclerView recyclerView;
    private String url;
    private ProgressBar loadBar;
    private int pageIndex = 0;
    private ClassifyRvAdapter3 classifyRvAdapter3;
    private boolean isRefresh = true;
    private boolean isLoadMoare = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取地址
        url = Cantents.CLISSIFTY_Item2_URl + "list_cn";

        //创建布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classifyRvAdapter3 = new ClassifyRvAdapter3(getContext());
        recyclerView.setAdapter(classifyRvAdapter3);
        classifyRvAdapter3.notifyDataSetChanged();

        recyclerView.setLoadingListener(this);


        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);
        onLoadMaore();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.refresh_recyleview, container, false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.refresh_xrv);
        loadBar = (ProgressBar) view.findViewById(R.id.load);
        return view;
    }


    public void onLoadMaore() {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("pageIndex", "" + pageIndex)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SoftwareList softwareList = XmlUtils.toBean(SoftwareList.class, response.getBytes());
                        if (isRefresh){
                            classifyRvAdapter3.clear();
                            classifyRvAdapter3.addAll(softwareList.getSoftwarelist());
                            recyclerView.refreshComplete();
                        }

                        if (isLoadMoare){
                            classifyRvAdapter3.addAll(softwareList.getSoftwarelist());
                            recyclerView.loadMoreComplete();
                        }

                    }
                });
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        pageIndex=0;
        onLoadMaore();

    }

    @Override
    public void onLoadMore() {
        isLoadMoare = true;
        pageIndex++;
        onLoadMaore();

    }
}
