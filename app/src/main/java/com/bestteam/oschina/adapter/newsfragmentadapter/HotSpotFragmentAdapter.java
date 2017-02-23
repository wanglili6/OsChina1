package com.bestteam.oschina.adapter.NewsFragmentAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.newsfragmentActivity.NewsDetailActivity;
import com.bestteam.oschina.bean.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Why on 2017/2/23.
 */

public class HotSpotFragmentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<News> newsList = new ArrayList<>();

    public HotSpotFragmentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotspot, parent, false);
        ButterKnife.bind(this, view);
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
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("id",news.getId() + " ");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    /**
     * 给adapter设置数据的方法
     *
     * @param newsList
     */

    public void addAll(List<News> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    /**
     * 清空已有的数据
     */
    public void clear() {
        this.newsList.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title_item_hostpot)
        TextView tvTitle;
        @BindView(R.id.tv_content_item_hostpot)
        TextView tvContent;
        @BindView(R.id.tv_name_item_hostpot)
        TextView tvName;
        @BindView(R.id.tv_time_item_hostpot)
        TextView tvTime;
        @BindView(R.id.tv_comment_item_hostpot)
        TextView tvComment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
