package com.wdhhr.wswsvipnew.model.dataBase;

import java.util.List;

/**
 * Created by felear on 2017/8/29 0029.
 */

public class ExpressTracesBean {

    /**
     * EBusinessID : 1293060
     * ShipperCode : ZTO
     * Success : true
     * LogisticCode : 450010589947
     * State : 3
     * Traces : [{"AcceptTime":"2017-08-18 14:05:06","AcceptStation":"[深圳市] [深圳公明]的吴生安吉尔已收件 电话:18025432534"},{"AcceptTime":"2017-08-18 21:52:26","AcceptStation":"[深圳市] 快件离开 [深圳公明]已发往[深圳中心]"},{"AcceptTime":"2017-08-18 23:34:49","AcceptStation":"[深圳市] 快件到达 [深圳中心]"},{"AcceptTime":"2017-08-18 23:40:06","AcceptStation":"[深圳市] 快件离开 [深圳中心]已发往[深圳车公庙]"},{"AcceptTime":"2017-08-19 03:18:31","AcceptStation":"[深圳市] 快件到达 [深圳车公庙]"},{"AcceptTime":"2017-08-19 04:47:28","AcceptStation":"[深圳市] [深圳车公庙]的林友团正在第1次派件 电话:13651498580 请保持电话畅通、耐心等待"},{"AcceptTime":"2017-08-19 12:46:40","AcceptStation":"[深圳市] [深圳车公庙]的派件已签收 感谢使用中通快递,期待再次为您服务!"}]
     */

    private String EBusinessID;
    private String ShipperCode;
    private boolean Success;
    private String LogisticCode;
    private String State;
    private List<TracesBean> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean {
        /**
         * AcceptTime : 2017-08-18 14:05:06
         * AcceptStation : [深圳市] [深圳公明]的吴生安吉尔已收件 电话:18025432534
         */

        private String AcceptTime;
        private String AcceptStation;

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }
    }
}
