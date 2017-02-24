package com.bestteam.oschina.fragment.MessageCenterFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.AtMeFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.CommentList;
import com.bestteam.oschina.bean.MessageList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
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
    private String cataLog = "2";
    private String pageSize = "20";
    private XRecyclerView xRecyclerView;
    private boolean isRefresh = true;
    private boolean isLoad = false;
    private CommentList commentList;

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
//     private void initData(){
//        String  url = Cantents.COMMENT_MESSAGE_CENTER;
//        OkHttpUtils
//                .get()
//                .url(url)
//                .build()
//                .execute(new StringCallback() {
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(getContext(),"网络请求错误",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        processData(response);
//                    }
//
//                });
//
//
//
//
//       /* mDatas = new ArrayList<>();
//        for(int i = 0; i<50;i++){
//            mDatas.add("用户名"+i);
//        }
//        mAdapter = new AtMeFragmentRVAdapter(getContext(),mDatas);
//        rv.setAdapter(mAdapter);
//
//        mAdapter.notifyDataSetChanged();*/
//    }

    private void initData() {
        String url = Cantents.COMMENT_MESSAGE_CENTER;
        Map<String, String> parmas = new HashMap<>();

        parmas.put("uid", "3280644");

        parmas.put("pageIndex", String.valueOf(pageIndex));
        parmas.put("pageSize", pageSize);
        parmas.put("cataLog", cataLog);

        Map<String, String> headers = new HashMap<>();
        String ysrCookie = "oscid=mgpStihVMPm1xnSONyn7kHbeMiZAkvS9fg" +
                "%2F6UY0Z0fnwGExheP1XydgqsJvbmPb0JqFALMWqQDgiewdj13%2BMw3CnoH5wf4SR8vujD6FSX1uKhq" +
                "%2Fxs9SVzq82JnSk6cwpvUMCM%2FfiQRScW4m%2B9R8WLw%3D%3D; Domain=.oschina.net; " +
                "Expires=Fri, 23-Feb-2018 06:47:07 GMT; Path=/";
        // String cookie = SPUtils.getString(context, MyConfig.TLF_COOLKIE, ysrCookie);
        headers.put("ysrCookie", ysrCookie);

        OKHttp3Helper.create().get(url, headers, parmas, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {

                commentList = XmlUtils.toBean(CommentList.class, data.getBytes());
                if (isRefresh) {

                    mAdapter = new AtMeFragmentRVAdapter(getContext(), commentList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }
                if (isLoad) {
                    mAdapter = new AtMeFragmentRVAdapter(getContext(), commentList.getList());
                    xRecyclerView.loadMoreComplete();
                    isLoad = false;
                }


                MessageList messageList = XmlUtils.toBean(MessageList.class, data.getBytes());
                mAdapter = new AtMeFragmentRVAdapter(getContext(), commentList.getList());

                xRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
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


