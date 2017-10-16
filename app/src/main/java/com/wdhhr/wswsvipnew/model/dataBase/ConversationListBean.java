package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/19 0019.
 */

public class ConversationListBean extends DataSupport {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationListBean that = (ConversationListBean) o;

        return conversationId.equals(that.conversationId);

    }

    @Override
    public int hashCode() {
        return conversationId.hashCode();
    }

    /**
     * beforTime :
     * comment : 0
     * conversationContent : 1111
     * conversationId : 1VO06581vd117A1F83230
     * conversationTitle : 111
     * conversationTypeId : 1
     * ctime : {"date":14,"day":1,"hours":19,"minutes":3,"month":7,"seconds":37,"time":1502708617000,"timezoneOffset":-480,"year":117}
     * flag : 0
     * id : 14
     * isCheck : 0
     * picUrl : http://39.108.105.63:8080/upload/businesImg/20170805/5045816NR3703n03X1DN2.png
     * readNum : 0
     * thumbNailImg :
     * upNum : 0
     */


    private String beforTime;
    private int comment;
    private String conversationContent;
    private String conversationId;
    private String conversationTitle;
    private String conversationTypeId;
    private TimeBean ctime;
    private int flag;
    private int id;
    private int isCheck;
    private String picUrl;
    private int readNum;
    private String thumbNailImg;
    private int upNum;

    @Override
    public String toString() {
        return "ConversationListBean{" +
                "beforTime='" + beforTime + '\'' +
                ", comment=" + comment +
                ", conversationContent='" + conversationContent + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", conversationTitle='" + conversationTitle + '\'' +
                ", conversationTypeId='" + conversationTypeId + '\'' +
                ", ctime=" + ctime +
                ", flag=" + flag +
                ", id=" + id +
                ", isCheck=" + isCheck +
                ", picUrl='" + picUrl + '\'' +
                ", readNum=" + readNum +
                ", thumbNailImg='" + thumbNailImg + '\'' +
                ", upNum=" + upNum +
                '}';
    }

    public String getBeforTime() {
        return beforTime;
    }

    public void setBeforTime(String beforTime) {
        this.beforTime = beforTime;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getConversationContent() {
        return conversationContent;
    }

    public void setConversationContent(String conversationContent) {
        this.conversationContent = conversationContent;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationTitle() {
        return conversationTitle;
    }

    public void setConversationTitle(String conversationTitle) {
        this.conversationTitle = conversationTitle;
    }

    public String getConversationTypeId() {
        return conversationTypeId;
    }

    public void setConversationTypeId(String conversationTypeId) {
        this.conversationTypeId = conversationTypeId;
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public String getThumbNailImg() {
        return thumbNailImg;
    }

    public void setThumbNailImg(String thumbNailImg) {
        this.thumbNailImg = thumbNailImg;
    }

    public int getUpNum() {
        return upNum;
    }

    public void setUpNum(int upNum) {
        this.upNum = upNum;
    }
}
