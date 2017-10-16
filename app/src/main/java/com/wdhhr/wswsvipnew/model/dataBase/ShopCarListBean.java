package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by felear on 2017/8/23 0023.
 */

public class ShopCarListBean {

    /**
     * ctime : {"date":11,"day":5,"hours":15,"minutes":45,"month":7,"seconds":27,"time":1502437527000,"timezoneOffset":-480,"year":117}
     * flag : 0
     * goods : {"brandId":"asdgacccc","businessId":"","categoryId":"aaaaa","costPrice":60,"ctime":{"date":11,"day":5,"hours":15,"minutes":45,"month":7,"seconds":27,"time":1502437527000,"timezoneOffset":-480,"year":117},"departmentId":"","describe":"","discountPrice":10,"earn":10,"etime":null,"goodsCount":0,"goodsId":"5810q01B154o47s54Q2f1","goodsInventory":8,"goodsName":"看守所里唱传奇","goodsPic":"http://39.108.105.63:8080/upload/goodImg/20170811/0Tp2387534a5d91102211.png","goodsPrice":80,"goodsSpec":"","goodsSpecJSON":"[{\"SpecA\": \"声调\"},{\"SpecB\": \"声色\"},{\"SpecC\": \"腔调\"}]","goodsSpecList":[],"id":24,"isCollection":0,"isFlag":0,"isHost":0,"isOff":0,"onlineSaleNum":7,"postage":10,"replenishmentCount":0,"replenishmentState":0,"specDataJSON":"","specDetailJSONList":"","vipPrice":80}
     * goodsDetailId : 00ZaL1114598758oY4421
     * goodsId : 5810q01B154o47s54Q2f1
     * goodsSpec : {"c_id":28,"costPrice":60,"ctime":{"date":11,"day":5,"hours":15,"minutes":45,"month":7,"seconds":27,"time":1502437527000,"timezoneOffset":-480,"year":117},"discountPrice":10,"earn":10,"etime":null,"flag":0,"goodsDetailCount":10,"goodsDetailId":"00ZaL1114598758oY4421","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/4ik1754Q34p82120w0121.png","goodsDetailPrice":100,"goodsDetailSpec":"","goodsId":"5810q01B154o47s54Q2f1","isHost":0,"postage":10,"profit":0,"specA":"四三拍","specB":"杀猪高音","specC":"最炫民族风","specD":"","specE":"","vipPrice":80}
     * num : 2
     * sc_id : 77
     * shopcarId : 1014U2b2j83090A7u8222
     * usersId : 7n0117u98170jh22720d7
     */

    private TimeBean ctime;
    private int flag;
    private GoodsListBean goods;
    private String goodsDetailId;
    private String goodsId;
    private GoodsSpecListBean goodsSpec;
    private int num;
    private int sc_id;
    private String shopcarId;
    private String usersId;
    // 是否已选中 0未选中，1选中
    private int isCheck;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopCarListBean that = (ShopCarListBean) o;

        return shopcarId.equals(that.shopcarId);

    }

    @Override
    public int hashCode() {
        return shopcarId.hashCode();
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
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

    public GoodsListBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsListBean goods) {
        this.goods = goods;
    }

    public String getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(String goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsSpecListBean getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(GoodsSpecListBean goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSc_id() {
        return sc_id;
    }

    public void setSc_id(int sc_id) {
        this.sc_id = sc_id;
    }

    public String getShopcarId() {
        return shopcarId;
    }

    public void setShopcarId(String shopcarId) {
        this.shopcarId = shopcarId;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

}