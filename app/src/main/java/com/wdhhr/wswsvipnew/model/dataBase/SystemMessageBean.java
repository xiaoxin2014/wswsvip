package com.wdhhr.wswsvipnew.model.dataBase;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class SystemMessageBean {

    /**
     * status : 0
     * msg : 操作成功
     * time : 1504102409507
     * data : {"messageList":[{"content":"您的订单:37ey02p822n0LH12Q2659已申请售后！","ctime":{"date":30,"day":3,"hours":11,"minutes":30,"month":7,"seconds":18,"time":1504063818000,"timezoneOffset":-480,"year":117},"ctimeStr":"10小时前","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":18,"isRead":1,"messgeid":"084713e0g1KT01C83s210","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7已申请售后！","ctime":{"date":29,"day":2,"hours":22,"minutes":7,"month":7,"seconds":11,"time":1504015631000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":17,"isRead":1,"messgeid":"5c10242P2817291018N70","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7商家已同意退货！","ctime":{"date":29,"day":2,"hours":22,"minutes":4,"month":7,"seconds":44,"time":1504015484000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":16,"isRead":1,"messgeid":"8Rw27l240422a2p04109z","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7已申请售后！","ctime":{"date":29,"day":2,"hours":19,"minutes":37,"month":7,"seconds":13,"time":1504006633000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":15,"isRead":1,"messgeid":"29297sa0H15v133080137","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7已申请售后！","ctime":{"date":29,"day":2,"hours":17,"minutes":35,"month":7,"seconds":51,"time":1503999351000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":14,"isRead":1,"messgeid":"o081JL0z2175175b93712","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:o17828130pP8312p1105q已发货！","ctime":{"date":29,"day":2,"hours":8,"minutes":50,"month":7,"seconds":50,"time":1503967850000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":8,"isRead":1,"messgeid":"wM4821U14870af21N0245","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:21007f025ChO18D84l5t1商家已同意退货！","ctime":{"date":28,"day":1,"hours":15,"minutes":52,"month":7,"seconds":26,"time":1503906746000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-28","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":9,"isRead":1,"messgeid":"G820p8w715V103422R185","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:21007f025ChO18D84l5t1已发货！","ctime":{"date":28,"day":1,"hours":14,"minutes":26,"month":7,"seconds":7,"time":1503901567000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-28","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":7,"isRead":1,"messgeid":"8118L9a070k2v648514p2","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"}],"count":0}
     */

    private int status;
    private String msg;
    private long time;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * messageList : [{"content":"您的订单:37ey02p822n0LH12Q2659已申请售后！","ctime":{"date":30,"day":3,"hours":11,"minutes":30,"month":7,"seconds":18,"time":1504063818000,"timezoneOffset":-480,"year":117},"ctimeStr":"10小时前","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":18,"isRead":1,"messgeid":"084713e0g1KT01C83s210","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7已申请售后！","ctime":{"date":29,"day":2,"hours":22,"minutes":7,"month":7,"seconds":11,"time":1504015631000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":17,"isRead":1,"messgeid":"5c10242P2817291018N70","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7商家已同意退货！","ctime":{"date":29,"day":2,"hours":22,"minutes":4,"month":7,"seconds":44,"time":1504015484000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":16,"isRead":1,"messgeid":"8Rw27l240422a2p04109z","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7已申请售后！","ctime":{"date":29,"day":2,"hours":19,"minutes":37,"month":7,"seconds":13,"time":1504006633000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":15,"isRead":1,"messgeid":"29297sa0H15v133080137","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:01Uk1316m922800928Tn7已申请售后！","ctime":{"date":29,"day":2,"hours":17,"minutes":35,"month":7,"seconds":51,"time":1503999351000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":14,"isRead":1,"messgeid":"o081JL0z2175175b93712","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:o17828130pP8312p1105q已发货！","ctime":{"date":29,"day":2,"hours":8,"minutes":50,"month":7,"seconds":50,"time":1503967850000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-29","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":8,"isRead":1,"messgeid":"wM4821U14870af21N0245","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:21007f025ChO18D84l5t1商家已同意退货！","ctime":{"date":28,"day":1,"hours":15,"minutes":52,"month":7,"seconds":26,"time":1503906746000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-28","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":9,"isRead":1,"messgeid":"G820p8w715V103422R185","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"},{"content":"您的订单:21007f025ChO18D84l5t1已发货！","ctime":{"date":28,"day":1,"hours":14,"minutes":26,"month":7,"seconds":7,"time":1503901567000,"timezoneOffset":-480,"year":117},"ctimeStr":"2017-08-28","etime":{"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117},"flag":0,"id":7,"isRead":1,"messgeid":"8118L9a070k2v648514p2","sendForm":"","title":"订单状态","type":0,"usersId":"3U2LY06j4081M7171t3d2"}]
         * count : 0
         */

        private int count;
        private List<MessageListBean> messageList;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public static class MessageListBean {
            /**
             * content : 您的订单:37ey02p822n0LH12Q2659已申请售后！
             * ctime : {"date":30,"day":3,"hours":11,"minutes":30,"month":7,"seconds":18,"time":1504063818000,"timezoneOffset":-480,"year":117}
             * ctimeStr : 10小时前
             * etime : {"date":30,"day":3,"hours":18,"minutes":25,"month":7,"seconds":36,"time":1504088736000,"timezoneOffset":-480,"year":117}
             * flag : 0
             * id : 18
             * isRead : 1
             * messgeid : 084713e0g1KT01C83s210
             * sendForm :
             * title : 订单状态
             * type : 0
             * usersId : 3U2LY06j4081M7171t3d2
             */

            private String content;
            private CtimeBean ctime;
            private String ctimeStr;
            private EtimeBean etime;
            private int flag;
            private int id;
            private int isRead;
            private String messgeid;
            private String sendForm;
            private String title;
            private int type;
            private String usersId;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public CtimeBean getCtime() {
                return ctime;
            }

            public void setCtime(CtimeBean ctime) {
                this.ctime = ctime;
            }

            public String getCtimeStr() {
                return ctimeStr;
            }

            public void setCtimeStr(String ctimeStr) {
                this.ctimeStr = ctimeStr;
            }

            public EtimeBean getEtime() {
                return etime;
            }

            public void setEtime(EtimeBean etime) {
                this.etime = etime;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getMessgeid() {
                return messgeid;
            }

            public void setMessgeid(String messgeid) {
                this.messgeid = messgeid;
            }

            public String getSendForm() {
                return sendForm;
            }

            public void setSendForm(String sendForm) {
                this.sendForm = sendForm;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUsersId() {
                return usersId;
            }

            public void setUsersId(String usersId) {
                this.usersId = usersId;
            }

            public static class CtimeBean {
                /**
                 * date : 30
                 * day : 3
                 * hours : 11
                 * minutes : 30
                 * month : 7
                 * seconds : 18
                 * time : 1504063818000
                 * timezoneOffset : -480
                 * year : 117
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }

            public static class EtimeBean {
                /**
                 * date : 30
                 * day : 3
                 * hours : 18
                 * minutes : 25
                 * month : 7
                 * seconds : 36
                 * time : 1504088736000
                 * timezoneOffset : -480
                 * year : 117
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }
        }
    }
}
