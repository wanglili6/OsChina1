package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.HisActivity;
import com.bestteam.oschina.activity.LoginActivity;
import com.bestteam.oschina.activity.TweetDetailActivity;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Comment;
import com.bestteam.oschina.bean.TweetsList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.SPUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Cookie;

import static android.content.Context.INPUT_METHOD_SERVICE;


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


        private final CircleImageView ivIconComment;
        private final TextView tvUserNameComment;
        private final TextView tvTimeComment;
        private final TextView tvComment;
        private final ImageView ivComment;
        private final EditText etComment;
        private final RelativeLayout rlHuifu;
        private int authorId;
        private final Button btSend;
        private InputMethodManager imm;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIconComment = (CircleImageView) itemView.findViewById(R.id.iv_icon_detail_comment);
            tvUserNameComment = (TextView) itemView.findViewById(R.id.tv_username_detail_comment);
            tvTimeComment = (TextView) itemView.findViewById(R.id.tv_time_detail_comment);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment_detail);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            etComment = (EditText) itemView.findViewById(R.id.et_huifu);
            rlHuifu = (RelativeLayout) itemView.findViewById(R.id.rl_huifu);
            btSend = (Button) itemView.findViewById(R.id.bt_send);

        }

        public void setData(int position) {
            Comment comment = list.get(position);
            final int id = comment.getId();
            String portrait = comment.getPortrait();
            String author = comment.getAuthor();
            authorId = comment.getAuthorId();
            String pubDate = comment.getPubDate();
            final String content = comment.getContent();
//            List<Comment.Reply> replies = comment.getReplies();
//            List<Comment.Refer> refers = comment.getRefers();
            if (!TextUtils.isEmpty(portrait)) {
                Picasso.with(context).load(portrait).into(ivIconComment);
            }
            tvUserNameComment.setText(author);
            tvTimeComment.setText(pubDate);
            tvComment.setText(content);
            final String huifu = "回复:@"+author+":";
            etComment.setText(huifu);
            etComment.setSelection(huifu.length());

            final String myUid = SPUtils.getString(context,Cantents.MY_UID,"");

            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(myUid)){
                        context.startActivity(new Intent(context, LoginActivity.class));
                        return;
                    }
                    imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                    if(rlHuifu.getVisibility() == View.VISIBLE){
                        rlHuifu.setVisibility(View.GONE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        etComment.clearFocus();
                    }else {
                        rlHuifu.setVisibility(View.VISIBLE);
                        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                        etComment.requestFocus();
                    }
                }
            });

            ivIconComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentIcon = new Intent(context,HisActivity.class);
                    intentIcon.putExtra("authorId",authorId);
                    context.startActivity(intentIcon);
                }
            });



            btSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cookie = SPUtils.getString(context, Cantents.MY_COOKIE, "");
                    String etContent = etComment.getText().toString();
                    OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
                    Map<String,String> header = new HashMap<String, String>();
                    header.put("cookie",cookie);
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id",id+"");
                    params.put("catalog","3");
                    params.put("uid",myUid);
                    params.put("content",etContent);
                    okHttp3Helper.post(Cantents.BASE_TWEET_SEND, header, params, new OKHttp3Helper.HttpCallback() {
                        @Override
                        public void onSuccess(String result) {

                            Toast.makeText(context,"发送成功!",Toast.LENGTH_SHORT).show();
                            etComment.setText(huifu);
                            etComment.setSelection(huifu.length());
                            rlHuifu.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFail(Exception e) {
                            Toast.makeText(context,"发送失败!",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }
}
