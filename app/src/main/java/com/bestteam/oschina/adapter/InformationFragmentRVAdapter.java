package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by Why on 2017/2/19.
 */

public class InformationFragmentRVAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> mDatas;

    public InformationFragmentRVAdapter(Context context,List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_information,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_content.setText(mDatas.get(position));


    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;
        private final TextView tv_name;
        private final TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content_item_information);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name_item_information);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time_item_information);

        }
    }
}
