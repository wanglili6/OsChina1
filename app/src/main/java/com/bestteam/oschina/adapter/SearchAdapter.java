package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.SearchDetailActivity;
import com.bestteam.oschina.bean.News;
import com.bestteam.oschina.bean.Result;
import com.bestteam.oschina.bean.SearchList;
import com.bestteam.oschina.bean.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Why on 2017/2/25.
 */

public class SearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SearchResult> searchResults = new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        final SearchResult searchResult = searchResults.get(position);
        viewHolder.tvTitle.setText(searchResult.getTitle());
        viewHolder.tvContent.setText(searchResult.getDescription());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SearchDetailActivity.class);
                intent.putExtra("url",searchResult.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    /**
     * 给adapter设置数据的方法
     *
     * @param
     */

    public void addAll(List<SearchResult> searchResults) {
        this.searchResults.addAll(searchResults);
        notifyDataSetChanged();
    }

    /**
     * 清空已有的数据
     */
    public void clear() {
        this.searchResults.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
