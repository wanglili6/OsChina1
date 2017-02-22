package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;

import java.util.List;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> mDatas;

    public AtMeFragmentRVAdapter(Context context,List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_atme,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_username.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }
    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_username;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.username_atme);
        }
    }
}
