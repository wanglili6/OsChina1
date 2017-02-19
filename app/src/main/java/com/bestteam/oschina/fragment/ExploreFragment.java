package com.bestteam.oschina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.FriendActivity;
import com.bestteam.oschina.activity.PlayActivity;
import com.bestteam.oschina.activity.SoftwareActivity;
import com.bestteam.oschina.activity.UpdataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class ExploreFragment extends Fragment {
    @BindView(R.id.btn_friend)
    TextView btnFriend;
    @BindView(R.id.btn_software)
    TextView btnSoftware;
    @BindView(R.id.btn_sweep)
    TextView btnSweep;
    @BindView(R.id.btn_shake)
    TextView btnShake;
    @BindView(R.id.btn_coder)
    TextView btnCoder;
    @BindView(R.id.btn_activity)
    TextView btnActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.btn_friend, R.id.btn_software, R.id.btn_sweep, R.id.btn_shake, R.id.btn_coder, R.id.btn_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_friend:
                startActivity(new Intent(getContext(),FriendActivity.class));
                break;
            case R.id.btn_software:
                startActivity(new Intent(getContext(),SoftwareActivity.class));

                break;
            case R.id.btn_sweep:
            case R.id.btn_shake:
            case R.id.btn_coder:
                Intent intent = new Intent();
                intent.setClass(getContext(),UpdataActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_activity:
                startActivity(new Intent(getContext(),PlayActivity.class));
                break;
        }
    }
}
