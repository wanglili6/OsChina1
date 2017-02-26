package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.softwareadapter.ClassifyRvAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SoftwareCatalogList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;

import java.util.Map;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class ClassifyItem1 extends Fragment {

    private RecyclerView fragmentRv;
    private LinearLayout loadBar;
    private SoftwareCatalogList softwareCatalogList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sub_classify_fragment,container,false);
        fragmentRv = (RecyclerView) view.findViewById(R.id.fragment_rv);
        loadBar = (LinearLayout) view.findViewById(R.id.load);
        fragmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    private void loadData(){
        loadBar.setVisibility(View.VISIBLE);
        Map<String,String> map = new ArrayMap<>();

        OKHttp3Helper.create().get(Cantents.CLISSIFTY_URl + "0", null, map, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                loadBar.setVisibility(View.GONE);
                softwareCatalogList = XmlUtils.toBean(SoftwareCatalogList.class, data.getBytes());
                ClassifyRvAdapter adapter = new ClassifyRvAdapter(getFragmentManager(),getContext(), softwareCatalogList.getSoftwarecataloglist());
                fragmentRv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Exception e) {
                MyToast.show(getContext(), "数据加载失败");
            }
        });

    }
}
