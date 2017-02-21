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
 * Created by 王丽丽 on 2017/2/18.
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_brows_back)
    ImageView tvBrowsBack;
    @BindView(R.id.iv_login_logo)
    ImageView ivLoginLogo;
    @BindView(R.id.iv_login_username_icon)
    ImageView ivLoginUsernameIcon;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView ivLoginUsernameDel;
    @BindView(R.id.ll_login_username)
    LinearLayout llLoginUsername;
    @BindView(R.id.iv_login_pwd_icon)
    ImageView ivLoginPwdIcon;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView ivLoginPwdDel;
    @BindView(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;
    @BindView(R.id.iv_login_hold_pwd)
    ImageView ivLoginHoldPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.bt_login_submit)
    Button btLoginSubmit;
    @BindView(R.id.bt_login_register)
    Button btLoginRegister;
    @BindView(R.id.lay_login_container)
    LinearLayout layLoginContainer;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_brows_back, R.id.tv_login_forget_pwd, R.id.bt_login_submit, R.id.bt_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_brows_back:    // 返回主界面

                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            case R.id.tv_login_forget_pwd:  //忘记密码

                startActivity(new Intent(this,retrieveActivity.class));
                finish();

                break;
            case R.id.bt_login_submit:  //登录界面  --->到弹一弹界面

                startActivity(new Intent(this, FlipActivity.class));
                finish();
                break;
            case R.id.bt_login_register:    //注册 --->到填写用户名和密码 选择性别

                startActivity(new Intent(this,RegisterActivity.class));
                finish();

                break;
        }
    }
}
