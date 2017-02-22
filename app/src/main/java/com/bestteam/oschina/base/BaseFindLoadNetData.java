package com.bestteam.oschina.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter3;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.bestteam.oschina.view.RefreshRecyleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class BaseFindLoadNetData extends Fragment {
    public void loadNetData(String url, final RecyclerView recyclerView) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(getContext(), "数据加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SoftwareList softwareList = XmlUtils.toBean(SoftwareList.class, response.getBytes());
                        ClassifyRvAdapter3 adapter = new ClassifyRvAdapter3(getContext(), softwareList.getList());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });


    }
}
