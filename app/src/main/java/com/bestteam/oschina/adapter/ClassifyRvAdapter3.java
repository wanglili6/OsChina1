package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.SoftwareMessgeActivity;
import com.bestteam.oschina.bean.SoftwareDec;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王丽丽 on 2017/2/19.
 */
public class ClassifyRvAdapter3 extends RecyclerView.Adapter {
    private Context context;
    private List<SoftwareDec> softwareDecList;



    public ClassifyRvAdapter3( Context context, List<SoftwareDec> softwareDecList) {
        this.context = context;
        this.softwareDecList = softwareDecList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item3_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final SoftwareDec softwareDec = softwareDecList.get(position);
        viewHolder.tvName.setText(softwareDec.getName());
        viewHolder.tvBody.setText(softwareDec.getDescription());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent initen = new Intent();
                initen.setClass(context, SoftwareMessgeActivity.class);
                initen.putExtra("url",softwareDec.getUrl());
                context.startActivity(initen);
            }
        });

    }


    @Override
    public int getItemCount() {

        return softwareDecList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_body)
        TextView tvBody;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
