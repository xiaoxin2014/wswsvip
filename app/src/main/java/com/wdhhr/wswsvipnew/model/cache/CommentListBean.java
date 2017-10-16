package com.wdhhr.wswsvipnew.model.cache;

/**
 * Created by felear on 2017/8/20 0020.
 */

public class CommentListBean {
    /**
     * beforTime : 2017-08-15
     * commentContent : 。。。
     * commentId : 035812jdzZq087e80S101
     * commentUserId : 7n0117u98170jh22720d7
     * conversationId : 0518M7M31np1hO8H02215
     * ctime : {"date":15,"day":2,"hours":8,"minutes":8,"month":7,"seconds":13,"time":1502755693000,"timezoneOffset":-480,"year":117}
     * flag : 0
     * id : 57
     * isCheck : 0
     * replyNum : 1
     * replyUserId :
     * status : 1
     * upNum : 0
     * userName : 啦啦啦。U7u
     * userPhoto : http://39.108.105.63:8080/upload/userImg/20170817/07013C045zt3Y1R028r27.png
     */

    private String beforTime;
    private String commentContent;
    private String commentId;
    private String commentUserId;
    private String conversationId;
    private CtimeBean ctime;
    private int flag;
    private int id;
    private int isCheck;
    private int replyNum;
    private String replyUserId;
    private int status;
    private int upNum;
    private String userName;
    private String userPhoto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentListBean that = (CommentListBean) o;

        return commentId.equals(that.commentId);

    }

    @Override
    public int hashCode() {
        return commentId.hashCode();
    }

    public String getBeforTime() {
        return beforTime;
    }

    public void setBeforTime(String beforTime) {
        this.beforTime = beforTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public CtimeBean getCtime() {
        return ctime;
    }

    public void setCtime(CtimeBean ctime) {
        this.ctime = ctime;
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

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUpNum() {
        return upNum;
    }

    public void setUpNum(int upNum) {
        this.upNum = upNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public static class CtimeBean {
        /**
         * date : 15
         * day : 2
         * hours : 8
         * minutes : 8
         * month : 7
         * seconds : 13
         * time : 1502755693000
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
