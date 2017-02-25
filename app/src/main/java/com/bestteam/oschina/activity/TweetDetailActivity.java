package com.bestteam.oschina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestteam.oschina.R;
import com.bestteam.oschina.adapter.TweetDetailAdapter;
import com.bestteam.oschina.base.Cantents;
import com.bestteam.oschina.bean.Comment;
import com.bestteam.oschina.bean.CommentList;
import com.bestteam.oschina.bean.Tweet;
import com.bestteam.oschina.bean.TweetDetail;
import com.bestteam.oschina.bean.User;
import com.bestteam.oschina.net.okhttp.interceptor.OKHttp3Helper;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 45011 on 2017/2/23.
 */
public class TweetDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private XRecyclerView xRv;
    private LinearLayout headView;
    private int id;
    private ImageView iv_icon;
    private TextView tv_username;
    private TextView tv_time;
    private TextView tv_content;
    private ImageView iv_small;
    private final int PULL_REFRESH = 0;
    private final int LOAD_MORE = 1;
    private int pageIndex = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    TweetDetail tweetDetail = (TweetDetail) msg.obj;
                    Tweet tweet = tweetDetail.getTweet();
                    if (tweet == null) {
                        //Toast.makeText(TweetDetailActivity.this,"tweet=null",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    initHeadDetail(tweet);
                    break;
                case PULL_REFRESH:
                    CommentList commentList1 = (CommentList) msg.obj;
                    if(commentList1 == null){
                        Toast.makeText(TweetDetailActivity.this,"null",Toast.LENGTH_SHORT).show();
                    }
                    List<Comment> comments1 = commentList1.getList();
                    initRecyclerView(comments1);
                    xRv.refreshComplete();
                    break;
                case LOAD_MORE:
                    CommentList commentList2 = (CommentList) msg.obj;
                    if(commentList2 == null){
                        Toast.makeText(TweetDetailActivity.this,"null",Toast.LENGTH_SHORT).show();
                    }
                    List<Comment> comments2 = commentList2.getList();
                    adapter.loadMore(comments2);
                    xRv.loadMoreComplete();
                    break;
            }
        }
    };

    private TextView tv_count_pinglun;
    private TextView tvZan;
    private ImageView iv_zan;
    private int isLike;
    private String imgBig;
    private TweetDetailAdapter adapter;
    private int allCount = 100000;
    private int pageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Log.i("id", "....." + id);
        initView();
        initData(PULL_REFRESH);
    }


    private void initView() {
        xRv = (XRecyclerView) findViewById(R.id.xrv);
        xRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TweetDetailAdapter(this);
        xRv.setAdapter(adapter);

        //将详情页当做头添加到xRv中,初始化headview
        headView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.headview, xRv, false);
        iv_icon = (ImageView) headView.findViewById(R.id.iv_icon_detail);
        tv_username = (TextView) headView.findViewById(R.id.tv_username_detail);
        tv_time = (TextView) headView.findViewById(R.id.tv_time_detail);
        tv_content = (TextView) headView.findViewById(R.id.tv_content_detail);
        iv_small = (ImageView) headView.findViewById(R.id.iv_small_detail);
        tv_count_pinglun = (TextView) headView.findViewById(R.id.tv_count_pinglun_detail);
        tvZan = (TextView) headView.findViewById(R.id.tv_zan_detail);
        iv_zan = (ImageView) headView.findViewById(R.id.iv_zan_detail);

        iv_zan.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        iv_small.setOnClickListener(this);

        xRv.addHeaderView(headView);

        xRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData(PULL_REFRESH);
            }

            @Override
            public void onLoadMore() {
                initData(LOAD_MORE);
            }
        });
    }


    private void initData(final int flag) {
        OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
        Map<String,String> params = new HashMap<>();
        if(flag == PULL_REFRESH){
            params.put("pageIndex","0");
        }else if(flag == LOAD_MORE){
            if((pageIndex+1)*pageSize >= allCount){
                Toast.makeText(TweetDetailActivity.this,"没有更多了",Toast.LENGTH_SHORT).show();
                xRv.loadMoreComplete();
                xRv.setLoadingMoreEnabled(false);
                return;
            }
            //xRv.setLoadingMoreEnabled(true);
            params.put("pageIndex",++pageIndex+"");
        }
        params.put("catalog","3");
        params.put("pageSize",pageSize+"");
        params.put("id",id+"");

        String url_detail = Cantents.BASE_TWEET_DETAIL + id;
        okHttp3Helper.get(url_detail, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                //System.out.println(data);
                final TweetDetail bean = XmlUtils.toBean(TweetDetail.class, data.getBytes());
                Message msg =  new Message();
                msg.what = 10;
                msg.obj = bean;
                handler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {
                //Toast.makeText(TweetDetailActivity.this,"获取网络失败",Toast.LENGTH_SHORT).show();
            }
        });

        okHttp3Helper.get(Cantents.BASE_TWEET_COMMENT, null, params, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                final CommentList bean = XmlUtils.toBean(CommentList.class, result.getBytes());
                allCount = bean.getAllCount();
                Message msg =  new Message();
                msg.what = flag;
                msg.obj = bean;
                handler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    public void initHeadDetail(Tweet tweet){
        isLike = tweet.getIsLike();
        String portrait = tweet.getPortrait();
        String imgSmall = tweet.getImgSmall();
        imgBig = tweet.getImgBig();
        String username = tweet.getAuthor();
        String body = tweet.getBody();
        String time = tweet.getPubDate();
        int likeCount = tweet.getLikeCount();
        List<User> likeUser = tweet.getLikeUser();
        if(isLike != 0 )
        iv_zan.setBackgroundResource(R.drawable.ic_likeed);
        tv_username.setText(username);
        tv_time.setText(time);
        tv_content.setText(body);
        tv_count_pinglun.setText(tweet.getCommentCount());
        if (!TextUtils.isEmpty(portrait)) {
            Picasso.with(TweetDetailActivity.this).load(portrait).into(iv_icon);
        }
        if (!TextUtils.isEmpty(imgSmall)) {
            Picasso.with(TweetDetailActivity.this).load(imgSmall).into(iv_small);
        }

        if (likeCount > 0) {
            StringBuffer sb = new StringBuffer();

            if (likeUser.size() < 10) {
                int start = 0;
                for (int i = 0; i < likeUser.size(); i++) {
                    String name = likeUser.get(i).getName();
                    //String s = Html.fromHtml("<B>" + name + "</b>").toString();
                    sb.append(name);
                    if (i != likeUser.size() - 1) {
                        sb.append("、");
                    }
                }

            }else {
                for (int i = 0; i < 10; i++) {
                    String name = likeUser.get(i).getName();
                    //String s = Html.fromHtml("<B>" + name + "</b>").toString();
                    sb.append(name);
                    if (i != 3) {
                        sb.append("、");
                    }
                }
                sb.append("等 ");
                //String ss = Html.fromHtml("<B>" + likeCount + "</b>").toString();
                sb.append(likeCount+"人 ");
            }
            sb.append("赞了该动弹");
            String textLike = sb.toString();
            tvZan.setText(textLike);
            tvZan.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerView(List<Comment> comments) {
        adapter.refresh(comments);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_small_detail:
                Intent intent = new Intent(this,ImgBigActivity.class);
                intent.putExtra("imgBig",imgBig);
                startActivity(intent);
                break;
            case R.id.iv_zan_detail:
                if(isLike == 0){
                    isLike = 1;
                    iv_zan.setBackgroundResource(R.drawable.ic_likeed);
                }else {
                    isLike = 0;
                    iv_zan.setBackgroundResource(R.drawable.ic_unlike);
                }
                break;
            case R.id.iv_icon_detail:

                break;
        }
    }
}
