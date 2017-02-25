package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 45011 on 2017/2/23.
 */
public class TweetDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Comment> list = new ArrayList<>();

    public TweetDetailAdapter(Context context) {
        this.context = context;
    }

    public void loadMore(List<Comment> comments) {
        list.addAll(comments);
        notifyDataSetChanged();
    }

    public void refresh(List<Comment> comments) {
        if (comments != null) {
            list.clear();
            list.addAll(comments);
            notifyDataSetChanged();
        }else {
            Log.i("null","null");
        }
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
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        private final ImageView ivIconComment;
        private final TextView tvUserNameComment;
        private final TextView tvTimeComment;
        private final TextView tvComment;
        private final ImageView ivComment;
        private final EditText etComment;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIconComment = (ImageView) itemView.findViewById(R.id.iv_icon_detail_comment);
            tvUserNameComment = (TextView) itemView.findViewById(R.id.tv_username_detail_comment);
            tvTimeComment = (TextView) itemView.findViewById(R.id.tv_time_detail_comment);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment_detail);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            etComment = (EditText) itemView.findViewById(R.id.et_huifu);


        }

        public void setData(int position) {
            Comment comment = list.get(position);
            String portrait = comment.getPortrait();
            String author = comment.getAuthor();
            int authorId = comment.getAuthorId();
            String pubDate = comment.getPubDate();
            String content = comment.getContent();
//            List<Comment.Reply> replies = comment.getReplies();
//            List<Comment.Refer> refers = comment.getRefers();
            if (!TextUtils.isEmpty(portrait)) {
                Picasso.with(context).load(portrait).into(ivIconComment);
            }
            tvUserNameComment.setText(author);
            tvTimeComment.setText(pubDate);
            tvComment.setText(content);
            etComment.setText("回复:@"+author+":");


            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(etComment.getVisibility() == View.VISIBLE){
                        etComment.setVisibility(View.GONE);
                    }else {
                        etComment.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
