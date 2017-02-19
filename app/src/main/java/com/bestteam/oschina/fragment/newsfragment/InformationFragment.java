package com.bestteam.oschina.fragment.newsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.InformationFragmentRVAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Why on 2017/2/19.
 */

public class InformationFragment extends Fragment {

    private RecyclerView rv;
    private List<String> mDatas;
    private InformationFragmentRVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_information, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);

        initDatas();
        return view;
    }

    private void initDatas() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String str = "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" + i;
            mDatas.add(str);
        }
        adapter = new InformationFragmentRVAdapter(getContext(),mDatas);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }




}
