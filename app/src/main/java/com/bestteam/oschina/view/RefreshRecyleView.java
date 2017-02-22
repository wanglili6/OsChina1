package com.bestteam.oschina.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.MyHavaHeardAdapter;

/**
 * Created by 王丽丽 on 2017/2/21.
 */

public class RefreshRecyleView extends RecyclerView {

    private View mFooterView;
    private View mHeaderView;

    public RefreshRecyleView(Context context) {
        this(context,null);
    }

    public RefreshRecyleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshRecyleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        initFooterView();
        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.item_heard, null);
    }

    private void initFooterView() {
        ViewGroup parent = new ViewGroup(getContext()) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }
        };
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.rv_footer, parent,false);
    }

    //设置adapter
    @Override
    public void setAdapter(Adapter adapter) {
        adapter = new MyHavaHeardAdapter(mHeaderView,mFooterView,adapter);
        super.setAdapter(adapter);
    }
}
