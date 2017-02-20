package com.bestteam.oschina.fragment.MessageCenterFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.AtMeFragmentRVAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragment extends Fragment {
    private RecyclerView rv;
    private List<String> mDatas;
    private AtMeFragmentRVAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_atme,container,false);
        rv = (RecyclerView) view.findViewById(R.id.rv_atme);
        initData();
        return view;
    }
    private void initData(){
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://www.oschina.net/action/api/active_list?uid=993896&pageIndex=0&catalog=3&pageSize=20";

        mDatas = new ArrayList<>();
        for(int i = 0; i<50;i++){
            mDatas.add("用户名"+i);
        }
        mAdapter = new AtMeFragmentRVAdapter(getContext(),mDatas);
        rv.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }
}

