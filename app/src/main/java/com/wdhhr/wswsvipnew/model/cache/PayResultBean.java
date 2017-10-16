package com.wdhhr.wswsvipnew.model.cache;

/**
 * Created by felear on 2017/8/26 0026.
 */

public class PayResultBean {

    private int result;
    private String orderNo;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
