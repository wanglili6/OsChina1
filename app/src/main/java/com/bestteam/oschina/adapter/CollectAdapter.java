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
        this.favoriteList.addAll(favoritesList);
    }

    public void clear() {
        this.favoriteList.clear();
    }

    private class CollectViewHolder extends RecyclerView.ViewHolder {

        private final TextView collect_tv_text;
        private final TextView collect_tv_wenzi;

        public CollectViewHolder(View view) {
            super(view);
            collect_tv_text = (TextView) view.findViewById(R.id.collect_tv_text);
            collect_tv_wenzi = (TextView) view.findViewById(R.id.collect_tv_wenzi);
        }

        //设置一个方法，进行数据的填充
        public void setData(Favorite favorites) {
            switch (favorites.getType()) {
                case Favorite.CATALOG_ALL:
                    collect_tv_text.setText("所有");
                    break;
                case Favorite.CATALOG_BLOGS:
                    collect_tv_text.setText("博客");
                    break;
                case Favorite.CATALOG_CODE:
                    collect_tv_text.setText("焦点");
                    break;
                case Favorite.CATALOG_NEWS:
                    collect_tv_text.setText("资讯");
                    break;
                case Favorite.CATALOG_SOFTWARE:
                    collect_tv_text.setText("软件");
                    break;
                case Favorite.CATALOG_TOPIC:
                    collect_tv_text.setText("标题");
                    break;
            }
            collect_tv_wenzi.setText(favorites.getTitle());
        }
    }
}
