package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bestteam.oschina.activity.PlayMeassgeActivity;
import com.bestteam.oschina.bean.AcrivityMessageBean;

import java.util.List;

/**
 * Created by 王丽丽 on 2017/2/24.
 */

public class ImgVPAdapter extends PagerAdapter {
    private List<ImageView> imageViewList;
    private List<AcrivityMessageBean.ResultBean.ItemsBean> itemsBeenList ;
    private Context context;

    public ImgVPAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    public ImgVPAdapter(List<ImageView> imageViewList, List<AcrivityMessageBean.ResultBean.ItemsBean> itemsBeenList, Context context) {
        this.imageViewList = imageViewList;
        this.itemsBeenList = itemsBeenList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = imageViewList.get(position);
        final AcrivityMessageBean.ResultBean.ItemsBean itemsBean = itemsBeenList.get(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("url",itemsBean.getHref());
                intent.putExtra("img",itemsBean.getImg());
                intent.setClass(context, PlayMeassgeActivity.class);
                context.startActivity(intent);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

}
