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
import com.bestteam.oschina.adapter.PrivateMessageFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.MessageList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class PrivateMessageFragment extends Fragment {
    private XRecyclerView xRecyclerView;
    private PrivateMessageFragmentRVAdapter mAdapter;
    private String cataLog = "3";
    private boolean isRefresh = true;
    private boolean isLoad = false;
    private  int pageIndex = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_privartemessage, container, false);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_privatemessage);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new PrivateMessageFragmentRVAdapter(getContext());
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
    /*http://www.oschina.net/action/api/message_list?uid=993896&pageIndex=0&pageSize=20*/
    private void initData() {
        String url = Cantents.COMMENT_DETAIL;

        String uid = SPUtils.getString(getContext(),Cantents.MY_UID,"");
        String cookie = SPUtils.getString(getContext(),Cantents.MY_COOKIE,"");
        if(uid.isEmpty()){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else{
            request_data(url, uid, cookie);

        }

    }


    private void request_data(String url, String uid, String cookie) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", uid);
        parmas.put("pageIndex", "0");
        parmas.put("pageSize", "20");
        /*parmas.put("catalog", cataLog);*/

        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);

        OKHttp3Helper.create().get(url, headers, parmas, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {

                MessageList messageList = XmlUtils.toBean(MessageList.class, data.getBytes());

                if(messageList.getList().isEmpty()){
                    Toast.makeText(getContext(),"好像最近没有动态",Toast.LENGTH_SHORT).show();
                }
                if (isRefresh) {
                    mAdapter.clear();

                    mAdapter.addAll(messageList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }
                if (isLoad) {
                    mAdapter.addAll(messageList.getList());
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
