package com.bestteam.oschina.fragment.MessageCenterFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.AtMeFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.MessageList;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragment extends Fragment {
    private RecyclerView rv;
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
        String  url = Cantents.COMMENT_MESSAGE_CENTER;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(),"网络请求错误",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                    }

                });



       /* mDatas = new ArrayList<>();
        for(int i = 0; i<50;i++){
            mDatas.add("用户名"+i);
        }
        mAdapter = new AtMeFragmentRVAdapter(getContext(),mDatas);
        rv.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();*/
    }
    private void processData(String response){
        MessageList messageList = XmlUtils.toBean(MessageList.class, response.getBytes());
        mAdapter = new AtMeFragmentRVAdapter(getContext(),messageList.getList());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}

