package com.bestteam.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bestteam.oschina.activity.SwitchImageViewDetailActivity;
import com.bestteam.oschina.bean.Comment;
import com.bestteam.oschina.bean.SwitchImageViewBean;

import java.util.List;

/**
 * Created by Why on 2017/2/24.
 */

public class SwitchImageViewAdapter extends PagerAdapter {

    private Context context;
    private List<ImageView> imageViews;
    private List<SwitchImageViewBean.ResultBean.ItemsBean> itemsBeen;

    public SwitchImageViewAdapter(Context context, List<ImageView> imageViews, List<SwitchImageViewBean.ResultBean.ItemsBean> itemsBeen) {
        this.context = context;
        this.imageViews = imageViews;
        this.itemsBeen = itemsBeen;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = imageViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
