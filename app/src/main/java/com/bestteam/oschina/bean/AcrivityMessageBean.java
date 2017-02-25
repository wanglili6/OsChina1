package com.bestteam.oschina.bean;

import java.util.List;

/**
 * Created by 王丽丽 on 2017/2/24.
 */

public class AcrivityMessageBean {

    /**
     * code : 1
     * message : success
     * result : {"items":[{"detail":"春暖花开的三月，OSC源创会将时隔一年回到深圳，与各位OSCer在春天来一场久违的约会，我们一如既往秉承着\u201c自由，开放，分享\u201d的思想，献上干货满满的主题，时下最热的开源项目，就等你来啦！","href":"https://www.oschina.net/event/2231301","id":2231301,"img":"http://static.oschina.net/uploads/cover/2686220_HSJoW_bi.jpg","name":"【深圳】OSC源创会第59期报名开始","pubDate":"2017-02-20 10:58:21","type":5},{"detail":"2017第八届中国数据库技术大会（DTCC2017）以\u201c数据驱动\u2022价值发现\u201d为主题，汇集来自互联网、电子商务、金融、电信、政府、行业协会等20多个领域的120多位技术专家，共同探讨Oracle、MySQL、NoSQL、云端数据库、智能数据平台、区块链、数据可视化、深度学习等领域的前瞻性热点话题与技术。","href":"https://www.oschina.net/event/2214383","id":2214383,"img":"https://static.oschina.net/uploads/cover/2691515_tHNPx_bi.jpg","name":"2017中国数据库技术大会（DTCC）","pubDate":"2017-01-09 10:46:03","type":5},{"detail":"。来自国内外各行业的信息技术主管与业务主管及知名云计算厂商将面对面深度沟通，分享混合云技术创新与应用实践，共同推进中国云计算产业发展进程。","href":"https://www.oschina.net/event/2213077","id":2213077,"img":"https://static.oschina.net/uploads/cover/2691515_BPOxX_bi.jpg","name":"第九届云计算中国峰会暨混合云世界论坛","pubDate":"2016-12-21 16:12:11","type":5}],"nextPageToken":"226B2C51A4EC6281","prevPageToken":"79A4A000255052ED","requestCount":3,"responseCount":3,"totalResults":3}
     * time : 2017-02-24 14:54:37
     */

    private int code;
    private String message;
    private ResultBean result;
    private String time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class ResultBean {
        /**
         * items : [{"detail":"春暖花开的三月，OSC源创会将时隔一年回到深圳，与各位OSCer在春天来一场久违的约会，我们一如既往秉承着\u201c自由，开放，分享\u201d的思想，献上干货满满的主题，时下最热的开源项目，就等你来啦！","href":"https://www.oschina.net/event/2231301","id":2231301,"img":"http://static.oschina.net/uploads/cover/2686220_HSJoW_bi.jpg","name":"【深圳】OSC源创会第59期报名开始","pubDate":"2017-02-20 10:58:21","type":5},{"detail":"2017第八届中国数据库技术大会（DTCC2017）以\u201c数据驱动\u2022价值发现\u201d为主题，汇集来自互联网、电子商务、金融、电信、政府、行业协会等20多个领域的120多位技术专家，共同探讨Oracle、MySQL、NoSQL、云端数据库、智能数据平台、区块链、数据可视化、深度学习等领域的前瞻性热点话题与技术。","href":"https://www.oschina.net/event/2214383","id":2214383,"img":"https://static.oschina.net/uploads/cover/2691515_tHNPx_bi.jpg","name":"2017中国数据库技术大会（DTCC）","pubDate":"2017-01-09 10:46:03","type":5},{"detail":"。来自国内外各行业的信息技术主管与业务主管及知名云计算厂商将面对面深度沟通，分享混合云技术创新与应用实践，共同推进中国云计算产业发展进程。","href":"https://www.oschina.net/event/2213077","id":2213077,"img":"https://static.oschina.net/uploads/cover/2691515_BPOxX_bi.jpg","name":"第九届云计算中国峰会暨混合云世界论坛","pubDate":"2016-12-21 16:12:11","type":5}]
         * nextPageToken : 226B2C51A4EC6281
         * prevPageToken : 79A4A000255052ED
         * requestCount : 3
         * responseCount : 3
         * totalResults : 3
         */

        private String nextPageToken;
        private String prevPageToken;
        private int requestCount;
        private int responseCount;
        private int totalResults;
        private List<ItemsBean> items;

        public String getNextPageToken() {
            return nextPageToken;
        }

        public void setNextPageToken(String nextPageToken) {
            this.nextPageToken = nextPageToken;
        }

        public String getPrevPageToken() {
            return prevPageToken;
        }

        public void setPrevPageToken(String prevPageToken) {
            this.prevPageToken = prevPageToken;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public void setRequestCount(int requestCount) {
            this.requestCount = requestCount;
        }

        public int getResponseCount() {
            return responseCount;
        }

        public void setResponseCount(int responseCount) {
            this.responseCount = responseCount;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * detail : 春暖花开的三月，OSC源创会将时隔一年回到深圳，与各位OSCer在春天来一场久违的约会，我们一如既往秉承着“自由，开放，分享”的思想，献上干货满满的主题，时下最热的开源项目，就等你来啦！
             * href : https://www.oschina.net/event/2231301
             * id : 2231301
             * img : http://static.oschina.net/uploads/cover/2686220_HSJoW_bi.jpg
             * name : 【深圳】OSC源创会第59期报名开始
             * pubDate : 2017-02-20 10:58:21
             * type : 5
             */

            private String detail;
            private String href;
            private int id;
            private String img;
            private String name;
            private String pubDate;
            private int type;

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPubDate() {
                return pubDate;
            }

            public void setPubDate(String pubDate) {
                this.pubDate = pubDate;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
