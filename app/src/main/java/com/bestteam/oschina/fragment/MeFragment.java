package com.bestteam.oschina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.AttentionActivity;
import com.bestteam.oschina.activity.DongTanActivity;
import com.bestteam.oschina.activity.FansActivity;
import com.bestteam.oschina.activity.MessageCenterActivity;
import com.bestteam.oschina.activity.MyBlogActivity;
import com.bestteam.oschina.activity.ShouCangActivity;

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
    @BindView(R.id.ly_me_dongtan)
    LinearLayout lyMeDongtan;
    @BindView(R.id.ll_me_shoucang)
    TextView llMeShoucang;
    @BindView(R.id.ly_meshoucang)
    LinearLayout lyMeshoucang;
    @BindView(R.id.ll_me_guanzhu)
    TextView llMeGuanzhu;
    @BindView(R.id.ly_me_guanzhu)
    LinearLayout lyMeGuanzhu;
    @BindView(R.id.ll_me_fenshi)
    TextView llMeFenshi;
    @BindView(R.id.ly_me_fenshi)
    LinearLayout lyMeFenshi;
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

    @OnClick({R.id.me_iv_icon, R.id.me_tv_name, R.id.me_ll_sorces, R.id.ly_me_dongtan, R.id.ly_meshoucang, R.id.ly_me_guanzhu, R.id.ly_me_fenshi, R.id.rl_me_message, R.id.rl_me_boke, R.id.rl_me_question, R.id.rl_me_sports, R.id.rl_me_team})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_iv_icon:
                break;
            case R.id.me_tv_name:
                break;
            case R.id.me_ll_sorces:
                break;
            case R.id.ly_me_dongtan:
                Intent intent1 = new Intent(getActivity(), DongTanActivity.class);
                startActivity(intent1);
                break;
            case R.id.ly_meshoucang:
                Intent intent2 = new Intent(getActivity(), ShouCangActivity.class);
                startActivity(intent2);
                break;
            case R.id.ly_me_guanzhu:
                Intent intent3 = new Intent(getContext(), AttentionActivity.class);
                startActivity(intent3);
                break;
            case R.id.ly_me_fenshi:
                Intent intent4 = new Intent(getContext(), FansActivity.class);
                startActivity(intent4);
                break;
            case R.id.rl_me_message:
                Intent intent5 = new Intent(getContext(), MessageCenterActivity.class);
                startActivity(intent5);
                break;
            case R.id.rl_me_boke:
                Intent intent6 = new Intent(getContext(), MyBlogActivity.class);
                startActivity(intent6);
                break;
            case R.id.rl_me_question:
                break;
            case R.id.rl_me_sports:
                break;
            case R.id.rl_me_team:
                break;
        }
    }
}
