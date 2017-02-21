package com.bestteam.oschina.base;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.bestteam.oschina.adapter.ClassifyRvAdapter3;
import com.bestteam.oschina.bean.SoftwareList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/20.
 */

public class BaseFindLoadNetData extends Fragment{

    public void loadNetData(String url, final RecyclerView recyclerView){

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
                        MyToast.show(getContext(), "数据加载1111");
                        SoftwareList softwareList = XmlUtils.toBean(SoftwareList.class, response.getBytes());
                        ClassifyRvAdapter3 adapter = new ClassifyRvAdapter3(getFragmentManager(),getContext(),softwareList.getList());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });


    }
}
