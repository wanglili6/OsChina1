package com.bestteam.oschina.adapter.newsfragmentadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.newsfragmentActivity.NewsDetailActivity;
import com.bestteam.oschina.bean.News;
import com.bestteam.oschina.bean.NewsList;

import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Why on 2017/2/19.
 */

public class InformationFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<News> newsList;


    public InformationFragmentRVAdapter(Context context,List<News> newsList) {
        this.context = context;

        this.newsList = newsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_information, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        final News news = newsList.get(position);
        viewHolder.tvTitle.setText(news.getTitle());
        viewHolder.tvContent.setText(news.getBody());
        viewHolder.tvName.setText("@" + news.getAuthor());
        viewHolder.tvTime.setText(news.getPubDate());
        viewHolder.tvComment.setText("" + news.getCommentCount());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("url", news.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon_item_information)
        ImageView ivIcon;
        @BindView(R.id.tv_title_item_information)
        TextView tvTitle;
        @BindView(R.id.tv_content_item_information)
        TextView tvContent;
        @BindView(R.id.tv_name_item_information)
        TextView tvName;
        @BindView(R.id.tv_time_item_information)
        TextView tvTime;
        @BindView(R.id.iv_comment_item_information)
        ImageView ivComment;
        @BindView(R.id.tv_num_item_information)
        TextView tvComment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
