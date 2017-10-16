package com.wdhhr.wswsvipnew.model;

import com.wdhhr.wswsvipnew.model.dataBase.ExpressBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;
import com.wdhhr.wswsvipnew.model.dataBase.ProfitBean;

import java.util.List;

/**
 * Created by felear on 2017/8/24 0024.
 */

public class OrderCommonBean {

    /**
     * status : 0
     * msg : 操作成功
     * time : 1503548507573
     * data : {"orderId":"WSC276182410004","ordersPriceCount":210}
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
         * orderId : WSC276182410004
         * ordersPriceCount : 210
         */

        private String orderId;
        private String shipmentProfit;
        private String profit;
        private int count;
        private String todayProfit;
        private String myGold;
        private String ordersPriceCount;
        private List<OrdersListBean> ordersList;
        private List<ProfitBean> profitList;
        private List<ExpressBean> expressList;

        public String getMyGold() {
            return myGold;
        }

        public void setMyGold(String myGold) {
            this.myGold = myGold;
        }

        public List<ExpressBean> getExpressList() {
            return expressList;
        }

        public void setExpressList(List<ExpressBean> expressList) {
            this.expressList = expressList;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getShipmentProfit() {
            return shipmentProfit;
        }

        public void setShipmentProfit(String shipmentProfit) {
            this.shipmentProfit = shipmentProfit;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getTodayProfit() {
            return todayProfit;
        }

        public void setTodayProfit(String todayProfit) {
            this.todayProfit = todayProfit;
        }

        public List<ProfitBean> getProfitList() {
            return profitList;
        }

        public void setProfitList(List<ProfitBean> profitList) {
            this.profitList = profitList;
        }

        public List<OrdersListBean> getOrdersList() {
            return ordersList;
        }

        public void setOrdersList(List<OrdersListBean> ordersList) {
            this.ordersList = ordersList;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrdersPriceCount() {
            return ordersPriceCount;
        }

        public void setOrdersPriceCount(String ordersPriceCount) {
            this.ordersPriceCount = ordersPriceCount;
        }
    }
}
