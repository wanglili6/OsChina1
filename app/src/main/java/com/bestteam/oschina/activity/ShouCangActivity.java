package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.CollectAdapter;
import com.bestteam.oschina.bean.Favorite;
import com.bestteam.oschina.bean.FavoriteList;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Tywei on 2017/2/20.
 */
public class ShouCangActivity extends Activity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);
        ButterKnife.bind(this);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData("http://www.oschina.net/action/api/favorite_list?uid=3279999&pageIndex=0&pageSize=20&type=1");
    }

    // 网络请求
    private void initData(String url) {
        OkHttpUtils.get().url(url)
                .addHeader("Cookie", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ShouCangActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println(response + "response");
                        FavoriteList favorites = XmlUtils.toBean(FavoriteList.class, response.getBytes());
                        CollectAdapter collectAdapter = new CollectAdapter(ShouCangActivity.this);
                        List<Favorite> lists = favorites.getList();
                        collectAdapter.addAll(lists);
                        //创建adapter
                        xRecyclerView.setAdapter(collectAdapter);
                    }
                });
    }

    @OnClick(R.id.ib_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
