package com.bestteam.oschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.CommentDetailFragmentRVAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Active;
import com.bestteam.oschina.bean.ActiveList;
import com.bestteam.oschina.bean.CommentList;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zheng_000 on 2017/2/25.
 */

public class AtMeDetailActivity extends Activity {
    private ImageButton ibBack;
    private ImageView ivUserIcon;
    private TextView tvUserName;
    private TextView tvPhone;
    private TextView tvTime;
    private TextView tvCommentDetail;
    private XRecyclerView rvCommentDetail;
    private CommentDetailFragmentRVAdapter mAdapter;
    private String id;
    private String index;
    private boolean isRefresh = true;
    private boolean isLoad = false;
    private int pageIndex = 0;
    private TextView userName;
    private TextView time;
    private ImageView ivIcon;
    private TextView tvDetail;
    private TextView phone;
    private TextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmedetail);
         index = getIntent().getStringExtra("index");
        id = getIntent().getStringExtra("id");
        /*Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();*/
        initView();
        initData();

    }

    private void initView() {


            //评论
            userName = (TextView)findViewById(R.id.username1_comment_detail);
            time = (TextView)findViewById(R.id.time1_comment_detail);
            ivIcon = (ImageView)findViewById(R.id.icon1_comment_message);
            tvDetail = (TextView)findViewById(R.id.content1_comment_detail);
            phone = (TextView)findViewById(R.id.phone1_comment_detail);
            tv_userName = (TextView) findViewById(R.id.tv_username);



        ibBack = (ImageButton) findViewById(R.id.ib_comment_detail);
        ivUserIcon = (ImageView) findViewById(R.id.icon_comment_detail);
        tvUserName = (TextView) findViewById(R.id.username_comment_detail);
        tvPhone = (TextView) findViewById(R.id.phone_comment_detail);
        tvTime = (TextView) findViewById(R.id.time_comment_detail);
        tvCommentDetail = (TextView) findViewById(R.id.mytweet_comment_detail);

/*
        rvCommentDetail = (XRecyclerView) findViewById(R.id.rv_comment_detail);

        rvCommentDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));*/
        /*mAdapter = new CommentDetailFragmentRVAdapter(getApplicationContext());
        rvCommentDetail.setAdapter(mAdapter);

        rvCommentDetail.setPullRefreshEnabled(true);
        rvCommentDetail.setLoadingMoreEnabled(true);
        rvCommentDetail.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 0;
                requestData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                pageIndex = pageIndex + 1;
                requestData();
            }
        });*/
        /*requestData();*/
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }
    private void requestData(){
        /*http://www.oschina.net/action/api/comment_list?pageIndex=0&catalog=3&pageSize=20&id=6066159*/
        String url = Cantents.COMMENT_DETAIL;
        HashMap<String,String> parmas = new HashMap<>();
        parmas.put("pageIndex","0");
        parmas.put("catalog","3");
        parmas.put("pageSize","20");
        parmas.put("id",id);
        OKHttp3Helper.create().get(url, null, parmas, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                CommentList commentList = XmlUtils.toBean(CommentList.class,result.getBytes());
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(getApplicationContext(),"数据错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initData(){
        String url = Cantents.COMMENT_MESSAGE_CENTER ;
        String uid = SPUtils.getString(getApplicationContext(),Cantents.MY_UID,"");
        String cookie = SPUtils.getString(getApplicationContext(),Cantents.MY_COOKIE,"");

       /* GET:http://www.oschina.net/action/api/active_list?uid=993896&pageIndex=0&catalog=3&pageSize=20*/
        Map<String,String> parmas = new HashMap<>();

        parmas.put("uid",uid);

        parmas.put("pageIndex", String.valueOf(pageIndex));
        parmas.put("catalog","3");
        parmas.put("pageSize","20");


        Map<String,String> headers = new HashMap<>();
        headers.put("Cookie",cookie);
        OKHttp3Helper.create().get(url, headers, parmas, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                ActiveList activiList = XmlUtils.toBean(ActiveList.class,result.getBytes());

               /* Active active = (Active) activiList.getList();*/
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                List<Active> list = activiList.getList();
                Active active = list.get(Integer.parseInt(index));


               /* if (isRefresh) {
                    mAdapter.clear();

                    mAdapter.addAll(active);
                    rvCommentDetail.refreshComplete();
                    isRefresh = false;
                }
                if (isLoad) {
                    mAdapter.addAll(active);
                   rvCommentDetail.loadMoreComplete();
                    isLoad = false;
                }*/



                String portrait = SPUtils.getString(getApplicationContext(),Cantents.MY_IMG,"");
                if (!TextUtils.isEmpty(portrait)) {
                    Picasso.with(getApplicationContext()).load(portrait).into(ivUserIcon);
                }

                tvUserName.setText(active.getObjectReply().getObjectName());
                tvCommentDetail.setText(active.getMessage());
                tvTime.setText(active.getPubDate());
                tvCommentDetail.setText(active.getObjectReply().getObjectBody());
                switch (active.getAppClient()){
                    case 2:
                        tvPhone.setText("Moblile");
                        break;
                    case 3:
                        tvPhone.setText("Andriod");
                        break;
                    case 4:
                        tvPhone.setText("Iphone");
                        break;
                    case 5:
                        tvPhone.setText("Windows_Phone");
                        break;
                }


                //评论
                String portrait1 = active.getPortrait();

                if (!TextUtils.isEmpty(portrait1)) {
                    Picasso.with(getApplicationContext()).load(portrait1).into(ivIcon);
                }
                userName.setText(active.getAuthor());
                tvDetail.setText(active.getMessage());
                time.setText(active.getPubDate());
                switch (active.getAppClient()) {
                    case 2:
                        phone.setText("Moblile");
                        break;
                    case 3:
                        phone.setText("Andriod");
                        break;
                    case 4:
                        phone.setText("Iphone");
                        break;
                    case 5:
                        phone.setText("Windows_Phone");
                        break;
                }
               /* viewHolder.tvMyId.setText(active.getObjectReply().getObjectName()+" :");
                viewHolder.tvCount.setText(commentCount);*/


                /*Toast.makeText(getApplicationContext(),active.getMessage(),Toast.LENGTH_LONG).show()*/;
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(getApplicationContext(),"数据错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
