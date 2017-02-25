package com.bestteam.oschina.base;

/**
 * Created by 王丽丽 on 2017/2/19.
 * 存放常量的的接口
 */

public interface Cantents {
    public String CLISSIFTY_URl = "https://www.oschina.net/action/api/softwarecatalog_list?tag=";

    //动弹用
    public String BASE_URL_TWEET = "http://www.oschina.net/action/api/tweet_list?";
    public String BASE_TWEET_DETAIL = "http://www.oschina.net/action/api/tweet_detail?id=";
    public String BASE_TWEET_COMMENT ="http://www.oschina.net/action/api/comment_list?";
    public String BASE_TWEET_SEND = "http://www.oschina.net/action/api/comment_pub";


    public String CLISSIFTY_Item2_URl = "https://www.oschina.net/action/api/software_list?searchTag=";
    public String CLISSIFTY_CLASSIFY_URl = "https://www.oschina.net/action/api/softwaretag_list?searchTag=";

    //综合
    String NEWS_URL = "http://www.oschina.net/action/api/news_list?";
    String NEWS_DETAIL_URL = "http://www.oschina.net/action/api/news_detail?id=";
    String NEWS_HOT_URL = "http://www.oschina.net/action/api/news_list?";
    String BOLG_URL = "http://www.oschina.net/action/api/blog_list?";
    String BLOG_DETAIL_URL = "http://www.oschina.net/action/api/blog_detail?id=";
    //综合轮播图
    String SWITCH_IMAGEVIEW_URL = "http://www.oschina.net/action/apiv2/banner?catalog=1";

    //消息中心
    public String COMMENT_MESSAGE_CENTER = "http://www.oschina.net/action/api/active_list?uid=993896&pageIndex=0&catalog=3&pageSize=20";

   String  KEY_HAS_READ = "new_id";

    String MY_COOKIE = "cookie";    //cookie值

    String MY_UID = "uid";  //uid值
    String MY_USERNAME = "username";  //用户名
    String MY_PWD = "pwd";  //密码
    String MY_GENDER = "gender";    //获得男女


}
