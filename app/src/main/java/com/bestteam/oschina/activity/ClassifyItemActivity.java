package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.ClassifyRvAdapter;
import com.bestteam.oschina.bean.SoftwareCatalogList;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/19.
 */
public class ClassifyItemActivity extends Activity {
    @BindView(R.id.fragment_rv)
    RecyclerView fragmentRv;
    @BindView(R.id.layout_list_container)
    LinearLayout layoutListContainer;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_base_recycler_view);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");

        fragmentRv.setLayoutManager(new LinearLayoutManager(this));
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    private SoftwareCatalogList softwareCatalogList;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(ClassifyItemActivity.this, "数据加载失败");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        softwareCatalogList = XmlUtils.toBean(SoftwareCatalogList.class, response.getBytes());
                        ClassifyRvAdapter adapter = new ClassifyRvAdapter(ClassifyItemActivity.this, softwareCatalogList.getSoftwarecataloglist());
                        fragmentRv.setAdapter(adapter);
                    }
                });

    }
}
