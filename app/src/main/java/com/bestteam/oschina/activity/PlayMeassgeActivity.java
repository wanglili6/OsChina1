package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bestteam.oschina.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 王丽丽 on 2017/2/25.
 */

public class PlayMeassgeActivity extends Activity {
    @BindView(R.id.webView_message)
    WebView webViewMessage;
    @BindView(R.id.image_activity)
    ImageView imageActivity;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ib_commant)
    ImageView ibCommant;
    @BindView(R.id.iv_share)
    ImageView ivShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏颜色
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.play_meassge);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        String img = getIntent().getStringExtra("img");
        //让webView支持js
        webViewMessage.getSettings().setJavaScriptEnabled(true);


        //自适应
        webViewMessage.setWebChromeClient(new WebChromeClient());

        //设置图片点击事件
        webViewMessage.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //给网页里面的img标签去添加点击事件
                addPictureClick();
            }
        });
        //加载网页
        webViewMessage.loadUrl(url);
        Picasso.with(this).load(img).into(imageActivity);

        //让给js传递java对象
        webViewMessage.addJavascriptInterface(new SoftwareMessgeActivity.JsCallJava() {
            @JavascriptInterface
            @Override
            public void openImage(String src) {
                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                intent.putExtra("url", src);
                startActivity(intent);
            }
        }, "imagelistner");
    }


    private void addPictureClick() {
        //android调用js代码
        webViewMessage.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "    objs[i].onclick=function()  " + "   " + " {  "
                + "        window.imagelistner.openImage(this.src);  "
                + "    }  " + "}" + "})()");

    }

    @OnClick({R.id.iv_back, R.id.ib_commant, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.ib_commant://收藏

                break;
            case R.id.iv_share://分享

                break;
        }
    }

    //创建接口对象
    public interface JsCallJava {
        public void openImage(String src);
    }
}
