package com.bestteam.oschina.bean;

import java.util.List;

/**
 * Created by Why on 2017/2/24.
 */

public class SwitchImageViewBean {

    /**
     * code : 1
     * message : success
     * result : {"items":[{"detail":"","href":"https://www.oschina.net/question/2720166_2231678","id":2231678,"img":"https://static.oschina.net/uploads/cooperation/75410/google-beta-natural-language-api_8e32c9ab-2e05-41d9-aba3-f472c60575bd.jpg","name":"高手问答 | TensorFlow 实战","pubDate":"2017-02-20 10:54:05","type":2},{"detail":"","href":"https://my.oschina.net/gitosc/blog/841668","id":841668,"img":"https://static.oschina.net/uploads/cooperation/75323/ubuntu-forum-black-sql_f56538b5-299c-4b53-b3c7-aba7fc72e6c7.jpg","name":"码云周刊 | 面试前高效率地学点干货","pubDate":"2017-02-20 11:04:53","type":3},{"detail":"","href":"https://www.oschina.net/news/82051/do-not-transfer-into-management","id":82051,"img":"https://static.oschina.net/uploads/cooperation/78083/chrome55-save-at-least-35-percent-memory_5852c5d5-901a-4ae9-b407-84cc097cfa3d.jpg","name":"十年程序员老鸟给新手的几条忠告","pubDate":"2017-02-20 11:08:58","type":6},{"detail":"","href":"https://my.oschina.net/liuzhijun/blog/841366","id":841366,"img":"https://static.oschina.net/uploads/cooperation/77929/top-income-programming-languages-2016_0eaa28f2-be6c-4fba-9312-1213601c89a2.jpg","name":"用 Python 打造一颗\u201c心\u201d","pubDate":"2017-02-20 11:14:51","type":3},{"detail":"","href":"http://www.oschina.net/news/81873/2017-february-yuanchuanghui","id":81873,"img":"https://static.oschina.net/uploads/cooperation/78455/intellij-idea-2016-3-public-preview_d90db147-55b1-4f9b-b915-e6fb0dee07e2.jpg","name":"厦门、福州源创会开始报名啦！","pubDate":"2017-02-13 11:14:12","type":6}],"nextPageToken":"61AF0C190D6BD629","prevPageToken":"3EA621243546C8A5","requestCount":5,"responseCount":5,"totalResults":5}
     * time : 2017-02-24 15:09:58
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
         * items : [{"detail":"","href":"https://www.oschina.net/question/2720166_2231678","id":2231678,"img":"https://static.oschina.net/uploads/cooperation/75410/google-beta-natural-language-api_8e32c9ab-2e05-41d9-aba3-f472c60575bd.jpg","name":"高手问答 | TensorFlow 实战","pubDate":"2017-02-20 10:54:05","type":2},{"detail":"","href":"https://my.oschina.net/gitosc/blog/841668","id":841668,"img":"https://static.oschina.net/uploads/cooperation/75323/ubuntu-forum-black-sql_f56538b5-299c-4b53-b3c7-aba7fc72e6c7.jpg","name":"码云周刊 | 面试前高效率地学点干货","pubDate":"2017-02-20 11:04:53","type":3},{"detail":"","href":"https://www.oschina.net/news/82051/do-not-transfer-into-management","id":82051,"img":"https://static.oschina.net/uploads/cooperation/78083/chrome55-save-at-least-35-percent-memory_5852c5d5-901a-4ae9-b407-84cc097cfa3d.jpg","name":"十年程序员老鸟给新手的几条忠告","pubDate":"2017-02-20 11:08:58","type":6},{"detail":"","href":"https://my.oschina.net/liuzhijun/blog/841366","id":841366,"img":"https://static.oschina.net/uploads/cooperation/77929/top-income-programming-languages-2016_0eaa28f2-be6c-4fba-9312-1213601c89a2.jpg","name":"用 Python 打造一颗\u201c心\u201d","pubDate":"2017-02-20 11:14:51","type":3},{"detail":"","href":"http://www.oschina.net/news/81873/2017-february-yuanchuanghui","id":81873,"img":"https://static.oschina.net/uploads/cooperation/78455/intellij-idea-2016-3-public-preview_d90db147-55b1-4f9b-b915-e6fb0dee07e2.jpg","name":"厦门、福州源创会开始报名啦！","pubDate":"2017-02-13 11:14:12","type":6}]
         * nextPageToken : 61AF0C190D6BD629
         * prevPageToken : 3EA621243546C8A5
         * requestCount : 5
         * responseCount : 5
         * totalResults : 5
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
             * detail :
             * href : https://www.oschina.net/question/2720166_2231678
             * id : 2231678
             * img : https://static.oschina.net/uploads/cooperation/75410/google-beta-natural-language-api_8e32c9ab-2e05-41d9-aba3-f472c60575bd.jpg
             * name : 高手问答 | TensorFlow 实战
             * pubDate : 2017-02-20 10:54:05
             * type : 2
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
