package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Messages;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_000 on 2017/2/26.
 */

public class PrivateMessageFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Messages> messagesList = new ArrayList<>();
   private ViewHolder viewHolder;
    public PrivateMessageFragmentRVAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_message, parent, false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolder = (ViewHolder) holder;

        viewHolder.loadData(position);
    }

    @Override
    public int getItemCount() {
        return messagesList != null ? messagesList.size() : 0;
    }

    public void addAll(List<Messages> messagesList) {
        this.messagesList.addAll(messagesList);
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private TextView tvTime;
        private ImageView ivIcon;
        private TextView tvContent;
        private TextView tvPhone;
        /* private TextView tvMyDetail;
         private TextView tvMyId;*/
        private TextView tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username_comment_message);
            tvTime = (TextView) itemView.findViewById(R.id.time_comment_message);
            ivIcon = (ImageView) itemView.findViewById(R.id.icon_comment_message);
            tvContent = (TextView) itemView.findViewById(R.id.content_comment_message);
            tvPhone = (TextView) itemView.findViewById(R.id.phone_comment_message);
            tvCount = (TextView) itemView.findViewById(R.id.count_comment_message);
        }

        public void loadData(int position) {
            Messages messages = messagesList.get(position);
            String portrait = messages.getPortrait();
            String commentCount = String.valueOf(messages.getMessageCount());
            if (!TextUtils.isEmpty(portrait)) {
                Picasso.with(context).load(portrait).into(viewHolder.ivIcon);
            }


            viewHolder.tvUsername.setText(messages.getFriendName());
            viewHolder.tvContent.setText(messages.getContent());
            viewHolder.tvTime.setText(messages.getPubDate());
           /* viewHolder.tvMyDetail.setText(active.getObjectReply().getObjectBody());
            viewHolder.tvMyId.setText(active.getObjectReply().getObjectName()+" :");*/
            viewHolder.tvCount.setText(commentCount);


            switch (messages.getAppClient()) {
                case 2:
                    viewHolder.tvPhone.setText("Moblile");
                    break;
                case 3:
                    viewHolder.tvPhone.setText("Andriod");
                    break;
                case 4:
                    viewHolder.tvPhone.setText("Iphone");
                    break;
                case 5:
                    viewHolder.tvPhone.setText("Windows_Phone");
                    break;

            }
        }


    }
    public void clear() {
        this.messagesList.clear();
    }

}
