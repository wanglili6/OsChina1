package com.bestteam.oschina.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.SearchAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.SearchList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.XmlUtils;
import com.bestteam.oschina.view.SearchEditText;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private SearchEditText search;
    private XRecyclerView xRecyclerView;
    private String pageSize = "20";
    private ImageView iv;
    private int pageIndex = 0;
    private SearchAdapter adapter;
    private boolean isRefresh = true;
    private boolean isLoaderMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (SearchEditText) findViewById(R.id.query);
        xRecyclerView = (XRecyclerView) findViewById(R.id.search_rv);
        iv = (ImageView) findViewById(R.id.iv_search);


        initSeachView();
        initRecyclerView();
        requestData();
    }

    private void initSeachView() {
        search.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = search.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    xRecyclerView.setVisibility(View.VISIBLE);
                    if (xRecyclerView.getVisibility() == View.VISIBLE) {
                        xRecyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String content = search.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return false;
                } else {
                    xRecyclerView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }


    private void initRecyclerView() {

        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(SearchActivity.this);
        xRecyclerView.setAdapter(adapter);

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setVisibility(View.GONE);

        RecyclerViewFresh();

    }

    private void RecyclerViewFresh() {
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 0;
                requestData();
            }

            @Override
            public void onLoadMore() {
                isLoaderMore = true;
                pageIndex = pageIndex + 1;
                requestData();
            }
        });
    }

    private void requestData() {
        String url = Cantents.Result_URL;
        //catalog=news&content=java&pageIndex=0&pageSize=20"
        Map<String, String> params = new HashMap<>();
        params.put("catalog", "news");
        params.put("content", "java");
        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("pageSize", pageSize);
        OKHttp3Helper.create().get(url, null, params, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                SearchList searchList = XmlUtils.toBean(SearchList.class, result.getBytes());

                if (isRefresh) {
                    adapter.clear();
                    adapter.addAll(searchList.getList());
                    xRecyclerView.refreshComplete();
                    isRefresh = false;
                }

                if (isLoaderMore) {
                    adapter.addAll(searchList.getList());
                    xRecyclerView.loadMoreComplete();
                    isLoaderMore = false;
                }


            }

            @Override
            public void onFail(Exception e) {
                iv.setVisibility(View.VISIBLE);
            }
        });


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
