package com.bestteam.oschina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SoftwareCatalogList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.google.gson.Gson;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class ClassifyFragment extends Fragment {

    private RecyclerView fragmentRv;
    private ClassifyRvAdapter classifyRvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler_view,container,false);
        initView(view);
        initRecyleView();
        loadNetData();

        return view;
    }


    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        fragmentRv = (RecyclerView) view.findViewById(R.id.fragment_rv);
    }

    /**
     * 初始化RecycleVIew
     */
    private void initRecyleView() {
        //设置布局管理器
        fragmentRv.setLayoutManager(new LinearLayoutManager(getContext()));

        //设置适配器


    }

    /**
     * 获取网络数据
     */
    private void loadNetData() {
        OkHttpUtils
                .get()
                .url(Cantents.CLISSIFTY_URl+"0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SoftwareCatalogList softwareCatalogList =
                                XmlUtils.toBean(SoftwareCatalogList.class, response.getBytes());
                        classifyRvAdapter = new ClassifyRvAdapter(getContext(),
                                softwareCatalogList.getSoftwarecataloglist());
                        fragmentRv.setAdapter(classifyRvAdapter);



                    }


                });
    }


}
