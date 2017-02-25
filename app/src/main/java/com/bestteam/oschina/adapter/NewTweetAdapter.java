package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.HisActivity;
import com.bestteam.oschina.activity.ImgBigActivity;
import com.bestteam.oschina.activity.TweetDetailActivity;
import com.bestteam.oschina.bean.Tweet;
import com.bestteam.oschina.bean.TweetsList;
import com.bestteam.oschina.bean.User;
import com.bestteam.oschina.util.SpannableUtil;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 45011 on 2017/2/19.
 */

public class NewTweetAdapter extends RecyclerView.Adapter {

    public List<Tweet> list = new ArrayList<>();
    private Context context;

    public void loadMore(TweetsList bean) {
        list.addAll(bean.getTweetslist());
        notifyDataSetChanged();
    }

    public void refresh(TweetsList bean) {
        list.clear();
        list.addAll(bean.getTweetslist());
        notifyDataSetChanged();
    }

    public NewTweetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newtweet, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("Position" + position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends ViewHolder {
        @BindView(R.id.iv_icon)
        CircleImageView ivIcon;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_pinglun)
        ImageView ivPinglun;
        @BindView(R.id.tv_count_pinglun)
        TextView tvCountPinglun;
        @BindView(R.id.iv_zan)
        ImageView ivZan;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.iv_content)
        ImageView ivContent;
        private Date pub;
        private Tweet tweet;
        private int isLike;
        private String imgBig;
        private int authorid;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            tweet = list.get(position);
            authorid = tweet.getAuthorid();
            String portrait = tweet.getPortrait();
            String imgSmall = tweet.getImgSmall();
            imgBig = tweet.getImgBig();
            //加载头像
            if (!TextUtils.isEmpty(portrait)) {
                Picasso.with(context).load(portrait).into(ivIcon);
            }
            //加载用户名
            tvUsername.setText(tweet.getAuthor());

            //加载文本内容
            tvContent.setText(tweet.getBody());
            Spannable spannable = SpannableUtil.formatterHtmlTag(context, tvContent.getText());
            spannable = SpannableUtil.formatterOnlyTag(context, spannable);
            spannable = SpannableUtil.formatterOnlyLink(context, spannable);
            //spannable = SpannableUtil.formatterEmoji(context.getResources(), spannable, 50);

            //超链接行为
            tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            tvContent.setText(spannable);

            //加载内容缩略图
            if (!TextUtils.isEmpty(imgSmall)) {
                Picasso.with(context).load(imgSmall).into(ivContent);
                ivContent.setVisibility(View.VISIBLE);
            } else {
                ivContent.setVisibility(View.GONE);
            }
            tvCountPinglun.setText(tweet.getCommentCount());

            //加载发表时间
            String pubDate = tweet.getPubDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                pub = format.parse(pubDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long currentTime = new Date().getTime() + 8*60*60*1000;


            if (pub != null) {
                long pubTime = pub.getTime();
                int month =  ((int)(currentTime - pubTime)) / 1000 / 60 / 60 / 24 / 30;
                int day = ((int)(currentTime - pubTime)) / 1000 / 60 / 60 / 24 ;
                int hour = ((int)(currentTime - pubTime)) / 1000 / 60 / 60  ;
                int minute = ((int)(currentTime - pubTime)) / 1000 / 60 ;

                if (month > 1) {
                    tvTime.setText(pubDate);
                } else {
                    if (month == 1) {
                        tvTime.setText("一个月前");
                    } else if (month < 1 && day >= 1) {
                        tvTime.setText(day + "天前");
                    } else if (day < 1 && hour >= 1) {
                        tvTime.setText(hour + "小时前");
                    }else if(hour<1 && minute>=1){
                        tvTime.setText(minute + "分钟前");
                    } else if (minute < 1) {
                        tvTime.setText("刚刚");
                    }
                }
            }
            //是否点赞
            isLike = tweet.getIsLike();
            if (isLike != 0) {
                ivZan.setBackgroundResource(R.drawable.ic_likeed);
            }

            //加载点赞数据
            int likeCount = tweet.getLikeCount();
            List<User> likeUser = tweet.getLikeUser();
            //SpannableStringBuilder ssb = new SpannableStringBuilder();
            //ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.RED);
            if (likeCount > 0) {
                StringBuffer sb = new StringBuffer();

                if (likeUser.size() < 10) {
                    int start = 0;
                    for (int i = 0; i < likeUser.size(); i++) {
                        String name = likeUser.get(i).getName();
                        String s = Html.fromHtml("<B>" + name + "</b>").toString();
                        sb.append(s);
                        if (i != likeUser.size() - 1) {
                            sb.append("、");
                        }
                    }

                }else {
                    for (int i = 0; i < 10; i++) {
                        String name = likeUser.get(i).getName();
                        String s = Html.fromHtml("<B>" + name + "</b>").toString();
                        sb.append(s);
                        if (i != 3) {
                            sb.append("、");
                        }
                    }
                    sb.append("等 ");
                    String ss = Html.fromHtml("<B>" + likeCount + "</b>").toString();
                    sb.append(ss+"人 ");
                }
                sb.append("赞了该动弹");
                String textLike = sb.toString();
                tvZan.setText(textLike);
                tvZan.setVisibility(View.VISIBLE);
            }

            final int id = tweet.getId();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TweetDetailActivity.class);
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }
            });
        }

        @OnClick({R.id.iv_content, R.id.iv_zan,R.id.iv_icon})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_content:
                    Intent intent = new Intent(context,ImgBigActivity.class);
                    intent.putExtra("imgBig",imgBig);
                    context.startActivity(intent);
                    break;
                case R.id.iv_zan:
                    if(isLike == 0){
                        isLike = 1;
                        ivZan.setBackgroundResource(R.drawable.ic_likeed);
                    }else if (isLike == 1){
                        isLike = 0;
                        ivZan.setBackgroundResource(R.drawable.ic_unlike);
                    }
                    break;
                case R.id.iv_icon:
                    Intent intentIcon = new Intent(context, HisActivity.class);
                    intentIcon.putExtra("authorId",authorid);
                    context.startActivity(intentIcon);
                    break;
            }
        }
    }
}
