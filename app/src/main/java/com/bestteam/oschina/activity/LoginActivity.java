package com.bestteam.oschina.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.LoginUserBean;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.util.XmlUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 王丽丽 on 2017/2/18.
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_brows_back)
    ImageView tvBrowsBack;
    @BindView(R.id.iv_login_logo)
    ImageView ivLoginLogo;
    @BindView(R.id.iv_login_username_icon)
    ImageView ivLoginUsernameIcon;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;   //用户名
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
    Button btLoginSubmit;   //登录按键
    @BindView(R.id.bt_login_register)
    Button btLoginRegister; //注册按键
    @BindView(R.id.lay_login_container)
    LinearLayout layLoginContainer;
    private int uid;

    // 请求 url 地址
    private String url = "http://www.oschina.net/action/api/login_validate";
    private SharedPreferences preferences;

    private String username;
    private String pwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //监听
        initListener();
    }

    private void dataLogin() {

        username = etLoginUsername.getText().toString().trim();
        pwd = etLoginPwd.getText().toString().trim();


        SPUtils.saveString(this, "username", "pwd");
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            MyToast.show(LoginActivity.this, "账号或密码不能为空");
            return;
        } else {

//           SPUtils.getString(LoginActivity.this,"username",null);
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("keep_login", "1")
                    .addParams("username", username)
                    .addParams("pwd", pwd)
                    .build()
                    .execute(new StringCallback() {


                        @Override
                        public String parseNetworkResponse(okhttp3.Response response, int id) throws IOException {
                            String cookie = response.header("Set-Cookie", "");
                            Log.d("KYZG", cookie);
                            //把cookie值存入sp中,用时get
                            SPUtils.saveString(getApplicationContext(), Cantents.MY_COOKIE,cookie);
                            //cookie
                            return response.body().string();

                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                            MyToast.show(LoginActivity.this, "用户名或者密码错误...");

                            //进入注册界面
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LoginUserBean user = XmlUtils.toBean(LoginUserBean.class, response.getBytes());
                            SPUtils.saveString(getApplicationContext(),Cantents.MY_UID,user.getUser().getId()+"");
                            /*Log.d("uid",""+user.getUser().getId());*/
                            MyToast.show(LoginActivity.this, "登录成功");
                            //startActivity(new Intent(LoginActivity.this,MainActivity.class));

                            finish();
                        }
                    });
        }
    }

    /**
     * 设置监听器
     */
    private void initListener() {

        btLoginSubmit.setOnClickListener(this);

    }

    /**
     * 单击事件监听
     */

    @OnClick({R.id.tv_brows_back, R.id.tv_login_forget_pwd, R.id.bt_login_submit, R.id.bt_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_brows_back:    // 返回主界面
                startActivity(new Intent(this, MainActivity.class));
                // finish();
                break;
            case R.id.tv_login_forget_pwd:  //忘记密码

                startActivity(new Intent(this, retrieveActivity.class));
                finish();
                break;
            case R.id.bt_login_submit:  //登录界面  --->到弹一弹界面
                // 登录
                dataLogin();


                break;
            case R.id.bt_login_register:    //注册 --->到填写用户名和密码 选择性别

                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }


}
