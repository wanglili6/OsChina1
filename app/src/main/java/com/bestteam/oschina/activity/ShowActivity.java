package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bestteam.oschina.R;
import com.bestteam.oschina.util.MyToast;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by 王丽丽 on 2017/2/23.
 */
public class ShowActivity extends Activity {
    @BindView(R.id.photoView)
    PhotoView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        //显示图片
        Picasso.with(this).load(url).into(photoView);
    }

    public void back(View view) {
        finish();

    }

}
