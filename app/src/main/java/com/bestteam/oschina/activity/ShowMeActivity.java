package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.util.SPUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 王丽丽 on 2017/2/26.
 */
public class ShowMeActivity extends Activity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.username_img)
    CircleImageView usernameImg;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    private String uid;
    private String name;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showme);
        ButterKnife.bind(this);
        uid = SPUtils.getString(this, Cantents.MY_UID, "");
        name = SPUtils.getString(this, Cantents.MY_NMAE, "");
        img = SPUtils.getString(this, Cantents.MY_IMG, "");

        showData();
    }

    private void showData() {
        if (TextUtils.isEmpty(uid)){
            usernameImg.setImageResource(R.drawable.widget_default_face);
            tvUsername.setText("用户名");
        }else {
            Picasso.with(this).load(img).into(usernameImg);
            tvUsername.setText(name);
        }
    }

    @OnClick({R.id.ib_back, R.id.tv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
            case R.id.tv_me:
                finish();
                break;
        }
    }
}
