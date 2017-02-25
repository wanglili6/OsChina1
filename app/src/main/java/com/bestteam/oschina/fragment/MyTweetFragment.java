package com.bestteam.oschina.fragment;

/**
 * Created by 45011 on 2017/2/19.
 */

public class MyTweetFragment extends DadTweetFragment {

    @Override
    public void setFlag() {
        refresh = PULL_REFRESH_ME;
        loadmore = LOAD_MORE_ME;
    }
}
