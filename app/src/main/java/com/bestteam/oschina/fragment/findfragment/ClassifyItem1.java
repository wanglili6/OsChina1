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

public class ClassifyItem1 extends Fragment {

    private RecyclerView fragmentRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sub_classify_fragment,container,false);
        fragmentRv = (RecyclerView) view.findViewById(R.id.fragment_rv);
        fragmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    private void loadData(){
        OkHttpUtils
                .get()
                .url(Cantents.CLISSIFTY_URl+"0")
                .build()
                .execute(new StringCallback() {

                    private SoftwareCatalogList softwareCatalogList;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        MyToast.show(getContext(), "数据加载成功");
                        softwareCatalogList = XmlUtils.toBean(SoftwareCatalogList.class, response.getBytes());
                        ClassifyRvAdapter adapter = new ClassifyRvAdapter(getFragmentManager(),getContext(), softwareCatalogList.getSoftwarecataloglist());
                        fragmentRv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
