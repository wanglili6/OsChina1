package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王丽丽 on 2017/2/25.
 */

public class PlayMeassgeActivity extends Activity {
    @BindView(R.id.webView_message)
    WebView webViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_meassge);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        //让webView支持js
        webViewMessage.getSettings().setJavaScriptEnabled(true);
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

    //创建接口对象
    public interface JsCallJava {
        public void openImage(String src);
    }
}
