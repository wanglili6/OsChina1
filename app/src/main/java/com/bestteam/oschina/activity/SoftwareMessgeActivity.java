package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
        softwareMsgWebView.setWebChromeClient(new WebChromeClient());

        //设置图片点击事件
        softwareMsgWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //给网页里面的img标签去添加点击事件
                addPictureClick();
            }
        });
        //加载网页
        softwareMsgWebView.loadUrl(url);
//        softwareMsgWebView.addJavascriptInterface(new JsCallJava() {
//            @JavascriptInterface
//            @Override
//            public void openImage(String src) {
//                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
//                intent.putExtra("url", src);
//                startActivity(intent);
//            }
//        }, "imagelistner");


    }



    private void addPictureClick() {
        //android调用js代码
        softwareMsgWebView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "    objs[i].onclick=function()  " + "   " + " {  "
                + "        window.imagelistner.openImage(this.src);  "
                + "    }  " + "}" + "})()");

    }

    //创建接口对象
    public interface JsCallJava{
        public void openImage(String src);
    }







    @OnClick(R.id.ib_back)
    public void onClick() {

        finish();

    }
}
