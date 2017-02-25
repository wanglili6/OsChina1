package com.bestteam.oschina.adapter.softwareadapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.bean.SoftwareCatalogList;
import com.bestteam.oschina.fragment.findfragment.ClassityItem2;
import com.bestteam.oschina.fragment.findfragment.ClassityItem3;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王丽丽 on 2017/2/19.
 */
public class ClassifyRvAdapter2 extends RecyclerView.Adapter {
    private Context context;
    private List<SoftwareCatalogList.SoftwareType> softwareTypeList;
    private FragmentManager fragmentManager;


    public ClassifyRvAdapter2(FragmentManager fragmentManager, Context context, List<SoftwareCatalogList.SoftwareType> softwareTypeList) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.softwareTypeList = softwareTypeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final SoftwareCatalogList.SoftwareType softwareType = softwareTypeList.get(position);
        viewHolder.tvName.setText(softwareType.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = softwareType.getTag();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                ClassityItem3 classityItem3 = new ClassityItem3();
                classityItem3.setItemTag(tag);
                transaction.add(R.id.cassify_categor_contanier, classityItem3,"item3");
                transaction.addToBackStack("item3");
                transaction.commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return softwareTypeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
