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

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码
 * Created by lx on 2017/2/20.
 */

public class ResetActivity extends AppCompatActivity {

    @BindView(R.id.iv_retrieve_back)
    ImageView ivRetrieveBack;
    @BindView(R.id.iv_login_pwd_icon)
    ImageView ivLoginPwdIcon;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView ivLoginPwdDel;
    @BindView(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;
    @BindView(R.id.bu_retrieve_pwd)
    Button buRetrievePwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_retrieve_back, R.id.iv_login_pwd_icon, R.id.bu_retrieve_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_retrieve_back:
               //小箭头 返回找回密码界面
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            case R.id.iv_login_pwd_icon:
                break;
            case R.id.bu_retrieve_pwd:
                //确认键   返回登录界面
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
