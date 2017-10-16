package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by felear on 2017/8/17 0017.
 */

public class GoodsListBean extends DataSupport {
    @Override
    public String toString() {
        return "GoodsListBean{" +
                "brandId='" + brandId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", costPrice=" + costPrice +
                ", ctime=" + ctime +
                ", departmentId='" + departmentId + '\'' +
                ", describe='" + describe + '\'' +
                ", discountPrice=" + discountPrice +
                ", earn=" + earn +
                ", etime=" + etime +
                ", goodsCount=" + goodsCount +
                ", goodsId='" + goodsId + '\'' +
                ", goodsInventory=" + goodsInventory +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPic='" + goodsPic + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", goodsSpecJSON='" + goodsSpecJSON + '\'' +
                ", id=" + id +
                ", isCollection=" + isCollection +
                ", isFlag=" + isFlag +
                ", isHost=" + isHost +
                ", isOff=" + isOff +
                ", onlineSaleNum=" + onlineSaleNum +
                ", postage=" + postage +
                ", specDataJSON='" + specDataJSON + '\'' +
                ", specDetailJSONList='" + specDetailJSONList + '\'' +
                ", vipPrice=" + vipPrice +
                ", goodsSpecList=" + goodsSpecList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsListBean that = (GoodsListBean) o;

        return goodsId.equals(that.goodsId);

    }

    @Override
    public int hashCode() {
        return goodsId.hashCode();
    }

    /**
     * brandId :
     * businessId :
     * categoryId : aaaaa
     * costPrice : 130
     * ctime : {"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117}
     * departmentId : 1
     * describe :
     * discountPrice : 190
     * earn : 0
     * etime : null
     * goodsCount : 0
     * goodsId : jS5826150n7o2OM150111
     * goodsInventory : 0
     * goodsName : 三鹿奶粉
     * goodsPic : [http://39.108.105.63:8080/upload/goodImg/20170811/0781x13C2101911kr52YO.png,,,]
     * goodsPrice : 200
     * goodsSpec :
     * goodsSpecJSON : [{"SpecA": "适用人群"},{"SpecB": "大小"},{"SpecC": "容量"}]
     * goodsSpecList : [{"c_id":21,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":190,"earn":0,"etime":null,"flag":0,"goodsDetailCount":1000,"goodsDetailId":"021E15s2187U15i066oy1","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/0781x13C2101911kr52YO.png","goodsDetailPrice":200,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"大人","specB":"罐装","specC":"500ml","specD":"","specE":"","vipPrice":180},{"c_id":22,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":190,"earn":0,"etime":null,"flag":0,"goodsDetailCount":1000,"goodsDetailId":"H11662l71M50101A283T5","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/S1iD315011k802x729O10.png","goodsDetailPrice":180,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"大人","specB":"袋装","specC":"350ml","specD":"","specE":"","vipPrice":180},{"c_id":23,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":190,"earn":0,"etime":null,"flag":0,"goodsDetailCount":11111,"goodsDetailId":"2jO8Z17116y21oc5501w0","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/1Rh71s57001P121cl8F15.png","goodsDetailPrice":190,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"大人","specB":"罐装","specC":"350ml","specD":"","specE":"","vipPrice":180},{"c_id":24,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":190,"earn":0,"etime":null,"flag":0,"goodsDetailCount":2000,"goodsDetailId":"P17s10215g65r1810T2c6","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/11b0I150o87291N148m90.png","goodsDetailPrice":1800,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"中年","specB":"罐装","specC":"2L ","specD":"","specE":"","vipPrice":180},{"c_id":25,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":190,"earn":0,"etime":null,"flag":0,"goodsDetailCount":200,"goodsDetailId":"iW11015181d0567B2227z","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/282U14210ut07M19H1151.png","goodsDetailPrice":5500,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"儿童","specB":"罐装","specC":"2L","specD":"","specE":"","vipPrice":180},{"c_id":26,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":190,"earn":0,"etime":null,"flag":0,"goodsDetailCount":200,"goodsDetailId":"2j01m821151Wk1W0Ri765","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/282U14210ut07M19H1151.png","goodsDetailPrice":5500,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"儿童","specB":"罐装","specC":"2L","specD":"","specE":"","vipPrice":180}]
     * id : 23
     * isCollection : 0
     * isFlag : 0
     * isHost : 0
     * isOff : 0
     * onlineSaleNum : 0
     * postage : 10
     * specDataJSON :
     * specDetailJSONList :
     * vipPrice : 180
     */


    private String brandId;
    private String businessId;
    private String bannerPic;
    private String categoryId;
    private String costPrice;
    private TimeBean ctime;
    private String departmentId;
    private String describe;
    private String discountPrice;
    private String earn;
    private TimeBean etime;
    private String goodDetailPic;
    private int goodsCount;
    private String goodsId;
    private int goodsInventory;
    private String goodsName;
    private String goodsPic;
    private String goodsPrice;
    private String goodsSpec;
    private String goodsSpecJSON;
    private int id;
    private int isCollection;
    private int isFlag;
    private int isHost;
    private int isOff;
    private int onlineSaleNum;
    private int postage;
    private int replenishmentCount;
    private int replenishmentState;
    private String specDataJSON;
    private String specDetailJSONList;
    private String vipPrice;
    private List<GoodsSpecListBean> goodsSpecList;

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getEarn() {
        return earn;
    }

    public void setEarn(String earn) {
        this.earn = earn;
    }

    public TimeBean getEtime() {
        return etime;
    }

    public void setEtime(TimeBean etime) {
        this.etime = etime;
    }

    public String getGoodDetailPic() {
        return goodDetailPic;
    }

    public void setGoodDetailPic(String goodDetailPic) {
        this.goodDetailPic = goodDetailPic;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsInventory() {
        return goodsInventory;
    }

    public void setGoodsInventory(int goodsInventory) {
        this.goodsInventory = goodsInventory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsSpecJSON() {
        return goodsSpecJSON;
    }

    public void setGoodsSpecJSON(String goodsSpecJSON) {
        this.goodsSpecJSON = goodsSpecJSON;
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

    public int getIsHost() {
        return isHost;
    }

    public void setIsHost(int isHost) {
        this.isHost = isHost;
    }

    public int getIsOff() {
        return isOff;
    }

    public void setIsOff(int isOff) {
        this.isOff = isOff;
    }

    public int getOnlineSaleNum() {
        return onlineSaleNum;
    }

    public void setOnlineSaleNum(int onlineSaleNum) {
        this.onlineSaleNum = onlineSaleNum;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public int getReplenishmentCount() {
        return replenishmentCount;
    }

    public void setReplenishmentCount(int replenishmentCount) {
        this.replenishmentCount = replenishmentCount;
    }

    public int getReplenishmentState() {
        return replenishmentState;
    }

    public void setReplenishmentState(int replenishmentState) {
        this.replenishmentState = replenishmentState;
    }

    public String getSpecDataJSON() {
        return specDataJSON;
    }

    public void setSpecDataJSON(String specDataJSON) {
        this.specDataJSON = specDataJSON;
    }

    public String getSpecDetailJSONList() {
        return specDetailJSONList;
    }

    public void setSpecDetailJSONList(String specDetailJSONList) {
        this.specDetailJSONList = specDetailJSONList;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public List<GoodsSpecListBean> getGoodsSpecList() {

        if (goodsSpecList != null) {
            return goodsSpecList;
        } else {
            return DataSupport.where("goodsId = ?", goodsId).find(GoodsSpecListBean.class);
        }

    }

    public void setGoodsSpecList(List<GoodsSpecListBean> goodsSpecList) {

        this.goodsSpecList = goodsSpecList;
    }

}