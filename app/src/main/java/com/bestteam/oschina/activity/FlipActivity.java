package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 弹一弹界面
 * Created by lx on 2017/2/20.
 */

public class FlipActivity extends Activity {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.et_flip_content)
    EditText etFlipContent;
    @BindView(R.id.ib_picture)
    ImageButton ibPicture;
    @BindView(R.id.ib_mention)
    ImageButton ibMention;
    @BindView(R.id.ib_trend_software)
    ImageButton ibTrendSoftware;
    @BindView(R.id.ib_emoji_keyboard)
    ImageButton ibEmojiKeyboard;
    @BindView(R.id.emoji_keyboard_fragment)
    FrameLayout emojiKeyboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_send, R.id.ib_picture, R.id.ib_mention, R.id.ib_trend_software, R.id.ib_emoji_keyboard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_send:
                break;
            case R.id.ib_picture:
                startActivity(new Intent(this,PhotoActivity.class));
                finish();
                break;
            case R.id.ib_mention:
                break;
            case R.id.ib_trend_software:
                break;
            case R.id.ib_emoji_keyboard:
                break;
        }
    }

    private void initData() {
        String etContent = etFlipContent.getText().toString().trim();
        if (TextUtils.isEmpty(etContent)){
            tvSend.setTextColor(Color.GRAY);
        } else {
            tvSend.setTextColor(Color.WHITE);
        }
    }
}
