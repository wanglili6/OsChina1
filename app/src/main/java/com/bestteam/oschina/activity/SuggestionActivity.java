package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bestteam.oschina.R;
import com.bestteam.oschina.util.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tywei on 2017/2/23.
 */
public class SuggestionActivity extends Activity {
    @BindView(R.id.ib_suggestion_icon)
    ImageButton ibSuggestionIcon;
    @BindView(R.id.et_suggestion)
    EditText etSuggestion;
    @BindView(R.id.send_suggestion)
    Button sendSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_item);

        ButterKnife.bind(this);


    }

    public void send(View view) {
        String content = etSuggestion.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            MyToast.show(this,"请输入内容");
        }else{
            MyToast.show(this,"已经提交反馈");
        }
    }

    @OnClick(R.id.ib_suggestion_icon)
    public void onClick() {
        finish();
    }
}
