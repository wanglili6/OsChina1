package com.bestteam.oschina.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bestteam.oschina.R;


/**
 * Created by JTY on 2017/2/19.
 * RecyclerView上拉加载的Adapter
 */

public class UpToLoadAdapter extends RecyclerView.Adapter {
    private static final int TYPE_FOOTER = 1;
    private RecyclerView.Adapter adapter;
    private FooterViewHolder footerHolder;

    public UpToLoadAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return adapter.getItemCount();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建脚
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_recycler_foot, parent, false);
            return new FooterViewHolder(view);
        } else {
            //创建正常条目
            return adapter.onCreateViewHolder(parent, viewType);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            footerHolder = (FooterViewHolder) holder;

        } else {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1;
    }

    /**
     * 设置是否是最后一个条目
     * 如果是最后一个条目，脚布局显示：没有更多数据
     *
     * @param isEnd
     */
    public void isEnd(boolean isEnd) {
        if (footerHolder != null) {
            if (isEnd) {
                footerHolder.progressBar.setVisibility(View.GONE);
                footerHolder.text.setText("没有更多数据");
            } else {
                footerHolder.progressBar.setVisibility(View.VISIBLE);
                footerHolder.text.setText("正在加载...");
            }
        }
    }

    /**
     * 脚布局
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView text;

        public FooterViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.base_recycler_foot_progress);
            text = (TextView) view.findViewById(R.id.base_recycler_foot_textView);
        }
    }
}
