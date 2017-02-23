package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.TweetDetailActivity;


/**
 * Created by 45011 on 2017/2/23.
 */
public class TweetDetailAdapter extends RecyclerView.Adapter{
    private Context context;

    public TweetDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet_detail, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
       myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tvtvtv);
        }

        public void setData(int position){
            tv.setText("测试啊啊啊啊啊啊");
        }
    }
}
