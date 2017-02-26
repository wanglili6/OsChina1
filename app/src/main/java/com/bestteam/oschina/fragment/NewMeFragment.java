package com.bestteam.oschina.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestteam.oschina.R;
import com.bestteam.oschina.activity.AttentionActivity;
import com.bestteam.oschina.activity.DongTanActivity;
import com.bestteam.oschina.activity.FansActivity;
import com.bestteam.oschina.activity.MessageCenterActivity;
import com.bestteam.oschina.activity.MyBlogActivity;
import com.bestteam.oschina.activity.SettingActivity;
import com.bestteam.oschina.activity.ShouCangActivity;
import com.bestteam.oschina.view.GalaxyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 王丽丽 on 2017/2/25.
 */

public class NewMeFragment extends Fragment {
    @BindView(R.id.face_big)
    CircleImageView faceBig;
    @BindView(R.id.galaxy)
    GalaxyView galaxy;
    @BindView(R.id.main_setting)
    ImageButton mainSetting;
    @BindView(R.id.main_code)
    ImageButton mainCode;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.move_count)
    TextView moveCount;
    @BindView(R.id.move)
    TextView move;
    @BindView(R.id.collect_count)
    TextView collectCount;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.attention_count)
    TextView attentionCount;
    @BindView(R.id.attention)
    TextView attention;
    @BindView(R.id.fans_count)
    TextView fansCount;
    @BindView(R.id.fans)
    TextView fans;
    //这个是动弹那一行
    @BindView(R.id.me_message)
    LinearLayout meMessage;
    @BindView(R.id.me_blog)
    LinearLayout meBlog;
    @BindView(R.id.me_answers)
    LinearLayout meAnswers;
    @BindView(R.id.me_activity)
    LinearLayout meActivity;
    @BindView(R.id.me_time)
    LinearLayout meTime;
    @BindView(R.id.ll_move)
    LinearLayout llMove;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_attention)
    LinearLayout llAttention;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.text_contant)
    LinearLayout textContant;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.jifen)
    TextView jifen;
    @BindView(R.id.img_gender)
    CircleImageView imgGender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);

        ButterKnife.bind(this, view);
        ViewTreeObserver viewTreeObserver = faceBig.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initGalaxy();
            }
        });

        return view;
    }


    /**
     * 初始化Galaxy的方法
     */
    private void initGalaxy() {


        Window window = getActivity().getWindow();
        Rect decorRect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(decorRect);

        int decorHeight = decorRect.height();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int statusBar = display.getHeight() - decorHeight;

        //计算中心点
        int[] location = new int[2];
        faceBig.getLocationInWindow(location);
        Rect drawRect = new Rect();
        faceBig.getDrawingRect(drawRect);
        int x = location[0] + drawRect.width() / 2;
        int y = location[1] + drawRect.height() / 2;
        //设置Galaxy
        galaxy.setDefaultPlanets();
        galaxy.setGalaxyCenter(x, y - statusBar);
        galaxy.startRotate();
    }

    @OnClick({R.id.main_setting, R.id.main_code, R.id.rl_login, R.id.me_message, R.id.me_blog,
            R.id.me_answers, R.id.me_activity, R.id.me_time, R.id.ll_move, R.id.ll_collect,
            R.id.ll_attention, R.id.ll_fans})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_setting://设置页面
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.main_code://二维码页面
                break;
            case R.id.rl_login://整个登录页面
                break;
            case R.id.me_message://我的消息

                Intent intent5 = new Intent(getContext(), MessageCenterActivity.class);
                startActivity(intent5);
                break;
            case R.id.me_blog://我的博客

                Intent intent6 = new Intent(getContext(), MyBlogActivity.class);
                startActivity(intent6);
                break;
            case R.id.me_answers://我的问答
                break;
            case R.id.me_activity://我的活动
                break;
            case R.id.me_time://我的团队
                break;
            case R.id.ll_move://动弹

                Intent intent1 = new Intent(getActivity(), DongTanActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_collect://收藏
                Intent intent2 = new Intent(getActivity(), ShouCangActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_attention://关注
                Intent intent3 = new Intent(getContext(), AttentionActivity.class);
                startActivity(intent3);

                break;
            case R.id.ll_fans://粉丝
                Intent intent4 = new Intent(getContext(), FansActivity.class);
                startActivity(intent4);
                break;

        }
    }


}
