package com.wdhhr.wswsvipnew.model;

import com.wdhhr.wswsvipnew.model.cache.CommentListBean;
import com.wdhhr.wswsvipnew.model.dataBase.*;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by felear on 2017/8/19 0019.
 */

public class ShopCommonBean {

    /**
     * status : 0
     * msg : 操作成功
     * time : 1502952636351
     * data : {"goodsSpecList":[{"c_id":21,"costPrice":130,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":60,"earn":20,"etime":null,"flag":0,"goodsDetailCount":1000,"goodsDetailId":"021E15s2187U15i066oy1","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/0781x13C2101911kr52YO.png","goodsDetailPrice":200,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"大人","specB":"罐装","specC":"500ml","specD":"","specE":"","vipPrice":180},{"c_id":22,"costPrice":100,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":50,"earn":20,"etime":null,"flag":0,"goodsDetailCount":1000,"goodsDetailId":"H11662l71M50101A283T5","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/S1iD315011k802x729O10.png","goodsDetailPrice":180,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"大人","specB":"袋装","specC":"350ml","specD":"","specE":"","vipPrice":150},{"c_id":23,"costPrice":110,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":60,"earn":50,"etime":null,"flag":0,"goodsDetailCount":11111,"goodsDetailId":"2jO8Z17116y21oc5501w0","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/1Rh71s57001P121cl8F15.png","goodsDetailPrice":190,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":10,"profit":0,"specA":"大人","specB":"罐装","specC":"350ml","specD":"","specE":"","vipPrice":160},{"c_id":24,"costPrice":820,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":500,"earn":360,"etime":null,"flag":0,"goodsDetailCount":2000,"goodsDetailId":"P17s10215g65r1810T2c6","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/11b0I150o87291N148m90.png","goodsDetailPrice":1800,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":20,"profit":0,"specA":"中年","specB":"罐装","specC":"2L ","specD":"","specE":"","vipPrice":1500},{"c_id":25,"costPrice":2820,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":2000,"earn":1200,"etime":null,"flag":0,"goodsDetailCount":200,"goodsDetailId":"iW11015181d0567B2227z","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/282U14210ut07M19H1151.png","goodsDetailPrice":5500,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":20,"profit":0,"specA":"儿童","specB":"罐装","specC":"2L","specD":"","specE":"","vipPrice":4000},{"c_id":26,"costPrice":2820,"ctime":{"date":11,"day":5,"hours":15,"minutes":27,"month":7,"seconds":33,"time":1502436453000,"timezoneOffset":-480,"year":117},"discountPrice":2000,"earn":1200,"etime":null,"flag":0,"goodsDetailCount":200,"goodsDetailId":"2j01m821151Wk1W0Ri765","goodsDetailInventory":0,"goodsDetailOff":0,"goodsDetailPic":"http://39.108.105.63:8080/upload/goodImg/20170811/282U14210ut07M19H1151.png","goodsDetailPrice":5500,"goodsDetailSpec":"","goodsId":"jS5826150n7o2OM150111","isHost":0,"postage":20,"profit":0,"specA":"儿童","specB":"罐装","specC":"2L","specD":"","specE":"","vipPrice":4000}]}
     */

    private int status;
    private String msg;
    private long time;
    private DataBean data;

    @Override
    public String toString() {
        return "ShopCommonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                ", data=" + data +
                '}';
    }

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


        private int count;
        private int commentCount;
        private int goodsCount;
        private int myPendingPayment;
        private int myShipmentPending;
        private int myDelivered;
        private int myAfterSales;
        private int clientPendingPayment;
        private int clientShipmentPending;
        private int clientDelivered;
        private int clientAfterSales;
        private int message;
        private List<GoodsSpecListBean> goodsSpecList;
        private List<CategoryListBean> categoryList;
        private List<AdvertisementListBean> advertisementList;
        private List<BrandListBean> brandList;
        private List<GoodsListBean> goodsList;
        private List<ConversationListBean> conversationList;
        private List<String> goodsName;
        private List<ConversationTypeListBean> conversationTypeList;
        private List<CommentListBean> commentList;
        private List<ShopCarListBean> ShopCarList;

        public int getMyPendingPayment() {
            return myPendingPayment;
        }

        public void setMyPendingPayment(int myPendingPayment) {
            this.myPendingPayment = myPendingPayment;
        }

        public int getMyShipmentPending() {
            return myShipmentPending;
        }

        public void setMyShipmentPending(int myShipmentPending) {
            this.myShipmentPending = myShipmentPending;
        }

        public int getMyDelivered() {
            return myDelivered;
        }

        public void setMyDelivered(int myDelivered) {
            this.myDelivered = myDelivered;
        }

        public int getMyAfterSales() {
            return myAfterSales;
        }

        public void setMyAfterSales(int myAfterSales) {
            this.myAfterSales = myAfterSales;
        }

        public int getClientPendingPayment() {
            return clientPendingPayment;
        }

        public void setClientPendingPayment(int clientPendingPayment) {
            this.clientPendingPayment = clientPendingPayment;
        }

        public int getClientShipmentPending() {
            return clientShipmentPending;
        }

        public void setClientShipmentPending(int clientShipmentPending) {
            this.clientShipmentPending = clientShipmentPending;
        }

        public int getClientDelivered() {
            return clientDelivered;
        }

        public void setClientDelivered(int clientDelivered) {
            this.clientDelivered = clientDelivered;
        }

        public int getClientAfterSales() {
            return clientAfterSales;
        }

        public void setClientAfterSales(int clientAfterSales) {
            this.clientAfterSales = clientAfterSales;
        }

        public int getMessage() {
            return message;
        }

        public void setMessage(int message) {
            this.message = message;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public List<ShopCarListBean> getShopCarList() {
            return ShopCarList;
        }

        public void setShopCarList(List<ShopCarListBean> shopCarList) {
            this.ShopCarList = shopCarList;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentListBeanList) {
            this.commentList = commentListBeanList;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public List<GoodsSpecListBean> getGoodsSpecList() {
            return goodsSpecList;
        }

        public void setGoodsSpecList(List<GoodsSpecListBean> goodsSpecList) {
            this.goodsSpecList = goodsSpecList;
        }

        public List<GoodsSpecListBean> getGoodsSpecListAndSave() {
            if (goodsSpecList != null) {
                for (GoodsSpecListBean spec : goodsSpecList) {
                    DataSupport.deleteAll(GoodsSpecListBean.class, "goodsDetailId = ?", spec.getGoodsDetailId());
                    spec.save();
                }
            }
            return goodsSpecList;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public List<AdvertisementListBean> getAdvertisementList() {
            return advertisementList;
        }

        public void setAdvertisementList(List<AdvertisementListBean> advertisementList) {
            this.advertisementList = advertisementList;
        }


        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<BrandListBean> getBrandList() {
            return brandList;
        }

        public List<BrandListBean> getBrandListAndSave() {
            if (brandList != null) {
                for (BrandListBean brand : brandList) {
                    DataSupport.deleteAll(BrandListBean.class, "brandId = ?", brand.getBrandId());
                    brand.save();
                }
            }
            return brandList;
        }

        public void setBrandList(List<BrandListBean> brandList) {
            this.brandList = brandList;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public List<GoodsListBean> getGoodsListAndSave() {
            if (goodsList != null) {
                for (GoodsListBean goods : goodsList) {
                    DataSupport.deleteAll(GoodsListBean.class, "goodsId = ?", goods.getGoodsId());
                    goods.save();
                }
            }
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public List<ConversationListBean> getConversationList() {
            return conversationList;
        }

        public List<ConversationListBean> getConversationListAndSave() {
            if (conversationList != null) {
                for (ConversationListBean conversationListBean : conversationList) {
                    DataSupport.deleteAll(ConversationListBean.class,
                            "conversationId = ?", conversationListBean.getConversationId());
                    conversationListBean.save();
                }
            }
            return conversationList;
        }

        public void setConversationList(List<ConversationListBean> conversationList) {
            this.conversationList = conversationList;
        }


        public List<String> getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(List<String> goodsName) {
            this.goodsName = goodsName;
        }

        public List<ConversationTypeListBean> getConversationTypeList() {
            return conversationTypeList;
        }

        public void setConversationTypeList(List<ConversationTypeListBean> conversationTypeList) {
            this.conversationTypeList = conversationTypeList;
        }
    }

}
