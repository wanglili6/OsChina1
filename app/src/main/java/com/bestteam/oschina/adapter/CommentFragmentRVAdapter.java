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
import com.bestteam.oschina.bean.Active;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_000 on 2017/2/25.
 */

public class CommentFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Active> activeList = new ArrayList<>();
    private ViewHolder viewHolder;

    public CommentFragmentRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_message, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolder = (CommentFragmentRVAdapter.ViewHolder) holder;

        viewHolder.loadData(position);
    }

    @Override
    public int getItemCount() {
        return activeList != null ? activeList.size() : 0;
    }

    public void addAll(List<Active> activeList) {
        this.activeList.addAll(activeList);
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
            Active active = activeList.get(position);
            String portrait = active.getPortrait();
            String commentCount = String.valueOf(active.getCommentCount());
            if (!TextUtils.isEmpty(portrait)) {
                Picasso.with(context).load(portrait).into(viewHolder.ivIcon);
            }


            viewHolder.tvUsername.setText(active.getAuthor());
            viewHolder.tvContent.setText(active.getMessage());
            viewHolder.tvTime.setText(active.getPubDate());
           /* viewHolder.tvMyDetail.setText(active.getObjectReply().getObjectBody());
            viewHolder.tvMyId.setText(active.getObjectReply().getObjectName()+" :");*/
            viewHolder.tvCount.setText(commentCount);


            switch (active.getAppClient()) {
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
        this.activeList.clear();
    }
}