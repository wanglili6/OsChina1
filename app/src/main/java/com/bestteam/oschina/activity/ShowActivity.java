package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.bestteam.oschina.R;
import com.bestteam.oschina.util.MyToast;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by 王丽丽 on 2017/2/23.
 */
public class ShowActivity extends Activity {
    @BindView(R.id.photoView)
    PhotoView photoView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        MyToast.show(this,url);
        //显示图片
        Picasso.with(this).load(url).into(photoView);

    }
}
