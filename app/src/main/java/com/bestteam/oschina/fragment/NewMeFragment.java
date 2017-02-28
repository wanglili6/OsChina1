package com.bestteam.oschina.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
import com.bestteam.oschina.activity.LoginActivity;
import com.bestteam.oschina.activity.MainActivity;
import com.bestteam.oschina.activity.MessageCenterActivity;
import com.bestteam.oschina.activity.MyBlogActivity;
import com.bestteam.oschina.activity.SettingActivity;
import com.bestteam.oschina.activity.ShouCangActivity;
import com.bestteam.oschina.activity.ShowMeActivity;
import com.bestteam.oschina.activity.UpdataActivity;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.User;
import com.bestteam.oschina.util.MyToast;
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.view.GalaxyView;
import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

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
    //这个是动弹那一行
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
    private String uid;
    private String gender;
    private String name;
    private String img;


    @Override
    public void onResume() {
        super.onResume();
        uid = SPUtils.getString(getContext(), Cantents.MY_UID, "");
        gender = SPUtils.getString(getContext(), Cantents.MY_GENDER, "");
        name = SPUtils.getString(getContext(), Cantents.MY_NMAE, "");
        img = SPUtils.getString(getContext(), Cantents.MY_IMG, "");
        //判断是否登录
        showUid();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);

        ButterKnife.bind(this, view);
        //初始化背景图---自定义的宇宙背景
        ViewTreeObserver viewTreeObserver = faceBig.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initGalaxy();
            }
        });



        return view;
    }

    private void showUid() {
        if (TextUtils.isEmpty(uid)){
            faceBig.setImageResource(R.drawable.widget_default_face);
            textContant.setVisibility(View.GONE);
            imgGender.setVisibility(View.GONE);
            tvName.setText("点击头像登录");
           jifen.setVisibility(View.GONE);

        }else {
            imgGender.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(img).into(faceBig);
            textContant.setVisibility(View.VISIBLE);
            tvName.setText(name);
            jifen.setVisibility(View.VISIBLE);
        }

        if ("2".equals(gender)){
            imgGender.setImageResource(R.drawable.userinfo_icon_female);
        }else if ("1".equals(gender)){
            imgGender.setImageResource(R.drawable.userinfo_icon_male);
        }
    }


    /**
     * 初始化Galaxy的方法
     */
    private void initGalaxy() {
        //获取窗体对象
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
            R.id.ll_attention, R.id.ll_fans,R.id.face_big})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.main_setting://设置页面
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.main_code://二维码页面
                setCode();

                break;
            case R.id.rl_login://整个登录页面
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), ShowMeActivity.class));
                }
                break;
            case R.id.me_message://我的消息
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), MessageCenterActivity.class));
                }
                break;
            case R.id.me_blog://我的博客
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), MyBlogActivity.class));
                }
                break;
            case R.id.me_answers://我的问答
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), UpdataActivity.class));
                }
                break;
            case R.id.me_activity://我的活动
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), UpdataActivity.class));
                }
                break;
            case R.id.me_time://我的团队
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getContext(), UpdataActivity.class));
                }
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
            case R.id.face_big://用户头像
                if (TextUtils.isEmpty(uid)){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else {
                    //TODO:弹出对话框
                    showListDialog();
                }
                break;


        }
    }
    private void showListDialog() {
        final String[] items = { "更换头像","查看大图像"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(getContext());
        listDialog.setTitle("选择操作");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyToast.show(getContext(),"点击了"+which);
            }
        });
        listDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        listDialog.show();
    }
        public void setCode(){
            AlertDialog.Builder customizeDialog =
                    new AlertDialog.Builder(getContext());
            final View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.code_diaglo,null);
            customizeDialog.setView(dialogView);
            customizeDialog.show();
        }
}
