package com.wdhhr.wswsvipnew.model;

import com.wdhhr.wswsvipnew.model.dataBase.AddressListBean;

import java.util.List;

/**
 * Created by felear on 2017/8/25 0025.
 */

public class SetOrderBean {


    /**
     * status : 0
     * msg : 操作成功
     * time : 1503651377646
     * data : {"address":{"addressDesc":"华南城123去看哈哈哈男爸爸哈哈爸爸","address_id":"01A8k43501l12F75JQ410","area":"1960","city":"1955","ctime":{"date":14,"day":1,"hours":15,"minutes":54,"month":7,"seconds":1,"time":1502697241000,"timezoneOffset":-480,"year":117},"defualtAddress":"","flag":0,"id":51,"isDefualt":1,"name":"林起钊","phone":"13267166985","provice":"1827","tableAreas":null,"usersId":"7n0117u98170jh22720d7","utime":{"date":25,"day":5,"hours":16,"minutes":14,"month":7,"seconds":58,"time":1503648898000,"timezoneOffset":-480,"year":117}},"orderDetail":[{"price":210,"goodsPrice":210,"goodsPic":"http://39.108.105.63:8080/upload/goodImg/20170811/0781x13C2101911kr52YO.png","goodsName":"三鹿奶粉","departmentId":"e961e0c4271c6203f1157","goodsSpec":"大人,罐装,500ml","buyNum":1,"goodsInvertory":325,"goodsId":"jS5826150n7o2OM150111","goodsDetailId":"021E15s2187U15i066oy1"}],"priceCount":210,"goodsPrice":210,"isExsit":1}
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
         * address : {"addressDesc":"华南城123去看哈哈哈男爸爸哈哈爸爸","address_id":"01A8k43501l12F75JQ410","area":"1960","city":"1955","ctime":{"date":14,"day":1,"hours":15,"minutes":54,"month":7,"seconds":1,"time":1502697241000,"timezoneOffset":-480,"year":117},"defualtAddress":"","flag":0,"id":51,"isDefualt":1,"name":"林起钊","phone":"13267166985","provice":"1827","tableAreas":null,"usersId":"7n0117u98170jh22720d7","utime":{"date":25,"day":5,"hours":16,"minutes":14,"month":7,"seconds":58,"time":1503648898000,"timezoneOffset":-480,"year":117}}
         * orderDetail : [{"price":210,"goodsPrice":210,"goodsPic":"http://39.108.105.63:8080/upload/goodImg/20170811/0781x13C2101911kr52YO.png","goodsName":"三鹿奶粉","departmentId":"e961e0c4271c6203f1157","goodsSpec":"大人,罐装,500ml","buyNum":1,"goodsInvertory":325,"goodsId":"jS5826150n7o2OM150111","goodsDetailId":"021E15s2187U15i066oy1"}]
         * priceCount : 210
         * goodsPrice : 210
         * isExsit : 1
         */

        private AddressListBean address;
        private String priceCount;
        private String goodsPrice;
        private int isExsit;
        private List<OrderDetailBean> orderDetail;

        public AddressListBean getAddress() {
            return address;
        }

        public void setAddress(AddressListBean address) {
            this.address = address;
        }

        public String getPriceCount() {
            return priceCount;
        }

        public void setPriceCount(String priceCount) {
            this.priceCount = priceCount;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getIsExsit() {
            return isExsit;
        }

        public void setIsExsit(int isExsit) {
            this.isExsit = isExsit;
        }

        public List<OrderDetailBean> getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(List<OrderDetailBean> orderDetail) {
            this.orderDetail = orderDetail;
        }

        public static class OrderDetailBean {
            /**
             * price : 210
             * goodsPrice : 210
             * goodsPic : http://39.108.105.63:8080/upload/goodImg/20170811/0781x13C2101911kr52YO.png
             * goodsName : 三鹿奶粉
             * departmentId : e961e0c4271c6203f1157
             * goodsSpec : 大人,罐装,500ml
             * buyNum : 1
             * goodsInvertory : 325
             * goodsId : jS5826150n7o2OM150111
             * goodsDetailId : 021E15s2187U15i066oy1
             */

            private String price;
            private String goodsPrice;
            private String goodsPic;
            private String goodsName;
            private String departmentId;
            private String goodsSpec;
            private int buyNum;
            private int goodsInvertory;
            private String goodsId;
            private String goodsDetailId;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsPic() {
                return goodsPic;
            }

            public void setGoodsPic(String goodsPic) {
                this.goodsPic = goodsPic;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(String departmentId) {
                this.departmentId = departmentId;
            }

            public String getGoodsSpec() {
                return goodsSpec;
            }

            public void setGoodsSpec(String goodsSpec) {
                this.goodsSpec = goodsSpec;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public int getGoodsInvertory() {
                return goodsInvertory;
            }

            public void setGoodsInvertory(int goodsInvertory) {
                this.goodsInvertory = goodsInvertory;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsDetailId() {
                return goodsDetailId;
            }

            public void setGoodsDetailId(String goodsDetailId) {
                this.goodsDetailId = goodsDetailId;
            }
        }
    }
}
