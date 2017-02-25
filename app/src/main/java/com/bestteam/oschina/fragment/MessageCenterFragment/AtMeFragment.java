package com.bestteam.oschina.fragment.MessageCenterFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.LoginActivity;
import com.bestteam.oschina.adapter.AtMeFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.ActiveList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragment extends Fragment {
    private AtMeFragmentRVAdapter mAdapter;
    private int pageIndex = 0;
    private String cataLog = "3";
    private String pageSize = "20";
    private XRecyclerView xRecyclerView;
    private boolean isRefresh = true;
    private boolean isLoad = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_atme, container, false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_atme);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AtMeFragmentRVAdapter(getContext());
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 0;
                initData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                pageIndex = pageIndex + 1;
                initData();
            }
        });
        initData();
    }

    private void initData() {
        String url = Cantents.COMMENT_MESSAGE_CENTER;

        String uid = SPUtils.getString(getContext(),Cantents.MY_UID,"");
        String cookie = SPUtils.getString(getContext(),Cantents.MY_COOKIE,"");
        if(uid.isEmpty()){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else{
            request_data_1(url, uid, cookie);

        }

    }


    private void request_data_1(String url, String uid, String cookie) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", uid);
        parmas.put("pageIndex", String.valueOf(pageIndex));
        parmas.put("pageSize", pageSize);
        parmas.put("catalog", cataLog);

        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);

        OKHttp3Helper.create().get(url, headers, parmas, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {

               ActiveList activeList = XmlUtils.toBean(ActiveList.class, data.getBytes());

                if(activeList.getList().isEmpty()){
                   Toast.makeText(getContext(),"好像最近没有动态",Toast.LENGTH_SHORT).show();
                }
                if (isRefresh) {
                    mAdapter.clear();

                    mAdapter.addAll(activeList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }
                if (isLoad) {
                    mAdapter.addAll(activeList.getList());
                    xRecyclerView.loadMoreComplete();
                    isLoad = false;
                }



            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

          /*  @Override
            public void onFail(Exception e) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    /*private void processData(String response){
        MessageList messageList = XmlUtils.toBean(MessageList.class, response.getBytes());
        mAdapter = new AtMeFragmentRVAdapter(getContext(),messageList.getList());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }*/


