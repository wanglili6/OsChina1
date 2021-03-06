package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.softwareadapter.ClassifyRvAdapter2;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SoftwareCatalogList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class ClassityItem2 extends Fragment{

    private RecyclerView recyclerView;
    private int tag;
    private LinearLayout loadBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.layout_sub_classify_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_rv);
        loadBar = (LinearLayout) view.findViewById(R.id.load);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
        return view;
    }
    public void setItemTag(int tag){
        this.tag=tag;

    }

    private void loadData() {
        loadBar.setVisibility(View.VISIBLE);
        OkHttpUtils
                .get()
                .url(Cantents.CLISSIFTY_URl+tag)
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        loadBar.setVisibility(View.GONE);
                        SoftwareCatalogList softwareCatalogList = XmlUtils.toBean(SoftwareCatalogList.class, response.getBytes());
                        ClassifyRvAdapter2 adapter = new ClassifyRvAdapter2(getFragmentManager(),getContext(), softwareCatalogList.getSoftwarecataloglist());
                        recyclerView.setAdapter(adapter);


                    }
                });
    }
}
