//package com.bestteam.oschina.fragment.findfragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.bestteam.oschina.R;
//import com.bestteam.oschina.activity.SoftwareActivity;
//import com.bestteam.oschina.adapter.ClassifyRvAdapter;
//import com.bestteam.oschina.base.Cantents;
//import com.bestteam.oschina.bean.SoftwareCatalogList;
//import com.bestteam.oschina.util.MyToast;
//import com.bestteam.oschina.util.XmlUtils;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.builder.GetBuilder;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import okhttp3.Call;
//
///**
// * Created by 王丽丽 on 2017/2/18.
// */
//public class SubClassifyFragment extends Fragment implements ClassifyRvAdapter.RecyclerviewItemClickLisenter{
//
//    private RecyclerView fragmentRv;
//    public  ClassifyRvAdapter classifyRvAdapter;
//    private String category;
//    private SoftwareCatalogList softwareCatalogList;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle arguments = getArguments();
//        category = arguments.getString("category");//---tag
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.layout_sub_classify_fragment, container, false);
//        initView(view);
//        initRecyleView();
//        loadNetData();
//
//        return view;
//    }
//
//
//    /**
//     * 初始化控件
//     *
//     * @param view
//     */
//    private void initView(View view) {
//        fragmentRv = (RecyclerView) view.findViewById(R.id.fragment_rv);
//    }
//
//    /**
//     * 初始化RecycleVIew
//     */
//    private void initRecyleView() {
//        //设置布局管理器
//        fragmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        //设置适配器
//
//
//    }
//
//    /**
//     * 获取网络数据
//     */
//    private void loadNetData() {
//        OkHttpUtils
//                .get()
//                .url(Cantents.CLISSIFTY_URl+ category)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        MyToast.show(getContext(), "数据加载失败");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        softwareCatalogList = XmlUtils.toBean(SoftwareCatalogList.class, response.getBytes());
//                        classifyRvAdapter = new ClassifyRvAdapter(getContext(),
//                                softwareCatalogList.getSoftwarecataloglist());
//
//                        classifyRvAdapter.setOnItemClickLisenter(SubClassifyFragment.this);
//
//                        fragmentRv.setAdapter(classifyRvAdapter);
//                        classifyRvAdapter.notifyDataSetChanged();
//
//
//                    }
//
//
//                });
//    }
//
//
//    /**
//     * 处理每个条目的点击事件
//     * @param view
//     * @param position
//     */
//    @Override
//    public void onClick(View view, int position) {
//        SoftwareCatalogList.SoftwareType softwareType = softwareCatalogList.getSoftwarecataloglist().get(position);
//        int tag = softwareType.getTag();
//        ((ClassifyFragment)getParentFragment()).switchSubFragment(tag);
//    }
//}
