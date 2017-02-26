package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.PlayMeassgeActivity;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Event;
import com.bestteam.oschina.view.RefreshRecyleView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王丽丽 on 2017/2/24.
 */

public class NomalAdapter extends RefreshRecyleView.Adapter {
    private Context context;
    private List<Event> entities = new ArrayList<>();

    public NomalAdapter(Context context) {
        this.context = context;
    }

    public NomalAdapter(Context context, List<Event> entities) {
        this.context = context;
        this.entities = entities;
    }

    public void clear(){
        if (entities!=null){
            entities.clear();
        }
    }
    public void addAll(List<Event> list){

        this.entities.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nomal_rv_irem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Event entity = entities.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        Log.e("entity.getCover()",entity.getCover());
        if (TextUtils.isEmpty(entity.getCover())){
            viewHolder.ivImag.setImageResource(R.drawable.event_cover_default);
        }else {
            Picasso.with(context).load(entity.getCover()).into(viewHolder.ivImag);
        }

        viewHolder.tvTitle.setText(entity.getTitle());
        viewHolder.tvTime.setText(entity.getStartTime());
        if (entity.getStatus()==2){
            viewHolder.tvBaoming.setBackgroundResource(R.drawable.text_bg_shape);
            viewHolder.tvBaoming.setText("正在报名");
            viewHolder.tvBaoming.setTextColor(Color.GREEN);
        }else if (entity.getStatus()==3){
            viewHolder.tvBaoming.setBackgroundResource(R.drawable.text_bg_gray_shape);
            viewHolder.tvBaoming.setText("截止报名");
            viewHolder.tvBaoming.setTextColor(Color.GRAY);
        }
        //设置条目点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("url", Cantents.PLAY_URL+entity.getUrl());
                intent.putExtra("img",entity.getCover());
                intent.setClass(context, PlayMeassgeActivity.class);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    static class ViewHolder extends RefreshRecyleView.ViewHolder {
        @BindView(R.id.iv_imag)
        ImageView ivImag;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_baoming)
        TextView tvBaoming;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_count)
        TextView tvCount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
