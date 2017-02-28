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

/**
 * Created by zheng_000 on 2017/2/26.
 */

public class CommentDetailFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private Active active = new Active();
    private ViewHolder viewHolder;
    public CommentDetailFragmentRVAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_detail,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolder = (ViewHolder) holder;


        String portrait = active.getPortrait();
        if (!TextUtils.isEmpty(portrait)) {
            Picasso.with(context).load(portrait).into(viewHolder.ivIcon);
        }
        viewHolder.userName.setText(active.getAuthor());
        viewHolder.tvDetail.setText(active.getObjectReply().getObjectBody());
        viewHolder.time.setText(active.getPubDate());
        switch (active.getAppClient()){
            case 2:
                viewHolder.phone.setText("Moblile");
                break;
            case 3:
                viewHolder.phone.setText("Andriod");
                break;
            case 4:
                viewHolder.phone.setText("Iphone");
                break;
            case 5:
                viewHolder.phone.setText("Windows_Phone");
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    public void addAll(Active active) {
        this.active = active;
        notifyDataSetChanged();
    }
    public void clear() {
        this.active = null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView time;
        private ImageView ivIcon;
        private TextView tvDetail;
        private TextView phone;
        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.username_comment_detail);
            time = (TextView) itemView.findViewById(R.id.time_comment_detail);
            ivIcon = (ImageView) itemView.findViewById(R.id.icon_comment_detail);
            tvDetail = (TextView) itemView.findViewById(R.id.mytweet_comment_detail);
            phone = (TextView) itemView.findViewById(R.id.phone_comment_detail);
        }
    }
}
