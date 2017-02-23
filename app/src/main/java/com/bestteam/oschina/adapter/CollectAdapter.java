package com.bestteam.oschina.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Favorite;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tywei on 2017/2/20.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Favorite> favoriteList = new ArrayList<>();

    public CollectAdapter(Context context) {

        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.collect_item, parent, false);
        return new CollectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectViewHolder collectViewHolder = (CollectViewHolder) holder;
        collectViewHolder.setData(favoriteList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteList != null && favoriteList.size() != 0 ? favoriteList.size() : 0;
    }

    //给adapter设置数据的方法,(写一个方法进行拿到传递过来的数据)
    public void addAll(List<Favorite> favoritesList) {
        this.favoriteList = favoritesList;
    }

    private class CollectViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton collect_icon;
        private final TextView collect_text;

        public CollectViewHolder(View view) {
            super(view);
            collect_icon = (ImageButton) view.findViewById(R.id.collect_ib_icon);
            collect_text = (TextView) view.findViewById(R.id.collect_tv_text);
        }

        //设置一个方法，进行数据的填充
        public void setData(Favorite favorites) {
            collect_icon.setImageResource(R.drawable.actionbar_back_icon_normal);
            collect_text.setText(favorites.getTitle());
        }
    }
}
