package com.bestteam.oschina.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 王丽丽 on 2017/2/21.
 */
public class MyHavaHeardAdapter extends RecyclerView.Adapter {

    private RecyclerView.Adapter mAdapter;
    private View mFooterView;
    private View mHearerView;

    public MyHavaHeardAdapter(View mHearerView, View mFooterView, RecyclerView.Adapter mAdapter) {
        this.mHearerView = mHearerView;
        this.mFooterView = mFooterView;
        this.mAdapter = mAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {//返回头
            return RecyclerView.INVALID_TYPE;
        }

        //正常适配器
        //定义一个角标
        int abjPostion = position - 1;
        //获取adapter的数据
        int adapterCount = mAdapter.getItemCount();
        //判断角标值是否在正常adapter的范围内
        if (abjPostion < adapterCount) {
            return mAdapter.getItemViewType(abjPostion);
        }

        //脚
        return RecyclerView.INVALID_TYPE - 1;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecyclerView.INVALID_TYPE) {//头布局

            return new HeaderViewHolder(mHearerView);


        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {//脚
            return new FooterViewHolder(mFooterView);


        }
        return mAdapter.onCreateViewHolder(parent, viewType);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position==0){
            return;
        }
        //定义一个角标
        int abjPostion = position - 1;
        //获取adapter的数据
        int adapterCount = mAdapter.getItemCount();
        //判断角标值是否在正常adapter的范围内
        if (abjPostion < adapterCount) {
            mAdapter.onBindViewHolder(holder,abjPostion);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+2;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //脚
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
