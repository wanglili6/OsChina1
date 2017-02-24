package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.newsfragmentActivity.BlogDetailActivity;
import com.bestteam.oschina.activity.newsfragmentActivity.NewsDetailActivity;
import com.bestteam.oschina.bean.Blog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Why on 2017/2/23.
 */

public class CommendFragmentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Blog> blogList = new ArrayList<>();

    public CommendFragmentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commend, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Blog blog = blogList.get(position);
        viewHolder.tvTitle.setText(blog.getTitle());
        viewHolder.tvContent.setText(blog.getBody());
        viewHolder.tvName.setText("@" + blog.getAuthor());
        viewHolder.tvTime.setText(blog.getPubDate());
        viewHolder.tvComment.setText("" + blog.getCommentCount());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BlogDetailActivity.class);
                intent.putExtra("id",blog.getId() + " ");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList != null ? blogList.size() : 0;
    }



    /**
     * 给adapter设置数据的方法
     * @param
     */

    public void addAll(List<Blog> blogList) {
        this.blogList.addAll(blogList);
        notifyDataSetChanged();
    }

    /**
     * 清空已有的数据
     */
    public void clear() {
        this.blogList.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title_item_commend)
        TextView tvTitle;
        @BindView(R.id.tv_content_item_commend)
        TextView tvContent;
        @BindView(R.id.tv_name_item_commend)
        TextView tvName;
        @BindView(R.id.tv_time_item_commend)
        TextView tvTime;
        @BindView(R.id.tv_comment_item_commend)
        TextView tvComment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
