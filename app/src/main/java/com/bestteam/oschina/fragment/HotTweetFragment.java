package com.bestteam.oschina.fragment;

/**
 * Created by 45011 on 2017/2/19.
 */

public class HotTweetFragment extends DadTweetFragment {

    @Override
    public void setFlag() {
        refresh = PULL_REFRESH_HOT;
        loadmore = LOAD_MORE_HOT;
    }
}
