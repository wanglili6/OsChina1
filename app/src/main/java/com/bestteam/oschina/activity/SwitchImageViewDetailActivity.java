package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Why on 2017/2/25.
 */

public class SwitchImageViewDetailActivity extends Activity {
    @BindView(R.id.iv_back_newsdetail)
    ImageView ivBack;
    @BindView(R.id.webView_newsdetail)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        initWebView();

    }

    private void initWebView() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient());

        //允许在webview里面弹出js的窗体
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(getIntent().getStringExtra("href"));

    }

    @OnClick(R.id.iv_back_newsdetail)
    public void onClick() {
        finish();
    }
}
