package com.bestteam.oschina.fragment;

/**
 * Created by 45011 on 2017/2/21.
 */

public class NewTweetFragment extends DadTweetFragment {
    @Override
    public void setFlag() {
        refresh = PULL_REFRESH_NEW;
        loadmore = LOAD_MORE_NEW;
    }
}
