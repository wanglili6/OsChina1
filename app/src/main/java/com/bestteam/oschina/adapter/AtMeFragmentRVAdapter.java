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
import com.bestteam.oschina.activity.AtMeDetailActivity;
import com.bestteam.oschina.bean.Active;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zheng_000 on 2017/2/20.
 */

public class AtMeFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Active> activeList = new ArrayList<>();
    private ViewHolder viewHolder;
    private Date pub;

    public AtMeFragmentRVAdapter(Context context){
        this.context = context;
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

        final Active active = activeList.get(position);
        String portrait = active.getPortrait();
        String commentCount = String.valueOf(active.getCommentCount());
        if (!TextUtils.isEmpty(portrait)) {
            Picasso.with(context).load(portrait).into(viewHolder.ivIcon);
        }


        viewHolder.tvUsername.setText(active.getAuthor());
        viewHolder.tvContent.setText(active.getMessage());
        viewHolder.tvTime.setText(active.getPubDate());
        viewHolder.tvMyDetail.setText(active.getObjectReply().getObjectBody());
        viewHolder.tvMyId.setText(active.getObjectReply().getObjectName()+" :");
        viewHolder.tvCount.setText(commentCount);

           /* String pubDate = active.getPubDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                pub = format.parse(pubDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long currentTime = new Date().getTime() + (8 * 60 * 60 * 1000);
            if (pub != null) {
                long pubTime = pub.getTime();
                int month = (int) ((currentTime - pubTime) / 1000 / 60 / 60 / 24 / 30);
                int day = (int) ((currentTime - pubTime) / 1000 / 60 / 60 / 24);
                int minute = (int) ((currentTime - pubTime) / 1000 / 60 / 60);

                if (month > 1) {
                    viewHolder.tvTime.setText(pubDate);
                } else {
                    if (month == 1) {
                        viewHolder.tvTime.setText("一个月前");
                    } else if (month < 1 && day >= 1) {
                        viewHolder.tvTime.setText(day + "小时前");
                    } else if (day < 1 && minute >= 1) {
                        viewHolder.tvTime.setText(minute + "分钟前");
                    } else if (minute < 1) {
                        viewHolder.tvTime.setText("刚刚");
                    }
                }
            }*/


        switch (active.getAppClient()){
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


        /*viewHolder.loadData(position);*/
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AtMeDetailActivity.class);

                intent.putExtra("id",active.getId()+"");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return activeList != null ? activeList.size() : 0;
    }
    public void addAll(List<Active> activeList) {
        this.activeList.addAll(activeList);
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private TextView tvTime;
        private ImageView ivIcon;
        private TextView tvContent;
        private TextView tvPhone;
        private TextView tvMyDetail;
        private TextView tvMyId;
        private TextView tvCount;
        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username_atme);
            tvContent = (TextView) itemView.findViewById(R.id.content_atme);
            ivIcon = (ImageView) itemView.findViewById(R.id.icon_atme);
            tvTime = (TextView) itemView.findViewById(R.id.time_atme);
            tvPhone = (TextView) itemView.findViewById(R.id.phone_atme);
            tvMyDetail = (TextView) itemView.findViewById(R.id.mydetail_atme);
            tvMyId = (TextView) itemView.findViewById(R.id.myid_atme);
            tvCount = (TextView) itemView.findViewById(R.id.count_atme);

        }
        public void loadData(int position){
            Active active = activeList.get(position);
            String portrait = active.getPortrait();
            String commentCount = String.valueOf(active.getCommentCount());
            if (!TextUtils.isEmpty(portrait)) {
                Picasso.with(context).load(portrait).into(viewHolder.ivIcon);
            }


            viewHolder.tvUsername.setText(active.getAuthor());
            viewHolder.tvContent.setText(active.getMessage());
            viewHolder.tvTime.setText(active.getPubDate());
            viewHolder.tvMyDetail.setText(active.getObjectReply().getObjectBody());
            viewHolder.tvMyId.setText(active.getObjectReply().getObjectName()+" :");
            viewHolder.tvCount.setText(commentCount);

           /* String pubDate = active.getPubDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                pub = format.parse(pubDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long currentTime = new Date().getTime() + (8 * 60 * 60 * 1000);
            if (pub != null) {
                long pubTime = pub.getTime();
                int month = (int) ((currentTime - pubTime) / 1000 / 60 / 60 / 24 / 30);
                int day = (int) ((currentTime - pubTime) / 1000 / 60 / 60 / 24);
                int minute = (int) ((currentTime - pubTime) / 1000 / 60 / 60);

                if (month > 1) {
                    viewHolder.tvTime.setText(pubDate);
                } else {
                    if (month == 1) {
                        viewHolder.tvTime.setText("一个月前");
                    } else if (month < 1 && day >= 1) {
                        viewHolder.tvTime.setText(day + "小时前");
                    } else if (day < 1 && minute >= 1) {
                        viewHolder.tvTime.setText(minute + "分钟前");
                    } else if (minute < 1) {
                        viewHolder.tvTime.setText("刚刚");
                    }
                }
            }*/


            switch (active.getAppClient()){
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
