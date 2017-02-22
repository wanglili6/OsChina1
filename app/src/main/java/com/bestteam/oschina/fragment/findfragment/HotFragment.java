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
public class HotFragment extends BaseFindLoadNetData {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View  view = inflater.inflate(R.layout.layout_sub_classify_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String url = Cantents.CLISSIFTY_Item2_URl+"view";
        loadNetData(url,recyclerView);
        return view;
    }
}
