package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Comment> commentList = new ArrayList<>();
    private ViewHolder viewHolder;

    public AtMeFragmentRVAdapter(Context context){
        this.context = context;
    }
    public AtMeFragmentRVAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;

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

        viewHolder.loadData(position);

    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }
    public void addAll(List<Comment> commentList) {
        this.commentList.addAll(commentList);
        notifyDataSetChanged();
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
            Comment comment = commentList.get(position);
            Picasso.with(context).load(comment.getPortrait()).into(viewHolder.ivIcon);
            viewHolder.tvUsername.setText(comment.getAuthor());
            viewHolder.tvContent.setText(comment.getContent());

           /* public final static int CLIENT_MOBILE = 2;
            public final static int CLIENT_ANDROID = 3;
            public final static int CLIENT_IPHONE = 4;
            public final static int CLIENT_WINDOWS_PHONE = 5;*/

            switch (comment.getAppClient()){
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
    public void clear() {
        this.commentList.clear();
    }

}
