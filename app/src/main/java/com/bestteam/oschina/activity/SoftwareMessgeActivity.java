package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.util.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/20.
 */
public class SoftwareMessgeActivity extends Activity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_softwore)
    TextView tvSoftwore;
    @BindView(R.id.software_msg_webView)
    WebView softwareMsgWebView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softwaremessge);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");

        Log.i("wll----url", "onCreate: "+url);
        //让webView支持js
        softwareMsgWebView.getSettings().setJavaScriptEnabled(true);
        //加载网页
        softwareMsgWebView.loadUrl("https://www.oschina.net/p/spring");

    }


    @OnClick(R.id.ib_back)
    public void onClick() {

        finish();

    }
}