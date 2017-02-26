package com.bestteam.oschina.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.bestteam.oschina.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tywei on 2017/2/23.
 */
public class SuggestionActivity extends Activity {
    @BindView(R.id.ib_suggestion_icon)
    ImageButton ibSuggestionIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_item);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.ib_suggestion_icon)
    public void onClick() {
        finish();
    }
}
