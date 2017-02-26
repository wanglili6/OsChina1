package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.FriendActivity;
import com.bestteam.oschina.bean.Friend;
import com.bestteam.oschina.bean.FriendsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuanchao on 2017/2/20.
 */

public class AttentionFriendAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<Friend> friendDatas = new ArrayList<>();

    public AttentionFriendAdapter(Context context) {
        this.context = context;
    }
    public void loadMore(FriendsList bean) {
        friendDatas.addAll(bean.getFriendlist());
        notifyDataSetChanged();
    }

    public void refresh(FriendsList bean) {
        friendDatas.clear();
        friendDatas.addAll(bean.getFriendlist());
        notifyDataSetChanged();
    }
    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attention_friend_item,parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FriendViewHolder viewHolder = (FriendViewHolder) holder;
        final Friend friend = friendDatas.get(position);
        String portrait = friend.getPortrait();
        if (!TextUtils.isEmpty(portrait)) {
            Picasso.with(context).load(portrait).into(viewHolder.ivFriendItem);
        }
        viewHolder.tvName.setText(friend.getName());
        viewHolder.tvAddress.setText(friend.getFrom());
        viewHolder.tvPosition.setText(friend.getExpertise());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FriendActivity.class);
                intent.putExtra("gender",friend.getPortrait());
                intent.putExtra("name",friend.getName());
                intent.putExtra("expertise",friend.getExpertise());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendDatas == null? 0 : friendDatas.size();
    }
    class FriendViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivFriendItem;
        public TextView tvName;
        public TextView tvAddress;
        public TextView tvPosition;

        public FriendViewHolder(View itemView) {
            super(itemView);
            ivFriendItem = (ImageView) itemView.findViewById(R.id.iv_friend_item);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvPosition = (TextView) itemView.findViewById(R.id.tv_position);
        }
    }
}
