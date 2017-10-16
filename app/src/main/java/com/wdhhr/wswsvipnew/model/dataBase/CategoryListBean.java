package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/16 0016.
 */

public class CategoryListBean extends DataSupport {
    /**
     * categoryId : aaaaa
     * categoryName : 美食娱乐
     * count : 0
     * ctime : {"date":23,"day":0,"hours":16,"minutes":27,"month":6,"seconds":27,"time":1500798447000,"timezoneOffset":-480,"year":117}
     * etime : {"date":23,"day":0,"hours":16,"minutes":27,"month":6,"seconds":29,"time":1500798449000,"timezoneOffset":-480,"year":117}
     * fid :
     * flag : 0
     * id : 1
     * isCheck : 0
     * pic :
     * sort : 1
     */

    private int id;
    private String categoryId;
    private String categoryName;
    private int count;
    private TimeBean ctime;
    private TimeBean etime;
    private String fid;
    private int flag;
    private int isCheck;
    private String pic;
    private int sort;

    @Override
    public String toString() {
        return "CategoryListBean{" +
                "id=" + id +
                ", categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", count=" + count +
                ", ctime=" + ctime +
                ", etime=" + etime +
                ", fid='" + fid + '\'' +
                ", flag=" + flag +
                ", isCheck=" + isCheck +
                ", pic='" + pic + '\'' +
                ", sort=" + sort +
                '}';
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
