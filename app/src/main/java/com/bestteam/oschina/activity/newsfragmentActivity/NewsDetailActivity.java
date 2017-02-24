package com.bestteam.oschina.activity.newsfragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.NewsDetail;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Why on 2017/2/20.
 */

public class NewsDetailActivity extends Activity {
    @BindView(R.id.iv_back_newsdetail)
    ImageView ivBack;
    @BindView(R.id.tv_comment_newsdetail)
    TextView tvComment;
    @BindView(R.id.webView_newsdetail)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        initWebView();
        requestData();
    }

    private void initWebView() {

        webView.getSettings().setJavaScriptEnabled(true);


    }

    private void requestData() {
        String id = getIntent().getStringExtra("id");
        String url = Cantents.NEWS_DETAIL_URL + id;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                NewsDetail newsDetail = XmlUtils.toBean(NewsDetail.class, response.getBytes());
                webView.loadUrl(newsDetail.getNews().getUrl());

            }
        });

    }


    @OnClick(R.id.iv_back_newsdetail)
    public void onClick() {
        finish();
    }
}
