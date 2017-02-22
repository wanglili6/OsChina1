package com.bestteam.oschina.view;

import android.content.Context;



/**
 * Created by JTY on 2017/2/19.
 * ListFragment的网络加载业务逻辑的主持者基类
 * 如果你想使用ListFragment，请调用ListFragment的setPersenter方法，传入一个本类的实例对象
 */

public abstract class BasePresenter {
    Context context;
    RefreshListFragment fragment;

    public BasePresenter(Context context ,RefreshListFragment fragment) {
        this.fragment = fragment;
        this.context = context;
    }

    /**
     * 下拉刷新的业务逻辑
     */
    public abstract void doRefresh();

    /**
     * 上拉加载的业务逻辑
     */
    public abstract void doLoad();
}
