package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/18 0018.
 */

public class BrandListBean extends DataSupport {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrandListBean that = (BrandListBean) o;

        return brandId.equals(that.brandId);

    }

    @Override
    public int hashCode() {
        return brandId.hashCode();
    }

    /**
     * brandDesc : 阿阿莱斯达克咖喱岁的gas离地感受到过i阿杜
     * brandId : asdgacccc
     * brandLogo :
     * brandName : 中国黄金
     * brandPic :
     * brandPrice : 1
     * categoryId : 1
     * ctime : {"date":28,"day":5,"hours":11,"minutes":11,"month":6,"seconds":57,"time":1501211517000,"timezoneOffset":-480,"year":117}
     * etime : null
     * id : 2
     * isCollection : 0
     * isFlag : 0
     * online : 2
     */

    private String brandDesc;
    private String brandId;
    private String brandLogo;
    private String brandName;
    private String brandPic;
    private int brandPrice;
    private String categoryId;
    private TimeBean ctime;
    private TimeBean etime;
    private int id;
    private int isCollection;
    private int isFlag;
    private int online;

    @Override
    public String toString() {
        return "BrandListBean{" +
                "brandDesc='" + brandDesc + '\'' +
                ", brandId='" + brandId + '\'' +
                ", brandLogo='" + brandLogo + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brandPic='" + brandPic + '\'' +
                ", brandPrice=" + brandPrice +
                ", categoryId='" + categoryId + '\'' +
                ", ctime=" + ctime +
                ", etime=" + etime +
                ", id=" + id +
                ", isCollection=" + isCollection +
                ", isFlag=" + isFlag +
                ", online=" + online +
                '}';
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandPic() {
        return brandPic;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic;
    }

    public int getBrandPrice() {
        return brandPrice;
    }

    public void setBrandPrice(int brandPrice) {
        this.brandPrice = brandPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(int isFlag) {
        this.isFlag = isFlag;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}
