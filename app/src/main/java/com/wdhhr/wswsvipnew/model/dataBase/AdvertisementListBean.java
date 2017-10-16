package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by felear on 2017/8/19 0019.
 */

public class AdvertisementListBean {
    /**
     * advertisementId : asdgasdg
     * advertisementPic : asdgasdgasdgasdgasdga
     * advertisementTitle : asdgasaaaaaaa
     * advertisementUrl : ggggggggggggg
     * ctime : {"date":1,"day":2,"hours":15,"minutes":55,"month":7,"seconds":53,"time":1501574153000,"timezoneOffset":-480,"year":117}
     * etime : null
     * id : 3
     * joinId :
     * type : 1
     */

    private String advertisementId;
    private String advertisementPic;
    private String advertisementTitle;
    private String advertisementUrl;
    private TimeBean ctime;
    private TimeBean etime;
    private int id;
    private String joinId;
    private int type;

    @Override
    public String toString() {
        return "AdvertisementListBean{" +
                "advertisementId='" + advertisementId + '\'' +
                ", advertisementPic='" + advertisementPic + '\'' +
                ", advertisementTitle='" + advertisementTitle + '\'' +
                ", advertisementUrl='" + advertisementUrl + '\'' +
                ", ctime=" + ctime +
                ", etime=" + etime +
                ", id=" + id +
                ", joinId='" + joinId + '\'' +
                ", type=" + type +
                '}';
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getAdvertisementPic() {
        return advertisementPic;
    }

    public void setAdvertisementPic(String advertisementPic) {
        this.advertisementPic = advertisementPic;
    }

    public String getAdvertisementTitle() {
        return advertisementTitle;
    }

    public void setAdvertisementTitle(String advertisementTitle) {
        this.advertisementTitle = advertisementTitle;
    }

    public String getAdvertisementUrl() {
        return advertisementUrl;
    }

    public void setAdvertisementUrl(String advertisementUrl) {
        this.advertisementUrl = advertisementUrl;
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public TimeBean getEtime() {
        return etime;
    }

    public void setEtime(TimeBean etime) {
        this.etime = etime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
