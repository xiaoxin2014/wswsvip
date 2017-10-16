package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by szq on 2017/8/25.
 */

public class BusinessInfoBean {


    /**
     * status : 0
     * msg : 操作成功
     * time : 1503630352500
     * data : {"businessAddress":"1","businessArea":"1","businessBackImg":"http://39.108.105.63:8080/upload/businesImg/20170812/801U52h1s0O29V47119u0.png","businessCertificate":"1","businessCity":"1","businessContactName":"22223333！！！.","businessContactPhone":"","businessDesc":"123？？？！！？","businessId":"1","businessLegal":"1","businessLegalPhone":"1","businessName":"asasd","businessPic":"http://39.108.105.63:8080/upload/businesImg/20170805/5045816NR3703n03X1DN2.png","businessProvice":"1","businessStatus":0,"categoryId":"01G67EUm01220370Ue499","categoryName":"","id":1,"isFlag":0,"usersId":"7n0117u98170jh22720d7"}
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
         * businessAddress : 1
         * businessArea : 1
         * businessBackImg : http://39.108.105.63:8080/upload/businesImg/20170812/801U52h1s0O29V47119u0.png
         * businessCertificate : 1
         * businessCity : 1
         * businessContactName : 22223333！！！.
         * businessContactPhone :
         * businessDesc : 123？？？！！？
         * businessId : 1
         * businessLegal : 1
         * businessLegalPhone : 1
         * businessName : asasd
         * businessPic : http://39.108.105.63:8080/upload/businesImg/20170805/5045816NR3703n03X1DN2.png
         * businessProvice : 1
         * businessStatus : 0
         * categoryId : 01G67EUm01220370Ue499
         * categoryName :
         * id : 1
         * isFlag : 0
         * usersId : 7n0117u98170jh22720d7
         */

        private String businessAddress;
        private String businessArea;
        private String businessBackImg;
        private String businessCertificate;
        private String businessCity;
        private String businessContactName;
        private String businessContactPhone;
        private String businessDesc;
        private String businessId;
        private String businessLegal;
        private String businessLegalPhone;
        private String businessName;
        private String businessPic;
        private String businessProvice;
        private int businessStatus;
        private int toDayOrders;
        private int toDayVisit;
        private int visitSum;
        private int orderPriceSum;
        private String categoryId;
        private String categoryName;
        private int id;
        private int isFlag;
        private String usersId;

        public int getToDayOrders() {
            return toDayOrders;
        }

        public void setToDayOrders(int toDayOrders) {
            this.toDayOrders = toDayOrders;
        }

        public int getToDayVisit() {
            return toDayVisit;
        }

        public void setToDayVisit(int toDayVisit) {
            this.toDayVisit = toDayVisit;
        }

        public int getVisitSum() {
            return visitSum;
        }

        public void setVisitSum(int visitSum) {
            this.visitSum = visitSum;
        }

        public int getOrderPriceSum() {
            return orderPriceSum;
        }

        public void setOrderPriceSum(int orderPriceSum) {
            this.orderPriceSum = orderPriceSum;
        }

        public String getBusinessAddress() {
            return businessAddress;
        }

        public void setBusinessAddress(String businessAddress) {
            this.businessAddress = businessAddress;
        }

        public String getBusinessArea() {
            return businessArea;
        }

        public void setBusinessArea(String businessArea) {
            this.businessArea = businessArea;
        }

        public String getBusinessBackImg() {
            return businessBackImg;
        }

        public void setBusinessBackImg(String businessBackImg) {
            this.businessBackImg = businessBackImg;
        }

        public String getBusinessCertificate() {
            return businessCertificate;
        }

        public void setBusinessCertificate(String businessCertificate) {
            this.businessCertificate = businessCertificate;
        }

        public String getBusinessCity() {
            return businessCity;
        }

        public void setBusinessCity(String businessCity) {
            this.businessCity = businessCity;
        }

        public String getBusinessContactName() {
            return businessContactName;
        }

        public void setBusinessContactName(String businessContactName) {
            this.businessContactName = businessContactName;
        }

        public String getBusinessContactPhone() {
            return businessContactPhone;
        }

        public void setBusinessContactPhone(String businessContactPhone) {
            this.businessContactPhone = businessContactPhone;
        }

        public String getBusinessDesc() {
            return businessDesc;
        }

        public void setBusinessDesc(String businessDesc) {
            this.businessDesc = businessDesc;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getBusinessLegal() {
            return businessLegal;
        }

        public void setBusinessLegal(String businessLegal) {
            this.businessLegal = businessLegal;
        }

        public String getBusinessLegalPhone() {
            return businessLegalPhone;
        }

        public void setBusinessLegalPhone(String businessLegalPhone) {
            this.businessLegalPhone = businessLegalPhone;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getBusinessPic() {
            return businessPic;
        }

        public void setBusinessPic(String businessPic) {
            this.businessPic = businessPic;
        }

        public String getBusinessProvice() {
            return businessProvice;
        }

        public void setBusinessProvice(String businessProvice) {
            this.businessProvice = businessProvice;
        }

        public int getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(int businessStatus) {
            this.businessStatus = businessStatus;
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

        public String getUsersId() {
            return usersId;
        }

        public void setUsersId(String usersId) {
            this.usersId = usersId;
        }
    }
}
