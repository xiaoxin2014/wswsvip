package com.wdhhr.wswsvipnew.model.dataBase;

import java.util.List;

/**
 * Created by szq on 2017/8/29.
 */

public class WeibiCommonBean {


    /**
     * status : 0
     * msg : 操作成功
     * time : 1504076761800
     * data : {"GoldList":[{"ctime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":10,"time":1503976270000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":11,"minutes":11,"month":10,"seconds":10,"time":1511925070000,"timezoneOffset":-480,"year":117},"id":6,"isFlag":0,"money":20,"sendFormGlod":"","sendFormId":"","status":4,"stime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":10,"time":1503976270000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 11:11","type":"2","usersId":"3U2LY06j4081M7171t3d2","wGlod":"a114G8s400A2A17921101"},{"ctime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":30,"time":1503976290000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":11,"minutes":11,"month":10,"seconds":30,"time":1511925090000,"timezoneOffset":-480,"year":117},"id":7,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"3U2LY06j4081M7171t3d2","status":2,"stime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":30,"time":1503976290000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 11:11","type":"0","usersId":"3U2LY06j4081M7171t3d2","wGlod":"2t0e1Z1900v2713X11f8y"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":10,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":3,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K213"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":11,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":4,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K212"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":12,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":5,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K211"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":14,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":3,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K209"}],"tatolMoney":85,"count":6,"userPhoto":"http://39.108.105.63:8080/upload/userImg/20170829/80i02IE1BC49Y02452B71.png"}
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
         * GoldList : [{"ctime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":10,"time":1503976270000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":11,"minutes":11,"month":10,"seconds":10,"time":1511925070000,"timezoneOffset":-480,"year":117},"id":6,"isFlag":0,"money":20,"sendFormGlod":"","sendFormId":"","status":4,"stime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":10,"time":1503976270000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 11:11","type":"2","usersId":"3U2LY06j4081M7171t3d2","wGlod":"a114G8s400A2A17921101"},{"ctime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":30,"time":1503976290000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":11,"minutes":11,"month":10,"seconds":30,"time":1511925090000,"timezoneOffset":-480,"year":117},"id":7,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"3U2LY06j4081M7171t3d2","status":2,"stime":{"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":30,"time":1503976290000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 11:11","type":"0","usersId":"3U2LY06j4081M7171t3d2","wGlod":"2t0e1Z1900v2713X11f8y"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":10,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":3,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K213"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":11,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":4,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K212"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":12,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":5,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K211"},{"ctime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"etime":{"date":29,"day":3,"hours":15,"minutes":56,"month":10,"seconds":30,"time":1511942190000,"timezoneOffset":-480,"year":117},"id":14,"isFlag":0,"money":13,"sendFormGlod":"","sendFormId":"","status":3,"stime":{"date":29,"day":2,"hours":15,"minutes":56,"month":7,"seconds":30,"time":1503993390000,"timezoneOffset":-480,"year":117},"stimeStr":"2017-08-29 15:56","type":"1","usersId":"3U2LY06j4081M7171t3d2","wGlod":"910sw239600K78iM5K209"}]
         * tatolMoney : 85
         * count : 6
         * userPhoto : http://39.108.105.63:8080/upload/userImg/20170829/80i02IE1BC49Y02452B71.png
         */

        private int tatolMoney;
        private int count;
        private String userPhoto;
        private List<GoldListBean> GoldList;

        public int getTatolMoney() {
            return tatolMoney;
        }

        public void setTatolMoney(int tatolMoney) {
            this.tatolMoney = tatolMoney;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public List<GoldListBean> getGoldList() {
            return GoldList;
        }

        public void setGoldList(List<GoldListBean> GoldList) {
            this.GoldList = GoldList;
        }

        public static class GoldListBean {
            /**
             * ctime : {"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":10,"time":1503976270000,"timezoneOffset":-480,"year":117}
             * etime : {"date":29,"day":3,"hours":11,"minutes":11,"month":10,"seconds":10,"time":1511925070000,"timezoneOffset":-480,"year":117}
             * id : 6
             * isFlag : 0
             * money : 20
             * sendFormGlod :
             * sendFormId :
             * status : 4
             * stime : {"date":29,"day":2,"hours":11,"minutes":11,"month":7,"seconds":10,"time":1503976270000,"timezoneOffset":-480,"year":117}
             * stimeStr : 2017-08-29 11:11
             * type : 2
             * usersId : 3U2LY06j4081M7171t3d2
             * wGlod : a114G8s400A2A17921101
             */

            private CtimeBean ctime;
            private EtimeBean etime;
            private int id;
            private int isFlag;
            private int money;
            private String sendFormGlod;
            private String sendFormId;
            private int status;
            private StimeBean stime;
            private String stimeStr;
            private String type;
            private String usersId;
            private String wGlod;

            public CtimeBean getCtime() {
                return ctime;
            }

            public void setCtime(CtimeBean ctime) {
                this.ctime = ctime;
            }

            public EtimeBean getEtime() {
                return etime;
            }

            public void setEtime(EtimeBean etime) {
                this.etime = etime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsFlag() {
                return isFlag;
            }

            public void setIsFlag(int isFlag) {
                this.isFlag = isFlag;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public String getSendFormGlod() {
                return sendFormGlod;
            }

            public void setSendFormGlod(String sendFormGlod) {
                this.sendFormGlod = sendFormGlod;
            }

            public String getSendFormId() {
                return sendFormId;
            }

            public void setSendFormId(String sendFormId) {
                this.sendFormId = sendFormId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public StimeBean getStime() {
                return stime;
            }

            public void setStime(StimeBean stime) {
                this.stime = stime;
            }

            public String getStimeStr() {
                return stimeStr;
            }

            public void setStimeStr(String stimeStr) {
                this.stimeStr = stimeStr;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUsersId() {
                return usersId;
            }

            public void setUsersId(String usersId) {
                this.usersId = usersId;
            }

            public String getWGlod() {
                return wGlod;
            }

            public void setWGlod(String wGlod) {
                this.wGlod = wGlod;
            }

            public static class CtimeBean {
                /**
                 * date : 29
                 * day : 2
                 * hours : 11
                 * minutes : 11
                 * month : 7
                 * seconds : 10
                 * time : 1503976270000
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
                 * date : 29
                 * day : 3
                 * hours : 11
                 * minutes : 11
                 * month : 10
                 * seconds : 10
                 * time : 1511925070000
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

            public static class StimeBean {
                /**
                 * date : 29
                 * day : 2
                 * hours : 11
                 * minutes : 11
                 * month : 7
                 * seconds : 10
                 * time : 1503976270000
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
