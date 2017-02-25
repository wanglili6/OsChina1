package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tywei on 2017/2/19.
 */
public class SettingActivity extends Activity {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.rl_setting_clear)
    RelativeLayout rlSettingClear;
    @BindView(R.id.rl_setting_exit)
    RelativeLayout rlSettingExit;
    @BindView(R.id.rl_setting_opinion)
    RelativeLayout rlSettingOpinion;
    @BindView(R.id.rl_setting_us)
    RelativeLayout rlSettingUs;
    @BindView(R.id.rl_setting_update)
    RelativeLayout rlSettingUpdate;
    @BindView(R.id.tv_setting_zhuxiao)
    TextView tvSettingZhuxiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_item);
        ButterKnife.bind(this);

        //判断如果有uid就显示没有就隐藏
        String uid =  SPUtils.getString(getApplicationContext(), Cantents.MY_UID,"");
        if (TextUtils.isEmpty(uid)){
           tvSettingZhuxiao.setVisibility(View.GONE);
        } else{
            tvSettingZhuxiao.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_icon, R.id.rl_setting_clear, R.id.rl_setting_exit, R.id.rl_setting_opinion, R.id.rl_setting_us, R.id.rl_setting_update, R.id.tv_setting_zhuxiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                finish();
                break;
            case R.id.rl_setting_clear:
                break;
            case R.id.rl_setting_exit:
                break;
            case R.id.rl_setting_opinion:
                Intent intent = new Intent(this,SuggestionActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_us:
                break;
            case R.id.rl_setting_update:
                break;
            case R.id.tv_setting_zhuxiao:
                TaskStack();
                //点击注册清空数据,隐藏按键
                tvSettingZhuxiao.setVisibility(View.GONE);
                finish();
                break;
        }
    }
    //清空sp中存储的数据
    private void TaskStack() {
        SPUtils.clear(this);

    }
}
