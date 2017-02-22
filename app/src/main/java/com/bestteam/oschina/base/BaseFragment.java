package com.bestteam.oschina.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Why on 2017/2/21.
 */

public abstract class BaseFragment extends Fragment {

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutID(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        requestData();
    }


    /**
     * 添加布局
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 初始化View
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 获取数据
     */
    protected abstract void requestData();


}
