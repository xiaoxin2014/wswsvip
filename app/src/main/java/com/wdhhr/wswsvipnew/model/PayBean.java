package com.wdhhr.wswsvipnew.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by felear on 2017/8/24 0024.
 */

public class PayBean<T> {


    /**
     * status : 0
     * msg : 操作成功
     * time : 1503564885445
     * data : {"appid":"wx80c2c06a50ae8073","appsecret":"TuY576hjky46G7gKlgYH934HOOd8o06V","noncestr":"hO0215054464S182ms7DA","package":"Sign=WXPay","partnerid":"1484068392","partnerkey":"TuY576hjky46G7gKlgYH934HOOd8o06V","prepayid":"wx201708241654452ddcf10b800498716729","sign":"75B1F7A15371D47DA6F74CC7A24B5A05","timestamp":{"date":24,"day":4,"hours":16,"minutes":54,"month":7,"nanos":0,"seconds":45,"time":1503564885000,"timezoneOffset":-480,"year":117}}
     */

    private int status;
    private String msg;
    private long time;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx80c2c06a50ae8073
         * appsecret : TuY576hjky46G7gKlgYH934HOOd8o06V
         * noncestr : hO0215054464S182ms7DA
         * package : Sign=WXPay
         * partnerid : 1484068392
         * partnerkey : TuY576hjky46G7gKlgYH934HOOd8o06V
         * prepayid : wx201708241654452ddcf10b800498716729
         * sign : 75B1F7A15371D47DA6F74CC7A24B5A05
         * timestamp : {"date":24,"day":4,"hours":16,"minutes":54,"month":7,"nanos":0,"seconds":45,"time":1503564885000,"timezoneOffset":-480,"year":117}
         */

        private String appid;
        private String appsecret;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String partnerkey;
        private String prepayid;
        private String sign;
        private long timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPartnerkey() {
            return partnerkey;
        }

        public void setPartnerkey(String partnerkey) {
            this.partnerkey = partnerkey;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

    }
}
