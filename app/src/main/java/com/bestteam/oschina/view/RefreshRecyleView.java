package com.bestteam.oschina.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.softwareadapter.MyHavaHeardAdapter;

/**
 * Created by 王丽丽 on 2017/2/21.
 */

public class RefreshRecyleView extends RecyclerView {

    private View mFooterView;

    private int mFooterViewMeasuredHeight;
    private int heardLayoutMeasuredHeight;
    private TextView textView;
    private MyHavaHeardAdapter adapter;

    public RefreshRecyleView(Context context) {
        this(context, null);
    }

    public RefreshRecyleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        initFooterView();

    }

    public void setText(String s){
        textView.setText(s);
    }

    private void initFooterView() {


        ViewGroup parent = new ViewGroup(getContext()) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }
        };
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.rv_footer, parent, false);
        textView = (TextView) mFooterView.findViewById(R.id.foot_tv_taxt);

        //设置脚隐藏
//        mFooterView.measure(0, 0);
//        mFooterViewMeasuredHeight = mFooterView.getMeasuredHeight();
//        mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);
    }

    //设置adapter
    @Override
    public void setAdapter(Adapter normalAdapter) {
        adapter = new MyHavaHeardAdapter(mFooterView, normalAdapter);
        super.setAdapter(adapter);
    }


    //隐藏脚的方法
    public void hindFooterView() {
        //不加载更多
        hasLoadMoreData = false;
        mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);

        //刷新数据
        getAdapter().notifyDataSetChanged();


    }

    //设置布局管理器
    private LinearLayoutManager lm;

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        lm = (LinearLayoutManager) layout;
    }

    //是否在加载更多的数据
    private boolean hasLoadMoreData;


    //滑动监听的方法

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //在静止状态下并且是最后也个条目
        boolean isState = state == RecyclerView.SCROLL_STATE_IDLE;
        //最后一个条目
        int lastVisibleItemPosition = lm.findLastVisibleItemPosition();

        boolean isLast = lastVisibleItemPosition == getAdapter().getItemCount() - 1;

        Log.e("error", "lastVisibleItemPosition = " + lastVisibleItemPosition);
        Log.e("error", "adapter.getItemCount() - 1 =  " + (getAdapter().getItemCount() - 1));


        if (isState && isLast && !hasLoadMoreData && onLoadMoreListenter != null) {
            hasLoadMoreData = true;
//            显示脚
            mFooterView.setPadding(0, 0, 0, 0);
            //平滑的滑动到加载脚的地方
            smoothScrollToPosition(lastVisibleItemPosition);

            //加载数据
            onLoadMoreListenter.onLoadMaore();
        }


    }

    //创建一个接口
    public interface OnLoadMoreListenter {
        void onLoadMaore();
    }

    //设置一个方法,进行监听回调
    private OnLoadMoreListenter onLoadMoreListenter;

    public void setOnLoadMoreListenter(OnLoadMoreListenter onLoadMoreListenter) {
        this.onLoadMoreListenter = onLoadMoreListenter;


    }
}
