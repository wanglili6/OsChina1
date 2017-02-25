package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.AttentionFriendAdapter;
import com.bestteam.oschina.bean.Friend;
import com.bestteam.oschina.fragment.MeFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuanchao on 2017/2/19.
 */

public class AttentionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibBack;
    private TextView tvMe;
    private XRecyclerView rvAttention;
    private List<Friend> friendDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        //加载假数据
        friendDatas = new ArrayList<>();
        for (int i= 0;i <= 20;i ++){
            Friend friend = new Friend();
            friend.setUserid(i);
            friend.setName("zhangsan"+i);
            friend.setFrom("zhangsanfrom北京"+i);
            friend.setPortrait("zhangsanportrait"+i);
            friend.setExpertise("zhangsanexpertise"+i);
            friend.setUserid(1);
            friendDatas.add(friend);
        }

    }

    private void initEvent() {
        ibBack.setOnClickListener(this);
        tvMe.setOnClickListener(this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAttention.setLayoutManager(lm);
        rvAttention.setAdapter(new AttentionFriendAdapter(getApplicationContext(),friendDatas));
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        tvMe = (TextView) findViewById(R.id.tv_me);
        rvAttention = (XRecyclerView) findViewById(R.id.rv_attention);
    }

    @Override
    public void onClick(View view) {
        if (view==ibBack||view==tvMe){
            finish();
        }
    }
}
