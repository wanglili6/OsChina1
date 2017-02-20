package com.bestteam.oschina.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;

import java.util.List;

/**
 * Created by 45011 on 2017/2/19.
 */

public class NewTweetAdapter extends RecyclerView.Adapter {
    private Context context;

    public NewTweetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newtweet,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        System.out.println("Position"+position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText("最新动弹");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv =  (TextView) itemView.findViewById(R.id.tv_item_newtweet);
        }
    }
}
