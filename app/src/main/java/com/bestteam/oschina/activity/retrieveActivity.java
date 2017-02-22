package com.bestteam.oschina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 找回密码
 * Created by lx on 2017/2/20.
 */

public class retrieveActivity extends AppCompatActivity {

    @BindView(R.id.iv_retrieve_back)
    ImageView ivRetrieveBack;
    @BindView(R.id.iv_login_username_icon)
    ImageView ivLoginUsernameIcon;
    @BindView(R.id.et_retrievename)
    EditText etRetrievename;
    @BindView(R.id.iv_login_username_del)
    ImageView ivLoginUsernameDel;
    @BindView(R.id.ll_login_retrievename)
    LinearLayout llLoginRetrievename;
    @BindView(R.id.iv_login_pwd_icon)
    ImageView ivLoginPwdIcon;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView ivLoginPwdDel;
    @BindView(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;
    @BindView(R.id.tv_retrieve_pwd)
    Button tvRetrievePwd;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retrieve);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_retrieve_back, R.id.tv_retrieve_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_retrieve_back:

                  //小箭头   返回登录界面
                startActivity(new Intent(this,LoginActivity.class));
                finish();

                break;
            case R.id.tv_retrieve_pwd:
               //下一步 进入重置密码界面
                startActivity(new Intent(this,ResetActivity.class));
                finish();
                break;
        }
    }
}
