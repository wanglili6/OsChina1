package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Friend;

import java.util.List;

/**
 * Created by zhangyuanchao on 2017/2/20.
 */

public class AttentionFriendAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<Friend> friendDatas;

    public AttentionFriendAdapter(Context context, List<Friend> friendDatas) {
        this.context = context;
        this.friendDatas = friendDatas;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attention_friend_item,parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FriendViewHolder viewHolder = (FriendViewHolder) holder;
        Friend friend = friendDatas.get(position);
        viewHolder.ivFriendItem.setImageResource(R.drawable.bear);
        viewHolder.tvName.setText(friend.getName());
        viewHolder.tvNull.setText(friend.getFrom());

    }

    @Override
    public int getItemCount() {
        return friendDatas == null? 0 : friendDatas.size();
    }
    class FriendViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivFriendItem;
        public TextView tvName;
        public TextView tvNull;

        public FriendViewHolder(View itemView) {
            super(itemView);
            ivFriendItem = (ImageView) itemView.findViewById(R.id.iv_friend_item);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvNull = (TextView) itemView.findViewById(R.id.tv_null);
        }
    }
}
