package com.bestteam.oschina.fragment.messageFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Tywei on 2017/2/20.
 */

public class PersonalLetterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("私信");
        tv.setBackgroundColor(Color.BLUE);
        return tv;
    }
}
