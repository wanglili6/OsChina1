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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Messages> messagesList;
    private ViewHolder viewHolder;

    public AtMeFragmentRVAdapter(Context context){
        this.context = context;
    }
    public AtMeFragmentRVAdapter(Context context, List<Messages> messagesList) {
        this.context = context;
        this.messagesList = messagesList;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_atme,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolder = (ViewHolder) holder;
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

          /*  if(未登录){


                跳转登陆

            }else {

            }
            */

            Picasso.with(context).load(messages.getPortrait()).into(viewHolder.ivIcon);
            viewHolder.tvUsername.setText(messages.getFriendName());
            viewHolder.tvContent.setText(messages.getContent());

           /* public final static int CLIENT_MOBILE = 2;
            public final static int CLIENT_ANDROID = 3;
            public final static int CLIENT_IPHONE = 4;
            public final static int CLIENT_WINDOWS_PHONE = 5;*/

            switch (messages.getAppClient()){
                case 2:
                    viewHolder.tvPhone.setText("moblile");
                case 3:
                    viewHolder.tvPhone.setText("andriod");
                case 4:
                    viewHolder.tvPhone.setText("iphone");
                case 5:
                    viewHolder.tvPhone.setText("windows_phone");
            }
        }
    }

}
