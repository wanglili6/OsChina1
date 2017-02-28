package com.bestteam.oschina.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bestteam.oschina.activity.SwitchImageViewDetailActivity;
import com.bestteam.oschina.fragment.newsfragment.InformationFragment;

/**
 * Created by Why on 2017/2/25.
 */

public class SwitchImageViewViewPager extends ViewPager {

    private InformationFragment fragment;

    public void setSwitchImageView(InformationFragment fragment) {
        this.fragment = fragment;
    }

    public SwitchImageViewViewPager(Context context) {
        super(context);

    }

    public SwitchImageViewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int startX;
    private int startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                fragment.stopSwtich();
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                fragment.stopSwtich();
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int disX = startX - moveX;
                int disY = startY - moveY;

                if (Math.abs(disX) > Math.abs(disY) && moveX >startX) {
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP :
                fragment.startSwtich();
                int upX = (int) ev.getX();
                int upY = (int) ev.getY();
                if (startX == upX && startY == upY) {
                    Intent intent = new Intent(getContext(), SwitchImageViewDetailActivity.class);
                    intent.putExtra("href",fragment.switchImageViewBean.getResult().getItems().get(getCurrentItem() - 1).getHref());
                    getContext().startActivity(intent);

                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }


}
