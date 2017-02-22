package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.BaseFindLoadNetData;
import com.bestteam.oschina.base.Cantents;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class DomesticFragment extends BaseFindLoadNetData {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.layout_sub_classify_fragment,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_rv);
        //创建布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //获取地址
        String url = Cantents.CLISSIFTY_Item2_URl+"list_cn";

        //调用发现抽取的父fragment---BaseFindLoadNetData--加载数据的方法
        loadNetData(url,recyclerView);
        return view;
    }
}
