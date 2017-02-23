package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Messages;

import java.util.List;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Messages> messagesList;

    public AtMeFragmentRVAdapter(Context context, List<Messages> messagesList) {
        this.context = context;
        this.messagesList = messagesList;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_atme,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
       /* Messages messages = messagesList.get(position);
        Picasso.with(context).load(messages.getPortrait()).into(viewHolder.ivIcon);*/
        viewHolder.loadData(position);

    }

    @Override
    public int getItemCount() {
        return messagesList != null ? messagesList.size() : 0;
    }
    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private TextView tvTime;
        private ImageView ivIcon;
        private TextView tvContent;
        private TextView tvPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username_atme);
            tvContent = (TextView) itemView.findViewById(R.id.content_atme);
            ivIcon = (ImageView) itemView.findViewById(R.id.icon_atme);
            tvTime = (TextView) itemView.findViewById(R.id.time_atme);
            tvPhone = (TextView) itemView.findViewById(R.id.phone_atme);
        }
        public void loadData(int position){
            Messages messages = messagesList.get(position);
           /* if()*/
        }
    }

}
