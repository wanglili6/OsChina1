package com.bestteam.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.SoftwareCatalogList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王丽丽 on 2017/2/19.
 */
public class ClassifyRvAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SoftwareCatalogList.SoftwareType> softwareTypeList;

    public ClassifyRvAdapter(Context context,  List<SoftwareCatalogList.SoftwareType> softwareTypeList) {
        this.context = context;
        this.softwareTypeList = softwareTypeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SoftwareCatalogList.SoftwareType SoftwareType = softwareTypeList.get(position);
        viewHolder.tvName.setText(SoftwareType.getName());

    }

    @Override
    public int getItemCount() {
        return softwareTypeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ib_next)
        ImageButton ibNext;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
