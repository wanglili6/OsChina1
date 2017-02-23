package com.bestteam.oschina.fragment.newsfragment;

import com.bestteam.oschina.base.BaseRecyclerViewFragment;

import com.bestteam.oschina.Persenter.*;

/**
 * Created by Why on 2017/2/21.
 */

public class InformationFragment extends BaseRecyclerViewFragment{

    private NewsPresenter newsPresenter;

    @Override
    public void refreshRequestData() {
        super.refreshRequestData();
        if (newsPresenter == null ) {
            newsPresenter = new NewsPresenter(getContext(),this);
        }
        newsPresenter.getNewsList();
    }

    @Override
    public void loaderMoreRequestData() {
        super.loaderMoreRequestData();
        if (newsPresenter == null) {
            newsPresenter = new NewsPresenter(getContext(),this);
        }
        newsPresenter.getLoaderMoreNewsList();
    }
}
