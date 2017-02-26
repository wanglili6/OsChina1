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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.bestteam.oschina.util.SPUtils;
import com.bestteam.oschina.util.XmlUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 45011 on 2017/2/23.
 */
public class TweetDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private XRecyclerView xRv;
    private LinearLayout headView;
    private int id;
    private CircleImageView iv_icon;
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
                    if (commentList1 == null) {
                        Toast.makeText(TweetDetailActivity.this, "null", Toast.LENGTH_SHORT).show();
                    }
                    List<Comment> comments1 = commentList1.getList();
                    initRecyclerView(comments1);
                    xRv.refreshComplete();
                    break;
                case LOAD_MORE:
                    CommentList commentList2 = (CommentList) msg.obj;
                    if (commentList2 == null) {
                        Toast.makeText(TweetDetailActivity.this, "null", Toast.LENGTH_SHORT).show();
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
    private ImageView ivBack;
    private int uid;
    private LinearLayout ll;
    private EditText etHuifu;
    private Button btHuifu;
    private TextView tvAt;
    private RelativeLayout rl;
    private InputMethodManager imm;
    private String username;
    private String portrait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Log.i("id", "....." + id);
        //控制软键盘的方法
        imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);

        initView();
        initData(PULL_REFRESH);
    }


    private void initView() {
        ll = (LinearLayout) findViewById(R.id.ll_tweet_detail);
        etHuifu = (EditText) findViewById(R.id.et_big_huifu);
        btHuifu = (Button) findViewById(R.id.bt_big_send);
        tvAt = (TextView) findViewById(R.id.tv_at);
        rl = (RelativeLayout) findViewById(R.id.rl_big_huifu);

        xRv = (XRecyclerView) findViewById(R.id.xrv);
        xRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TweetDetailAdapter(this);
        xRv.setAdapter(adapter);

        //将详情页当做头添加到xRv中,初始化headview
        headView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.headview, xRv, false);
        iv_icon = (CircleImageView) headView.findViewById(R.id.iv_icon_detail);
        tv_username = (TextView) headView.findViewById(R.id.tv_username_detail);
        tv_time = (TextView) headView.findViewById(R.id.tv_time_detail);
        tv_content = (TextView) headView.findViewById(R.id.tv_content_detail);
        iv_small = (ImageView) headView.findViewById(R.id.iv_small_detail);
        tv_count_pinglun = (TextView) headView.findViewById(R.id.tv_count_pinglun_detail);
        tvZan = (TextView) headView.findViewById(R.id.tv_zan_detail);
        iv_zan = (ImageView) headView.findViewById(R.id.iv_zan_detail);
        ivBack = (ImageView) headView.findViewById(R.id.iv_back);

        iv_zan.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        iv_small.setOnClickListener(this);
        ivBack.setOnClickListener(this);

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

        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        // etHuifu.setOnClickListener(this);
        btHuifu.setOnClickListener(this);
        tvAt.setOnClickListener(this);
        etHuifu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    btHuifu.setVisibility(View.VISIBLE);
                    tvAt.setVisibility(View.VISIBLE);
                }else {
                    btHuifu.setVisibility(View.GONE);
                    tvAt.setVisibility(View.GONE);
                }
            }
        });
    }


    private void initData(final int flag) {
        OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
        Map<String, String> params = new HashMap<>();
        if (flag == PULL_REFRESH) {
            params.put("pageIndex", "0");
        } else if (flag == LOAD_MORE) {
            if ((pageIndex + 1) * pageSize >= allCount) {
                Toast.makeText(TweetDetailActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                xRv.loadMoreComplete();
                xRv.setLoadingMoreEnabled(false);
                return;
            }
            //xRv.setLoadingMoreEnabled(true);
            params.put("pageIndex", ++pageIndex + "");
        }
        params.put("catalog", "3");
        params.put("pageSize", pageSize + "");
        params.put("id", id + "");

        String url_detail = Cantents.BASE_TWEET_DETAIL + id;
        okHttp3Helper.get(url_detail, new OKHttp3Helper.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                //System.out.println(data);
                final TweetDetail bean = XmlUtils.toBean(TweetDetail.class, data.getBytes());
                Message msg = new Message();
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
                Message msg = new Message();
                msg.what = flag;
                msg.obj = bean;
                handler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    public void initHeadDetail(Tweet tweet) {
        isLike = tweet.getIsLike();
        portrait = tweet.getPortrait();
        String imgSmall = tweet.getImgSmall();
        imgBig = tweet.getImgBig();
        username = tweet.getAuthor();
        uid = tweet.getAuthorid();
        String body = tweet.getBody();
        String time = tweet.getPubDate();
        int likeCount = tweet.getLikeCount();
        List<User> likeUser = tweet.getLikeUser();
        if (isLike != 0)
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

            } else {
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
                sb.append(likeCount + "人 ");
            }
            sb.append("赞了该动弹");
            String textLike = sb.toString();
            if(likeCount>0){
                tvZan.setText(textLike);
                tvZan.setVisibility(View.VISIBLE);
            }else {
                tvZan.setVisibility(View.GONE);
            }

        }
    }

    private void initRecyclerView(List<Comment> comments) {
        adapter.refresh(comments);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_small_detail:
                Intent intent = new Intent(this, ImgBigActivity.class);
                intent.putExtra("imgBig", imgBig);
                startActivity(intent);
                break;
            case R.id.iv_zan_detail:
                if (isLike == 0) {
                    isLike = 1;
                    iv_zan.setBackgroundResource(R.drawable.ic_likeed);
                } else {
                    isLike = 0;
                    iv_zan.setBackgroundResource(R.drawable.ic_unlike);
                }
                break;
            case R.id.iv_icon_detail:
               Intent intentIcon = new Intent(this, HisActivity.class);
                intentIcon.putExtra("authorId", uid);
                intentIcon.putExtra("face", portrait);
                intentIcon.putExtra("name", username);
                startActivity(intentIcon);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_at:
                Intent forResult = new Intent(this,AttentionActivity.class);
                forResult.putExtra("at",true);
                startActivityForResult(forResult,102);
                break;
            case R.id.bt_big_send:
                //控制软键盘缩回

                imm.hideSoftInputFromWindow(etHuifu.getWindowToken(), 0);
                //EditText失去焦点
                etHuifu.clearFocus();

                String huifuContent = etHuifu.getText().toString();
                String myUid = SPUtils.getString(this, Cantents.MY_UID, "");
                if (TextUtils.isEmpty(myUid)) {
                    Toast.makeText(TweetDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (TextUtils.isEmpty(huifuContent)) {
                    Toast.makeText(TweetDetailActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String cookie = SPUtils.getString(this, Cantents.MY_COOKIE, "");
                OKHttp3Helper okHttp3Helper = OKHttp3Helper.create();
                Map<String, String> header = new HashMap<String, String>();
                header.put("cookie", cookie);
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id + "");
                params.put("catalog", "3");
                params.put("uid", myUid);
                params.put("content", huifuContent);
                okHttp3Helper.post(Cantents.BASE_TWEET_SEND, header, params, new OKHttp3Helper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        etHuifu.setText("");
                        Toast.makeText(TweetDetailActivity.this, "发送成功!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(TweetDetailActivity.this, "发送失败!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                etHuifu.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 102){
            if(resultCode == Cantents.RESULT_OK){
                String username = data.getStringExtra("username");
                String s = etHuifu.getText().toString();
                etHuifu.setText(s+" @"+username);
            }
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        btHuifu.setVisibility(View.GONE);
        tvAt.setVisibility(View.GONE);
    }*/
}
