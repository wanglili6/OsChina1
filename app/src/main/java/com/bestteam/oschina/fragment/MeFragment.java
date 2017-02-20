package com.bestteam.oschina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.MessageCenterActivity;
import com.bestteam.oschina.activity.MyBlogActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class MeFragment extends Fragment {
    @BindView(R.id.me_iv_icon)
    ImageView meIvIcon;
    @BindView(R.id.me_tv_name)
    TextView meTvName;
    @BindView(R.id.me_ll_sorces)
    TextView meLlSorces;
    @BindView(R.id.ll_me_dongtan)
    TextView llMeDongtan;
    @BindView(R.id.ll_me_shoucang)
    TextView llMeShoucang;
    @BindView(R.id.ll_me_guanzhu)
    TextView llMeGuanzhu;
    @BindView(R.id.ll_me_fenshi)
    TextView llMeFenshi;
    @BindView(R.id.me_ib_message)
    ImageView meIbMessage;
    @BindView(R.id.rl_me_message)
    RelativeLayout rlMeMessage;
    @BindView(R.id.me_ib_moke)
    ImageView meIbMoke;
    @BindView(R.id.rl_me_boke)
    RelativeLayout rlMeBoke;
    @BindView(R.id.me_ib_question)
    ImageView meIbQuestion;
    @BindView(R.id.rl_me_question)
    RelativeLayout rlMeQuestion;
    @BindView(R.id.me_ib_sport)
    ImageView meIbSport;
    @BindView(R.id.rl_me_sports)
    RelativeLayout rlMeSports;
    @BindView(R.id.me_ib_team)
    ImageView meIbTeam;
    @BindView(R.id.rl_me_team)
    RelativeLayout rlMeTeam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mefragment_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.me_iv_icon, R.id.me_tv_name, R.id.me_ll_sorces, R.id.ll_me_dongtan, R.id.ll_me_shoucang, R.id.ll_me_guanzhu, R.id.ll_me_fenshi, R.id.me_ib_message, R.id.rl_me_message, R.id.me_ib_moke, R.id.rl_me_boke, R.id.me_ib_question, R.id.rl_me_question, R.id.me_ib_sport, R.id.rl_me_sports, R.id.me_ib_team, R.id.rl_me_team})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_iv_icon:
                break;
            case R.id.me_tv_name:
                break;
            case R.id.me_ll_sorces:
                break;
            case R.id.ll_me_dongtan:
                break;
            case R.id.ll_me_shoucang:
                break;
            case R.id.ll_me_guanzhu:
                break;
            case R.id.ll_me_fenshi:
                break;
            case R.id.me_ib_message:
                break;
            case R.id.rl_me_message:
                Intent intent = new Intent(getContext(), MessageCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.me_ib_moke:
                break;
            case R.id.rl_me_boke:
                Intent intent1 = new Intent(getContext(), MyBlogActivity.class);
                startActivity(intent1);
                break;
            case R.id.me_ib_question:
                break;
            case R.id.rl_me_question:
                break;
            case R.id.me_ib_sport:
                break;
            case R.id.rl_me_sports:
                break;
            case R.id.me_ib_team:
                break;
            case R.id.rl_me_team:
                break;
        }
    }
}
