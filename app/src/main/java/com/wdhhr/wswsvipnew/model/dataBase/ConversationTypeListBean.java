package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by felear on 2017/8/19 0019.
 */

public class ConversationTypeListBean {
    /**
     * citme : {"date":23,"day":0,"hours":16,"minutes":26,"month":6,"seconds":57,"time":1500798417000,"timezoneOffset":-480,"year":117}
     * conversationName : 铁血军事
     * conversationPic : asdg
     * conversationTypeId : 1
     * id : 1
     * isFlag : 0
     * status : 0
     */

    private TimeBean citme;
    private String conversationName;
    private String conversationPic;
    private String conversationTypeId;
    private int id;
    private int isFlag;
    private int status;

    public TimeBean getCitme() {
        return citme;
    }

    public void setCitme(TimeBean citme) {
        this.citme = citme;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationPic() {
        return conversationPic;
    }

    public void setConversationPic(String conversationPic) {
        this.conversationPic = conversationPic;
    }

    public String getConversationTypeId() {
        return conversationTypeId;
    }

    public void setConversationTypeId(String conversationTypeId) {
        this.conversationTypeId = conversationTypeId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
