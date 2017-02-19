package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王丽丽 on 2017/2/17.
 */

public class SplashActivity extends Activity implements Animation.AnimationListener {
    @BindView(R.id.rl)
    RelativeLayout rl;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(intent);
//                SplashActivity.this.finish();
//            }
//        },500);

        Animation animation = createAnimation();


        rl.startAnimation(animation);
    animation.setAnimationListener(this);
    }

    //创建的动画
    private Animation createAnimation() {
        AnimationSet set = new AnimationSet(false);


        //渐变
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(5000);

        set.addAnimation(alpha);

        return set;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        mHandler.postDelayed(new MyTask(),2000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private class MyTask implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
        }
    }
}
