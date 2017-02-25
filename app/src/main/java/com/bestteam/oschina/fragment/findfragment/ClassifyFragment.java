package com.bestteam.oschina.fragment.findfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.softwareadapter.ClassifyRvAdapter;

import java.util.List;

/**
 * Created by 王丽丽 on 2017/2/18.
 */
public class ClassifyFragment extends Fragment {

    private RecyclerView fragmentRv;
    public ClassifyRvAdapter classifyRvAdapter;
    private List<Fragment> lists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler_view, container, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.cassify_categor_contanier, new ClassifyItem1(), "item1");
        transaction.commit();

        return view;
    }



}
