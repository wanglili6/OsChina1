package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.Tweet;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Tywei on 2017/2/20.
 */

public class DongTanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Tweet> tweetList = new ArrayList<>();

    public DongTanAdapter(Context context) {

        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dongtan_item, parent, false);
        return new DongtanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DongtanViewHolder dongtanViewHolder = (DongtanViewHolder) holder;
        dongtanViewHolder.setData(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList != null && tweetList.size() != 0 ? tweetList.size() : 0;
    }

    //给adapter设置数据的方法,(写一个方法进行拿到传递过来的数据)
    public void addAll(List<Tweet> favoritesList) {

        this.tweetList.addAll(favoritesList);
    }

    public void clear() {
        this.tweetList.clear();
    }

    private class DongtanViewHolder extends RecyclerView.ViewHolder {


        private final ImageButton dongtan_icon;
        private final TextView dongtan_text;
        private final TextView dongtan_suini;
        private final TextView dongtan_ard;

        public DongtanViewHolder(View view) {
            super(view);
            dongtan_icon = (ImageButton) view.findViewById(R.id.dongtan_ib_icon);
            dongtan_text = (TextView) view.findViewById(R.id.dongtan_tv_text);
            dongtan_suini = (TextView) view.findViewById(R.id.dongtan_tv_suini);
            dongtan_ard = (TextView) view.findViewById(R.id.dongtan_ll_ard);
        }

        //设置一个方法，进行数据的填充
        public void setData(Tweet favorites) {
            dongtan_icon.setImageResource(favorites.getLikeCount());
            dongtan_text.setText(favorites.getAuthor());
            dongtan_suini.setText(favorites.getAttach());
            switch (favorites.getAppclient()){
                case Tweet.CLIENT_ANDROID:
                    dongtan_ard.setText("Android");
                    break;
                case Tweet.CLIENT_IPHONE:
                    dongtan_ard.setText("Phone");
                    break;
                case Tweet.CLIENT_MOBILE:
                    dongtan_ard.setText("mobile");
                    break;
                case Tweet.CLIENT_WECHAT:
                    dongtan_ard.setText("wechat");
                    break;
                case Tweet.CLIENT_WINDOWS_PHONE:
                    dongtan_ard.setText("windows_phone");
                    break;
            }
        }
    }
}
