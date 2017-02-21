package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter;
import com.bestteam.oschina.adapter.ClassifyRvAdapter3;
import com.bestteam.oschina.base.BaseFindLoadNetData;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SoftwareCatalogList;
import com.bestteam.oschina.bean.SoftwareDec;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class ClassityItem3 extends BaseFindLoadNetData{

    private RecyclerView recyclerView;
    private int tag;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.layout_sub_classify_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        url = Cantents.CLISSIFTY_CLASSIFY_URl + tag;
        loadNetData(url,recyclerView);
        return view;
    }
    public void setItemTag(int tag){
        this.tag=tag;

    }
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
                        MyToast.show(getContext(), "数据加载1111");
                        SoftwareList softwareList = XmlUtils.toBean(SoftwareList.class, response.getBytes());
                        ClassifyRvAdapter3 adapter = new ClassifyRvAdapter3(getFragmentManager(),getContext(),softwareList.getList());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
